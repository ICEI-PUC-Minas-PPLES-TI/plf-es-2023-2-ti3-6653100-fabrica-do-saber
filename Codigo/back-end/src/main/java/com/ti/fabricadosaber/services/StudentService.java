package com.ti.fabricadosaber.services;

import java.time.LocalDate;
import java.util.List;

import com.ti.fabricadosaber.components.StudentTeamOperation;
import com.ti.fabricadosaber.exceptions.EntityNotFoundException;
import com.ti.fabricadosaber.models.Parent;
import com.ti.fabricadosaber.services.exceptions.DataBindingViolationException;
import com.ti.fabricadosaber.utils.SecurityUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.ti.fabricadosaber.models.Student;
import com.ti.fabricadosaber.models.Team;
import com.ti.fabricadosaber.repositories.StudentRepository;

import java.util.Set;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    @Lazy
    private TeamService teamService;

    @Autowired
    private StudentTeamOperation studentTeamOperation;

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

        this.studentTeamOperation.twoParents(obj);

        Team team = this.teamService.findById(obj.getTeam().getId());
        obj.setId(null);
        obj.setTeam(team);
        Set<Parent> parents = studentTeamOperation.saveParents(obj);
        obj.setParents(parents);
        obj.setRegistrationDate(LocalDate.now());

        Student createdStudent = studentRepository.save(obj);
        this.teamService.updateTeamStudentCount(team);

        return createdStudent;
    }


    public Student update(Student obj) {
        this.studentTeamOperation.twoParents(obj);

        Student newObj = findById(obj.getId());
        String[] ignoreProperties = { "id", "registrationDate", "parents", "team"};

        BeanUtils.copyProperties(obj, newObj, ignoreProperties);

        Team oldTeam = newObj.getTeam();
        Team newTeam = this.teamService.findById(obj.getTeam().getId());

        updateOldTeam(oldTeam, newTeam, newObj);

        Set<Parent> updatedParents = this.studentTeamOperation.saveParents(obj);
        newObj.setParents(updatedParents);
        newObj.setRegistrationDate(LocalDate.now());
        newObj.setTeam(newTeam);

        newTeam.getStudents().add(newObj);
        this.teamService.updateTeamStudentCount(newTeam);

        return studentRepository.save(newObj);
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
            this.studentRepository.delete(student);
            this.teamService.updateStudent(student);
        } catch (Exception e) {
            throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas");
        }
    }
}
