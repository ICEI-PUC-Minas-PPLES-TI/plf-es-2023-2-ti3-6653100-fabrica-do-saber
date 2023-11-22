package com.ti.fabricadosaber.services;

import java.time.LocalDate;
import java.util.*;

import com.ti.fabricadosaber.exceptions.EntityNotFoundException;
import com.ti.fabricadosaber.exceptions.StudentTeamAssociationException;
import com.ti.fabricadosaber.exceptions.TwoParentsException;
import com.ti.fabricadosaber.models.*;
import com.ti.fabricadosaber.services.exceptions.DataBindingViolationException;
import com.ti.fabricadosaber.utils.SecurityUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.ti.fabricadosaber.repositories.StudentRepository;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    @Lazy
    private TeamService teamService;

    @Autowired
    private ParentService parentService;

    @Autowired
    @Lazy
    private StudentTeamAssociationService studentTeamAssociationService;



    public Student findById(Long id) {
        SecurityUtil.checkUser();

        Student student =
                this.studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                        "Aluno não encontrado! Id: " + id + ", Tipo: " + Student.class.getName()));

        return student;
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



    @Transactional
    public Student create(Student obj) {
        SecurityUtil.checkUser();
        exceptionsOfStudentOnTeam(obj);

        twoParents(obj);
        obj.setId(null);

        Set<Parent> parents = saveParents(obj);
        obj.setParents(parents);
        obj.setRegistrationDate(LocalDate.now());

       Student createdStudent = studentRepository.save(obj);

        enrollStudent(createdStudent);

        return createdStudent;
    }


    public void enrollStudent(Student obj) {

        Set<Long> teamIds = obj.getTeamIds();

        for (Long teamId : teamIds) {

            Team existingTeam = teamService.findById(teamId);

            studentTeamAssociationService.enrollStudentOnTeam(new StudentTeamAssociation(obj, existingTeam),
            true);
        }
    }

    public void exceptionsOfStudentOnTeam(Student obj) {
        Set<Long> teamIds = obj.getTeamIds();
        int countTeam = 0;

        for (Long teamId : teamIds) {
            Team existingTeam = teamService.findById(teamId);

            if(!(existingTeam instanceof VacationTeam))
                countTeam++;

        }

        if(countTeam > 1)
            throw new StudentTeamAssociationException("O estudante só pode se matricular em uma turma por vez");
    }


    @Transactional
    public Student update(Student obj) {
        twoParents(obj);

        Student newObj = findById(obj.getId());
        exceptionsOfStudentOnTeam(obj);

        String[] ignoreProperties = { "id", "registrationDate", "parents"};
        BeanUtils.copyProperties(obj, newObj, ignoreProperties);

        Set<Parent> updatedParents = saveParents(obj);
        newObj.setParents(updatedParents);
        newObj.setRegistrationDate(LocalDate.now());

        //persistir o objeto atualizado no BD
        Student saveStudent = studentRepository.save(newObj);

        // Lidar com a relação
        updateStudentOnTeam(saveStudent);

        return saveStudent;
    }

    private void updateStudentOnTeam(Student student) {
        studentTeamAssociationService.updateStudentOnAssociation(student.getTeamIds(), student);
    }



    public Set<Parent> saveParents(Student obj) {
        String[] ignoreProperties = { "id", "registrationDate", "cpf"};
        Set<Parent> parents = new HashSet<>();

        Parent currentParent;
        for (Parent parent : obj.getParents()) {
            String cpfParent = parent.getCpf();

            if (parentService.existsByCpf(cpfParent)) {
                currentParent = this.parentService.findByCpf(cpfParent);
                BeanUtils.copyProperties(parent, currentParent, ignoreProperties);
                currentParent = this.parentService.update(currentParent);
            } else {
                currentParent = parentService.create(parent);
            }
            parents.add(currentParent);
        }

        return parents;
    }


    public void twoParents(Student obj) {
        if (obj.getParents().size() != 2) {
            throw new TwoParentsException("O estudante deve ter dois responsáveis.");
        }
    }


    public void delete(Long id) {
        Student student = findById(id);
        try {
            updateNumberStudentsAfterStudentDeletion(student);
            this.studentRepository.delete(student);
        } catch (Exception e) {
            throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas");
        }
    }


    public void updateNumberStudentsAfterStudentDeletion(Student student) {
        List<Team> teamsAssociated = studentTeamAssociationService.teamsAssociatedWithTheStudent(student);
        for (Team team : teamsAssociated) {
            teamService.updateTeamStudentCount(team, team.getNumberStudents() - 1);
        }
    }


}
