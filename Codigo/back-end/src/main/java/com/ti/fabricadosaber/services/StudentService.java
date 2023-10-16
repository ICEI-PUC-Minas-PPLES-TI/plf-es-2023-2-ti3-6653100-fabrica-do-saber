package com.ti.fabricadosaber.services;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import com.ti.fabricadosaber.exceptions.EntityNotFoundException;
import com.ti.fabricadosaber.exceptions.TwoParentsException;
import com.ti.fabricadosaber.models.Parent;
import com.ti.fabricadosaber.security.UserSpringSecurity;
import com.ti.fabricadosaber.services.exceptions.DataBindingViolationException;
import com.ti.fabricadosaber.services.exceptions.ObjectNotFoundException;
import com.ti.fabricadosaber.utils.SecurityUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ti.fabricadosaber.models.Student;
import com.ti.fabricadosaber.models.Team;
import com.ti.fabricadosaber.repositories.StudentRepository;
import com.ti.fabricadosaber.repositories.TeamRepository;

import java.util.Set;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ParentService parentService;

    public Student findById(Long id) {
        Student student =
                this.studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                        "Aluno não encontrado! Id: " + id + ", Tipo: " + Student.class.getName()));
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        SecurityUtils.checkUser(userSpringSecurity);
        return student;
    }

    public List<Student> listAllStudents() {
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        SecurityUtils.checkUser(userSpringSecurity);
        List<Student> students = this.studentRepository.findAll();
        if (students.isEmpty()) {
            throw new EntityNotFoundException("Nenhum estudante cadastrado");
        }
        return students;
    }

    public Set<Parent> listParents(Long id) {
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        SecurityUtils.checkUser(userSpringSecurity);
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estudante com o ID " + id + " não encontrado"));

        return student.getParents();
    }

    @Transactional
    public Student create(Student obj) {
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        SecurityUtils.checkUser(userSpringSecurity);
        twoParents(obj);
        Team team = findTeamById(obj.getTeam().getId());
        obj.setId(null);
        obj.setTeam(team);
        Set<Parent> createdParents = saveParents(obj);
        obj.setParents(createdParents);
        obj.setRegistrationDate(LocalDate.now());

        Student createdStudent = studentRepository.save(obj);
        updateTeamStudentCount(team);

        return createdStudent;
    }

    public Set<Parent> saveParents(Student obj) {
        String[] ignoreProperties = { "id", "registrationDate" };
        Set<Parent> createdParents = new HashSet<>();

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
            createdParents.add(currentParent);
        }

        return createdParents;
    }

    public void twoParents(Student obj) {
        if (obj.getParents().size() != 2) {
            throw new TwoParentsException("O estudante deve ter dois responsáveis.");
        }
    }

    public Student update(Student obj) {
        twoParents(obj);

        Student newObj = findById(obj.getId());
        String[] ignoreProperties = { "id", "registrationDate" };

        Team oldTeam = newObj.getTeam();
        Team newTeam = findTeamById(obj.getTeam().getId());

        Set<Parent> updatedParents = saveParents(obj);
        BeanUtils.copyProperties(obj, newObj, ignoreProperties);
        newObj.setParents(updatedParents);
        newObj.setRegistrationDate(LocalDate.now());
        newObj.setTeam(newTeam);

        if (oldTeam != null && !oldTeam.equals(newTeam)) {
            oldTeam.getStudents().remove(newObj);
            updateTeamStudentCount(oldTeam);
        }

        newTeam.getStudents().add(newObj);
        updateTeamStudentCount(newTeam);

        return studentRepository.save(newObj);
    }

    private Team findTeamById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Turma não encontrada! Id: " + id + ", Tipo: " + Team.class.getName()));
    }

    private void updateTeamStudentCount(Team team) {
        team.setNumberStudents(team.getStudents().size());
        teamRepository.save(team);
    }

    public void delete(Long id) {
        Student student = findById(id);
        try {
            this.studentRepository.delete(student);
            Team team = student.getTeam();
            if (team != null) { //modularizar esse método
                team.getStudents().remove(student);
                team.setNumberStudents(team.getStudents().size());
                teamRepository.save(team);

            }
        } catch (Exception e) {
            throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas");
        }
    }
}
