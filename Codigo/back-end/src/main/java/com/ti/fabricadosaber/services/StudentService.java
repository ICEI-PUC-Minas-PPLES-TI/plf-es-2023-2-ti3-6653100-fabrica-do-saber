package com.ti.fabricadosaber.services;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import com.ti.fabricadosaber.models.Parent;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import com.ti.fabricadosaber.models.Student;
import com.ti.fabricadosaber.models.Team;
import com.ti.fabricadosaber.repositories.StudentRepository;
import com.ti.fabricadosaber.repositories.TeamRepository;

import javax.persistence.EntityNotFoundException;
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
        Optional<Student> student = this.studentRepository.findById(id);
        return student.orElseThrow(() -> new RuntimeException(
                "Aluno não encontrado! Id: " + id + ", Tipo: " + Student.class.getName()));
    }

    public List<Student> listAllStudents() {
        try {
            return studentRepository.findAll();
        } catch (EmptyResultDataAccessException error) {
            throw new RuntimeException("Nenhum estudante cadastrado", error);
        }
    }

    public Set<Parent> listParents(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estudante com o ID " + id + " não encontrado"));

        return student.getParents();
    }

    @Transactional
    public Student create(Student obj) {
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
            throw new RuntimeException("O estudante deve ter dois responsáveis.");
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
                .orElseThrow(() -> new RuntimeException(
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
            if (team != null) {
                team.getStudents().remove(student);
                team.setNumberStudents(team.getStudents().size());
                teamRepository.save(team);

            }
        } catch (Exception e) {
            throw new RuntimeException("Não é possível excluir pois há entidades relacionadas");
        }
    }
}
