package com.ti.fabricadosaber.services;

import java.util.ArrayList;
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
        processStudents(obj);
        obj = this.teamRepository.save(obj);
        associateStudents(obj);
        return obj;
    }

    @Transactional
    public Team update(Team obj) {
        Team newObj = findById(obj.getId());
        newObj.setName(obj.getName());
        newObj.setClassroom(obj.getClassroom());
        newObj.setGrade(obj.getGrade());
        newObj.setTeacher(obj.getTeacher());
        processStudents(obj);
        newObj.setNumberStudents(obj.getNumberStudents());
        newObj.setStudents(obj.getStudents());
        newObj = this.teamRepository.save(newObj);
        associateStudents(newObj);
        return newObj;
    }

    public void associateStudents(Team obj) {
        for (Student student : obj.getStudents()) {
            student.setTeam(obj);
        }
    }


    public void processStudents(Team obj) {
        List<Student> students = obj.getStudents();
        if (students != null && !students.isEmpty()) {
            List<Student> updatedStudents = new ArrayList<>();
            for (Student student : students) {
                Student existingStudent = studentService.findById(student.getId());
                updateStudent(student);
                updatedStudents.add(existingStudent);
            }
            obj.setStudents(updatedStudents);
            obj.setNumberStudents(updatedStudents.size());
        } else {
            obj.setNumberStudents(0);
        }
    }


    public void updateStudent(Student student) {
        Team team = student.getTeam();
        if(team != null) {
            student.getTeam().getStudents().remove(student);
            student.getTeam().setNumberStudents(student.getTeam().getStudents().size());
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

    public Team deleteStudent(Long teamId, Long studentId) {
        Team team = findById(teamId);
        Student student = studentService.findById(studentId);


        if(!(team.getStudents().contains(student))) {
            throw new RuntimeException("Aluno não está vinculado a turma " + team.getName());
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
