package com.ti.fabricadosaber.services;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import com.ti.fabricadosaber.components.StudentTeamOperation;
import com.ti.fabricadosaber.dto.VacationTeamResponseDTO;
import com.ti.fabricadosaber.exceptions.EntityNotFoundException;
import com.ti.fabricadosaber.exceptions.StudenteOnTeamException;
import com.ti.fabricadosaber.services.exceptions.DataBindingViolationException;
import com.ti.fabricadosaber.utils.SecurityUtil;
import java.util.function.Predicate;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.ti.fabricadosaber.models.Student;
import com.ti.fabricadosaber.models.Teacher;
import com.ti.fabricadosaber.models.Team;
import com.ti.fabricadosaber.models.VacationTeam;
import com.ti.fabricadosaber.repositories.VacationTeamRepository;
import jakarta.transaction.Transactional;

@Service
public class VacationTeamService {

    @Autowired
    private VacationTeamRepository vacationTeamRepository;

    @Autowired
    private StudentTeamOperation studentTeamOperation;


    @Autowired
    private TeacherService teacherService;

    @Autowired
    @Lazy
    private StudentService studentService;


    public VacationTeam findById(Long id) {
        VacationTeam vacationTeam = this.vacationTeamRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "Creche de férias não encontrada! Id: " + id + ", Tipo: " + Team.class.getName()));

        SecurityUtil.checkUser();
        return vacationTeam;
    }

    public List<VacationTeam> listAllVacationTeams() {
        SecurityUtil.checkUser();

        List<VacationTeam> team = this.vacationTeamRepository.findAll();
        if (team.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma creche de férias cadastrada");
        }
        return team;
    }

    public Set<Student> listStudents(Long id) {
        SecurityUtil.checkUser();

        VacationTeam team = vacationTeamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Creche de férias com o id " + id + " não encontrada."));

        return team.getStudents();
    }

    @Transactional
    public VacationTeam create(VacationTeam obj) {
        Teacher teacher = this.teacherService.findById(obj.getTeacher().getId());
        obj.setId(null);
        obj.setTeacher(teacher);
        this.processStudentInCreation(obj);
        obj.setStartDate(LocalDate.now());
        obj = this.vacationTeamRepository.save(obj);
        //this.associateStudents(obj);
        return obj;
    }

    @Transactional
    public VacationTeam save(VacationTeam vacationTeam) {
        return vacationTeamRepository.save(vacationTeam);
    }

   /* public void associateStudents(VacationTeam obj) {
        if (obj.getStudents() != null) {
            for (Student student : obj.getStudents()) {
                student.setTeam(obj);
            }
        }
    }*/


    public void removeStudentFromAllTeams(Student student) {
        List<VacationTeam> teams = vacationTeamRepository.findByStudentsContaining(student);
        for (VacationTeam team : teams) {
            team.getStudents().remove(student);
            updateVacationTeamStudentCount(team);
        }
    }




    public void updateVacationTeamStudentCount(VacationTeam vacationTeam) {
        int studentCount = vacationTeam.getStudents().size();
        vacationTeam.setNumberStudents(studentCount);
    }

    public void processStudentInCreation(VacationTeam obj) {
        Set<Student> students = obj.getStudents();
        obj.setNumberStudents(0);

        if (students != null && !students.isEmpty()) {
            Set<Student> updatedStudents = new HashSet<>();

            for (Student student : students) {

                Student existingStudent = studentService.findById(student.getId());

                existingStudent.getVacationTeams().add(obj);
                updatedStudents.add(existingStudent);
            }

            obj.setNumberStudents(updatedStudents.size());
            obj.setStudents(updatedStudents);

        }
    }



    @Transactional
    public VacationTeam update(VacationTeam obj) {
        VacationTeam newObj = findById(obj.getId());
        newObj.setName(obj.getName());
        newObj.setClassroom(obj.getClassroom());
        newObj.setGrade(obj.getGrade());
        newObj.setTeacher(obj.getTeacher());
        newObj.setStartDate(obj.getStartDate());
        newObj.setEndDate(obj.getEndDate());

        processStudentOnUpdate(obj, newObj);


        newObj.setNumberStudents(obj.getNumberStudents());
        newObj.setStudents(obj.getStudents());


        newObj = this.vacationTeamRepository.save(newObj);
        //studentTeamOperation.associateStudents(newObj);
        return newObj;
    }


    public void delete(Long id) {
        VacationTeam team = findById(id);
        try {
            this.vacationTeamRepository.delete(team);
        } catch (Exception e) {
            throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas");
        }
    }

    public void processStudentOnUpdate(VacationTeam obj, VacationTeam newObj) {
        processStudentInCreation(obj);
        Set<Student> students = newObj.getStudents();

        if(students != null) {
            if(obj.getNumberStudents() != 0) {
                for (Student student : students) {
                    if (!obj.getStudents().contains(student))
                        student.setTeam(null);
                }
            } else {
                Predicate<Student> alwaysTrue = student -> {
                    student.setTeam(null);
                    return true;
                };
                students.removeIf(alwaysTrue);
            }
        }

    }



    public void updateStudent(Student student) {
        //VacationTeam team = student.getTeam();
        // if (team != null) {
        //     student.getTeam().getStudents().remove(student);
        //     student.getTeam().setNumberStudents(student.getTeam().getStudents().size());
        //     vacationTeamRepository.save(team);
        // }
    }


    // O controller acessa esse método
    public VacationTeam deleteStudentFromTeam(Long teamId, List<Long> idsStudent) {
        VacationTeam team = findById(teamId);

        for (Long idStudent : idsStudent) {

            Student student = studentService.findById(idStudent);
            if (!(team.getStudents().contains(student))) {
                throw new StudenteOnTeamException("Aluno não está vinculado a creche de férias" + team.getName());
            }

            updateStudent(student);
            student.setTeam(null);
            team.getStudents().remove(student);
        }

        updateVacationTeamStudentCount(team);
        return team;
    }

    public VacationTeamResponseDTO convertToVacationTeamResponseDTO(VacationTeam team) {

        VacationTeamResponseDTO dto = new VacationTeamResponseDTO();
        dto.setId(team.getId());
        dto.setName(team.getName());
        dto.setClassroom(team.getClassroom());
        dto.setGrade(team.getGrade());
        dto.setNumberStudents(team.getNumberStudents());
        dto.setTeacherId(team.getTeacher().getId());
        dto.setStartDate(team.getStartDate());
        dto.setEndDate(team.getEndDate());

        if (team.getStudents() != null) {
            List<Long> studentIds = team.getStudents().stream()
                    .map(Student::getId)
                    .collect(Collectors.toList());
            dto.setStudentIds(studentIds);
        }

        return dto;
    }
}
