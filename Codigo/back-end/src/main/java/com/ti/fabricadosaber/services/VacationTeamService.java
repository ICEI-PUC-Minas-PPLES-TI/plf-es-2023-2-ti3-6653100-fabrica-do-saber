package com.ti.fabricadosaber.services;

import com.ti.fabricadosaber.exceptions.DataException;
import com.ti.fabricadosaber.exceptions.EntityNotFoundException;
import com.ti.fabricadosaber.models.*;
import com.ti.fabricadosaber.repositories.VacationTeamRepository;
import com.ti.fabricadosaber.services.exceptions.DataBindingViolationException;
import com.ti.fabricadosaber.services.interfaces.TeamOperations;
import com.ti.fabricadosaber.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Set;

@Service
public class VacationTeamService implements TeamOperations {

    @Autowired
    private VacationTeamRepository vacationTeamRepository;

    @Autowired
    private TeacherService teacherService;


    @Autowired
    @Lazy
    private StudentService studentService;

    @Autowired
    @Lazy
    private StudentTeamAssociationService studentTeamAssociationService;


    @Override
    public VacationTeam findById(Long id) {
        VacationTeam team = this.vacationTeamRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "Creche não encontrada! Id: " + id + ", Tipo: " + VacationTeam.class.getName()));

        SecurityUtil.checkUser();
        return team;
    }

    public void checkDate(VacationTeam vacationTeam) {
        dataNotNull(vacationTeam);
        startDateValidate(vacationTeam);
        validateDateRange(vacationTeam);
    }

    public VacationTeam create(VacationTeam obj) {

        obj.setId(null);
        obj.setTeacher(checkTeacher(obj.getTeacher()));
        checkDate(obj);

        obj = this.vacationTeamRepository.save(obj);

        teamStudentsInCreate(obj);

        return obj;

    }

    public VacationTeam update(VacationTeam obj) {
        VacationTeam newObj = findById(obj.getId());
        newObj.setName(obj.getName());
        newObj.setClassroom(obj.getClassroom());
        newObj.setGrade(obj.getGrade());
        newObj.setTeacher(checkTeacher(obj.getTeacher()));
        newObj.setNumberStudents(obj.getNumberStudents());


        newObj = this.vacationTeamRepository.save(newObj);

        updateTeamWithStudents(newObj);

        return newObj;
    }

    public void teamStudentsInCreate(VacationTeam obj) {

        Set<Long> studentIds = obj.getStudentIds();

        if(studentIds != null && !studentIds.isEmpty()) {

            for(Long studentId : studentIds) {
                Student existingStudent = studentService.findById(studentId);

                studentTeamAssociationService.enrollStudentOnTeam(new StudentTeamAssociation(existingStudent, obj),
                        false);
            }
        }
    }

    private void updateTeamWithStudents(Team team) {
        studentTeamAssociationService.updateTeamOnAssociation(team.getStudentIds(), team);
    }


    @Override
    public Teacher checkTeacher(Teacher teacher) {
        Teacher existingTeacher = null;

        if(teacher != null)
            existingTeacher = teacherService.findById(teacher.getId());

        return existingTeacher;
    }


    public static void validateDateRange(VacationTeam vacationTeam) {
        LocalDate dataInicio = vacationTeam.getStartDate();
        LocalDate dataTermino = vacationTeam.getEndDate();

        if (dataInicio.isAfter(dataTermino)) {
            throw new DataException("A data de início não pode ser posterior à data de término.");
        }
    }

    public void dataNotNull(VacationTeam vacationTeam) {
        LocalDate dataInicio = vacationTeam.getStartDate();
        LocalDate dataTermino = vacationTeam.getEndDate();

        if(dataInicio == null || dataTermino == null) {
            throw new DataException("A data de início e término da creche de férias não pode ser nulo.");
        }
    }


    public void startDateValidate(VacationTeam vacationTeam) {
        if(!(vacationTeam.getStartDate().isAfter(LocalDate.now())))
            throw new DataException("A creche só pode começar de hoje em diante!");
    }


    public void updateTeamStudentCount(VacationTeam vacationTeam, Integer studentCount) {
        vacationTeam.setNumberStudents(studentCount);
        vacationTeamRepository.save(vacationTeam);
    }


    @Override
    public void delete(Long id) {
        VacationTeam team = findById(id);
        try {
            this.vacationTeamRepository.delete(team);
        } catch (Exception e) {
            throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas");
        }
    }



}
