package com.ti.fabricadosaber.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.ti.fabricadosaber.models.Student;
import com.ti.fabricadosaber.models.Teacher;
import com.ti.fabricadosaber.models.Team;
import com.ti.fabricadosaber.repositories.TeamRepository;

import jakarta.transaction.Transactional;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    public Team findById(Long id) {
        Optional<Team> team = this.teamRepository.findById(id);
        return team.orElseThrow(() -> new RuntimeException(
                "Turma não encontrada! Id: " + id + ", Tipo: " + Team.class.getName()));
    }

    public List<Team> listAllTeams() {
        try {
            return teamRepository.findAll();
        } catch (EmptyResultDataAccessException error) {
            throw new RuntimeException("Nenhuma turma cadastrada", error);
        }
    }

    public List<Student> listStudents(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma com o id " + id + " não encontrada."));

        return team.getStudents();
    }

    @Transactional
    public Team create(Team obj) {
        Teacher teacher = this.teacherService.findById(obj.getTeacher().getId());
        obj.setId(null);
        obj.setTeacher(teacher);
        obj = this.teamRepository.save(obj);
        return obj;

    }

    public Team addStudentToTeam(Long teamId, Long studentId) {
        Team team = findById(teamId);
        Student student = studentService.findById(studentId);
        
        if (student.getTeam() != null) {
            throw new RuntimeException("Aluno já existe na turma " + team.getClassroom());
        }

        student.setTeam(team);
        team.getStudents().add(student);
        team.setNumberStudents(team.getStudents().size());
        return teamRepository.save(team);
    }

    public Team update(Team obj) {
        Team newObj = findById(obj.getId());
        String[] ignoreProperties = { "id", "students", "numberStudents" };

        BeanUtils.copyProperties(obj, newObj, ignoreProperties);
        return this.teamRepository.save(newObj);
    }

    public Team deleteStudent(Long teamId, Long studentId) {
        Team team = findById(teamId);
        Student student = studentService.findById(studentId);

        if(student.getTeam() == null) {
            throw new RuntimeException("Aluno já foi deletado da turma " + team.getClassroom());
        }
        student.setTeam(null);
        team.getStudents().remove(student);
        team.setNumberStudents(team.getStudents().size());
        return teamRepository.save(team);
    }

    public void delete(Long id) {
        Team team = findById(id);

        try {
            this.teamRepository.delete(team);
        } catch (Exception e) {
            throw new RuntimeException("Não é possível excluir pois há entidades relacionadas");
        }
    }

}
