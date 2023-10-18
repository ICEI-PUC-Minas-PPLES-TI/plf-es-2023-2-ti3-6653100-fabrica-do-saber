package com.ti.fabricadosaber.utils;

import com.ti.fabricadosaber.exceptions.StudenteOnTeamException;
import com.ti.fabricadosaber.models.Student;
import com.ti.fabricadosaber.models.Team;
import com.ti.fabricadosaber.services.StudentService;
import com.ti.fabricadosaber.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudentTeamUtil {

    @Autowired
    private static StudentService studentService;

    @Autowired
    private static TeamService teamService;


    public static Team deleteStudent(Long teamId, List<Long> idsStudent) {
        Team team = teamService.findById(teamId);

        for (Long idStudent : idsStudent) {

            Student student = studentService.findById(idStudent);
            if (!(team.getStudents().contains(student))) {
                throw new StudenteOnTeamException("Aluno não está vinculado a turma " + team.getName());
            }

            teamService.updateStudent(student);
            student.setTeam(null);
            team.getStudents().remove(student);
        }

       teamService.updateTeamStudentCount(team);
        return team;
    }


    public void processStudentInCreation(Team obj) {
        List<Student> students = obj.getStudents();
        if (students != null && !students.isEmpty()) {
            List<Student> updatedStudents = new ArrayList<>();
            for (Student student : students) {

                Student existingStudent = studentService.findById(student.getId());

                teamService.updateStudent(existingStudent);
                updatedStudents.add(existingStudent);

                obj.setStudents(updatedStudents);
                obj.setNumberStudents(updatedStudents.size());
            }
        } else {
            obj.setNumberStudents(0);
        }
    }

    public void associateStudents(Team obj) {
        if (obj.getStudents() != null) {
            for (Student student : obj.getStudents()) {
                student.setTeam(obj);
            }
        }
    }

    public void processStudentOnUpdate(Team obj, Team newObj) {
        processStudentInCreation(obj);
        List<Student> studentNewObj = newObj.getStudents();
        if(studentNewObj != null && obj.getNumberStudents() == 0)
            studentNewObj.forEach(x -> x.setTeam(null));
    }



}
