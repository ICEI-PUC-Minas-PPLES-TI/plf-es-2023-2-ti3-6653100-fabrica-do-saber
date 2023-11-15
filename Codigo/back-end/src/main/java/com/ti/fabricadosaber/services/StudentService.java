package com.ti.fabricadosaber.services;

import java.time.LocalDate;
import java.util.*;

import com.ti.fabricadosaber.components.StudentTeamOperation;
import com.ti.fabricadosaber.exceptions.EntityNotFoundException;
import com.ti.fabricadosaber.models.Parent;
import com.ti.fabricadosaber.models.VacationTeam;
import com.ti.fabricadosaber.services.exceptions.DataBindingViolationException;
import com.ti.fabricadosaber.utils.SecurityUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.ti.fabricadosaber.models.Student;
import com.ti.fabricadosaber.models.Team;
import com.ti.fabricadosaber.repositories.StudentRepository;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    @Lazy
    private TeamService teamService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private StudentTeamOperation studentTeamOperation;


    @Autowired
    @Lazy
    private VacationTeamService vacationTeamService;

    public Student findById(Long id) {
        SecurityUtil.checkUser();

        Student student =
                this.studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                        "Aluno não encontrado! Id: " + id + ", Tipo: " + Student.class.getName()));

        return student;
    }

    @Transactional
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> listAllStudents() {
        SecurityUtil.checkUser();

        List<Student> students = this.studentRepository.findAll();
        if (students.isEmpty()) {
            throw new EntityNotFoundException("Nenhum estudante cadastrado");
        }
        return students;
    }

    public Set<Parent> listParents(Long id) {
        SecurityUtil.checkUser();

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estudante com o ID " + id + " não encontrado"));

        return student.getParents();
    }


    public Set<VacationTeam> listVacationTeams(Long id) {

        SecurityUtil.checkUser();

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estudante com o ID " + id + " não encontrado"));

        return student.getVacationTeams();
    }



    @Transactional
    public Student create(Student obj) {
        SecurityUtil.checkUser();


        this.studentTeamOperation.twoParents(obj);

        Team team = this.teamService.findById(obj.getTeam().getId());

        obj.setId(null);
        obj.setTeam(team);

        Set<Parent> parents = studentTeamOperation.saveParents(obj);
        obj.setParents(parents);
        obj.setRegistrationDate(LocalDate.now());

        Student createdStudent = studentRepository.save(obj);
        associateStudentWithVacationTeam(obj);
        this.teamService.updateTeamStudentCount(team);

        return createdStudent;
    }




    @Transactional
    public Student update(Student obj) {
        this.studentTeamOperation.twoParents(obj);

        Student newObj = findById(obj.getId());
        String[] ignoreProperties = { "id", "registrationDate", "parents", "team"};

        BeanUtils.copyProperties(obj, newObj, ignoreProperties);

        Team oldTeam = newObj.getTeam();
        Team newTeam = this.teamService.findById(obj.getTeam().getId());

        updateOldTeam(oldTeam, newTeam, newObj);
        updateVacationTeams(newObj, obj.getVacationTeams()); // relação vacationTeam e student

        Set<Parent> updatedParents = this.studentTeamOperation.saveParents(obj);
        newObj.setParents(updatedParents);
        newObj.setRegistrationDate(LocalDate.now());
        newObj.setTeam(newTeam);

        newTeam.getStudents().add(newObj);
        this.teamService.updateTeamStudentCount(newTeam);

        return studentRepository.save(newObj);
    }




    //Todo: Esse método só serve para create
    public void associateStudentWithVacationTeam(Student obj) {
        Set<VacationTeam> vacationTeams = obj.getVacationTeams();

        if (vacationTeams != null && !vacationTeams.isEmpty()) {
            Set<VacationTeam> managedVacationTeams = new HashSet<>();

            for (VacationTeam vacationTeam : vacationTeams) {
                VacationTeam existsVacationTeam = vacationTeamService.findById(vacationTeam.getId());

                // Garanta que o VacationTeam está gerenciado pela JPA
                existsVacationTeam = entityManager.merge(existsVacationTeam);

                // Verifique se o estudante já está associado à equipe
                if (!existsVacationTeam.getStudents().contains(obj)) {
                    existsVacationTeam.getStudents().add(obj);
                    managedVacationTeams.add(existsVacationTeam);
                    vacationTeamService.updateVacationTeamStudentCount(existsVacationTeam);
                }
            }

            Student existingStudent = obj.getId() != null ? findById(obj.getId()) : null;

            if (existingStudent != null) {
                // Remover relações com VacationTeams não mencionados no corpo da requisição
                Set<VacationTeam> existingTeams = existingStudent.getVacationTeams();
                existingTeams.removeAll(managedVacationTeams);

                for (VacationTeam existingTeam : existingTeams) {
                    existingTeam.getStudents().remove(obj);
                }
            }

            obj.setVacationTeams(managedVacationTeams);
        }
    }




    /* todo: o método updateVacationTeams serve para update e só consegue cumprir os requisitos 1) e 3).

1) Quando é passado em JSON para requisição PUT de Studant objeto VacatioTeam que já está vinculado ao estudante no banco de dados, o método simplismente ignora.

2) Quando não é passado em JSON para requisição PUT de Studant o objeto VacationTeam que está vinculado ao estudante no banco de dados, o método deleta essa relação completamente do banco de dados. Isso implica que essa relação tem que sumir da tabela intermediária que gerencia a relação n-n entre Studant e VacationTeam. Além disso o número de estudantes da turma tem que ser decrementado.

3) Quando é passado em JSON para requisição PUT de Studant o objeto VacationTeam que não tem vinculação ao estudante no banco de dados, o método cria essa relação e tal relação tem que aparecer na tabela intermediária do banco de dados. Além disso, o número de estudantes da turma tem que ser incrementado.
 */


    @Transactional
    public void updateVacationTeams(Student student, Set<VacationTeam> updatedVacationTeams) {
        Set<VacationTeam> existingVacationTeams = student.getVacationTeams();

        for (VacationTeam updatedTeam : updatedVacationTeams) {
            VacationTeam existingTeam = vacationTeamService.findById(updatedTeam.getId());

            if (existingVacationTeams.contains(existingTeam)) {
                // O estudante já está vinculado a esta equipe, ignorar
                continue;
            }

            // Adicionar nova relação mencionada no corpo da requisição
            existingTeam.getStudents().add(student);
            vacationTeamService.updateVacationTeamStudentCount(existingTeam);
        }

        // Remover relações não mencionadas no corpo da requisição
        for (VacationTeam existingTeam : existingVacationTeams) {
            if (!updatedVacationTeams.contains(existingTeam)) {
                existingTeam.getStudents().remove(student);
                vacationTeamService.updateVacationTeamStudentCount(existingTeam);
            }
        }

        // Atualizar as relações na entidade Student
        student.setVacationTeams(updatedVacationTeams);
    }



    public void updateOldTeam(Team oldTeam, Team newTeam, Student newObj) {
        if (oldTeam != null && !oldTeam.equals(newTeam)) {
            oldTeam.getStudents().remove(newObj);
            this.teamService.updateTeamStudentCount(oldTeam);
        }
    }


    public void delete(Long id) {
        Student student = findById(id);
        try {
            vacationTeamService.removeStudentFromAllTeams(student);
            this.studentRepository.delete(student);
            this.teamService.updateStudent(student);
        } catch (Exception e) {
            throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas");
        }
    }
}
