package com.ti.fabricadosaber.services;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.ti.fabricadosaber.dto.TeamResponseDTO;
import com.ti.fabricadosaber.exceptions.EntityNotFoundException;
import com.ti.fabricadosaber.exceptions.StudentTeamAssociationException;
import com.ti.fabricadosaber.models.*;
import com.ti.fabricadosaber.services.exceptions.DataBindingViolationException;
import com.ti.fabricadosaber.services.interfaces.TeamOperations;
import com.ti.fabricadosaber.utils.SecurityUtil;
import jakarta.persistence.DiscriminatorValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.ti.fabricadosaber.repositories.TeamRepository;
import jakarta.transaction.Transactional;

@Service
public class TeamService implements TeamOperations {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @Autowired
    @Lazy
    private StudentTeamAssociationService studentTeamAssociationService;


    @Override
    public Team findById(Long id) {
        Team team = this.teamRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "Turma não encontrada! Id: " + id + ", Tipo: " + Team.class.getName()));

        SecurityUtil.checkUser();
        return team;
    }

    public TeamResponseDTO findByIdDTO(Long id) {
        Team team = findById(id);

        SecurityUtil.checkUser();

        List<Long> studentIds = studentTeamAssociationService.findStudentIdsByTeamId(id);

        TeamResponseDTO teamResponseDTO = convertToTeamResponseDTO(team, studentIds);

        return teamResponseDTO;
    }



    public List<TeamResponseDTO> listAllTeams() {
        SecurityUtil.checkUser();

        List<Team> teams = this.teamRepository.findAllTeams();
        List<TeamResponseDTO> teamDTO = new ArrayList<>();

        for (Team team : teams) {
            List<Long> studentIds = studentTeamAssociationService.findStudentIdsByTeamId(team.getId());
            TeamResponseDTO teamResponseDTO = convertToTeamResponseDTO(team, studentIds);
            teamDTO.add(teamResponseDTO);
        }

        if (teamDTO.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma turma cadastrada");
        }

        return teamDTO;
    }




        public List<Student> listStudents(Long id) {
        Team team = findById(id);

        if(team instanceof VacationTeam)
            throw new StudentTeamAssociationException("Nenhuma turma cadastrada com id: " + id);

        List<Student> students = studentTeamAssociationService.findStudentsActiveOnTeam(id);

        if(students.isEmpty()) {
            throw new EntityNotFoundException("Nenhum aluno está ativo na turma");
        }

        return students;
    }



    @Transactional
    public Team create(Team obj) {
        obj.setId(null);
        obj.setTeacher(checkTeacher(obj.getTeacher()));

        obj = this.teamRepository.save(obj);

        teamStudentsInCreate(obj);

        return obj;
    }

    @Override
    public Teacher checkTeacher(Teacher teacher) {
        Teacher existingTeacher = null;

        if(teacher != null)
            existingTeacher = teacherService.findById(teacher.getId());

        return existingTeacher;
    }


    private void teamStudentsInCreate(Team obj) {

        Set<Long> studentIds = obj.getStudentIds();

        if(studentIds != null && !studentIds.isEmpty()) {

            for(Long studentId : studentIds) {
                Student existingStudent = studentService.findById(studentId);

                studentTeamAssociationService.enrollStudentOnTeam(new StudentTeamAssociation(existingStudent, obj),
                        false);
            }
        }
    }

    public void teacherExcluded(Team team) {
        Team teamWithoutStudent  = findById(team.getId());
        teamWithoutStudent.setTeacher(null);
        teamRepository.save(teamWithoutStudent);
    }


    @Transactional
    public Team update(Team obj) {
        Team newObj = findById(obj.getId());
        newObj.setName(obj.getName());
        newObj.setClassroom(obj.getClassroom());
        newObj.setGrade(obj.getGrade());
        newObj.setTeacher(checkTeacher(obj.getTeacher()));
        newObj.setStudentIds(obj.getStudentIds()); // Objeto que vai vai ter estudantes mudado



        newObj = this.teamRepository.save(newObj);

        updateTeamWithStudents(newObj);


        return newObj;
    }


    private void updateTeamWithStudents(Team team) {
        studentTeamAssociationService.updateTeamOnAssociation(team.getStudentIds(), team);
    }


    @Override
    public void delete(Long id) {
        Team team = findById(id);
        try {
            studentTeamAssociationService.deleteTeam(id);
            this.teamRepository.delete(team);
        } catch (Exception e) {
            throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas");
        }
    }



    public void updateTeamStudentCount(Team team, Integer studentCount) {
        team.setNumberStudents(studentCount);
        teamRepository.save(team);
    }




    public TeamResponseDTO convertToTeamResponseDTO(Team team, List<Long> studentIds) {

        TeamResponseDTO dto = new TeamResponseDTO();
        dto.setId(team.getId());
        dto.setName(team.getName());
        dto.setClassroom(team.getClassroom());
        dto.setGrade(team.getGrade());
        dto.setClassroom(team.getClassroom());
        dto.setNumberStudents(team.getNumberStudents());

        if(team.getTeacher() == null)
            dto.setTeacherId(null);
        else
            dto.setTeacherId(team.getTeacher().getId());

        dto.setStudentIds(studentIds);
        dto.setType(team.getClass().getAnnotation(DiscriminatorValue.class).value());

        return dto;
    }





}
