package com.ti.fabricadosaber.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ti.fabricadosaber.components.StudentOperationComponent;
import com.ti.fabricadosaber.dto.TeamResponseDTO;
import com.ti.fabricadosaber.exceptions.EntityNotFoundException;
import com.ti.fabricadosaber.exceptions.StudenteOnTeamException;
import com.ti.fabricadosaber.services.exceptions.DataBindingViolationException;
import com.ti.fabricadosaber.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
    private StudentOperationComponent studentOperationComponent;


    @Autowired
    private TeacherService teacherService;

    @Autowired
    @Lazy
    private StudentService studentService;


    public Team findById(Long id) {
        Team team = this.teamRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "Turma não encontrada! Id: " + id + ", Tipo: " + Team.class.getName()));

        SecurityUtil.checkUser();
        return team;
    }

    public List<Team> listAllTeams() {
        SecurityUtil.checkUser();

        List<Team> team = this.teamRepository.findAll();
        if (team.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma turma cadastrada");
        }
        return team;
    }

    public List<Student> listStudents(Long id) {
        SecurityUtil.checkUser();

        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Turma com o id " + id + " não encontrada."));

        return team.getStudents();
    }

    @Transactional
    public Team create(Team obj) {
        Teacher teacher = this.teacherService.findById(obj.getTeacher().getId());
        obj.setId(null);
        obj.setTeacher(teacher);
        studentOperationComponent.processStudentInCreation(obj);
        obj = this.teamRepository.save(obj);
        studentOperationComponent.associateStudents(obj);
        return obj;
    }

    @Transactional
    public Team update(Team obj) {
        Team newObj = findById(obj.getId());
        newObj.setName(obj.getName());
        newObj.setClassroom(obj.getClassroom());
        newObj.setGrade(obj.getGrade());
        newObj.setTeacher(obj.getTeacher());

        studentOperationComponent.processStudentOnUpdate(obj, newObj);

        newObj.setNumberStudents(obj.getNumberStudents());
        newObj.setStudents(obj.getStudents());
        newObj = this.teamRepository.save(newObj);
        studentOperationComponent.associateStudents(newObj);
        return newObj;
    }



    public void updateStudent(Student student) {
        Team team = student.getTeam();
        if (team != null) {
            student.getTeam().getStudents().remove(student);
            student.getTeam().setNumberStudents(student.getTeam().getStudents().size());
            teamRepository.save(team);
        }
    }

    public Team addStudentToTeam(Long id, List<Long> idsStudents) {
        Team team = findById(id);

        for (Long idStudent : idsStudents) {
            Student student = studentService.findById(idStudent);
            updateStudent(student);
            student.setTeam(team);
            team.getStudents().add(student);
        }

        team.setNumberStudents(team.getStudents().size());
        return teamRepository.save(team);
    }


    public void updateTeamStudentCount(Team team) {
        team.setNumberStudents(team.getStudents().size());
        teamRepository.save(team);
    }


    public void delete(Long id) {
        Team team = findById(id);

        try {
            this.teamRepository.delete(team);
        } catch (Exception e) {
            throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas");
        }
    }

    public TeamResponseDTO convertToTeamResponseDTO(Team team) {

        TeamResponseDTO dto = new TeamResponseDTO();
        dto.setId(team.getId());
        dto.setName(team.getName());
        dto.setClassroom(team.getClassroom());
        dto.setGrade(team.getGrade());
        dto.setNumberStudents(team.getNumberStudents());
        dto.setTeacherId(team.getTeacher().getId());

        if (team.getStudents() != null) {
            List<Long> studentIds = team.getStudents().stream()
                    .map(Student::getId)
                    .collect(Collectors.toList());
            dto.setStudentIds(studentIds);
        }

        return dto;
    }

}
