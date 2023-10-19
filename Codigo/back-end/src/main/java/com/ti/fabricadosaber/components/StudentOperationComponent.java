package com.ti.fabricadosaber.components;

import com.ti.fabricadosaber.models.Student;
import com.ti.fabricadosaber.models.Team;
import com.ti.fabricadosaber.services.StudentService;
import com.ti.fabricadosaber.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudentOperationComponent {

    @Autowired
    private static StudentService studentService;

    @Autowired
    private static TeamService teamService;



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

    public void processStudentOnUpdate(Team obj, Team newObj) {
        processStudentInCreation(obj);
        List<Student> studentNewObj = newObj.getStudents();
        if(studentNewObj != null && obj.getNumberStudents() == 0)
            studentNewObj.forEach(x -> x.setTeam(null));
    }

    public void associateStudents(Team obj) {
        if (obj.getStudents() != null) {
            for (Student student : obj.getStudents()) {
                student.setTeam(obj);
            }
        }
    }




}
