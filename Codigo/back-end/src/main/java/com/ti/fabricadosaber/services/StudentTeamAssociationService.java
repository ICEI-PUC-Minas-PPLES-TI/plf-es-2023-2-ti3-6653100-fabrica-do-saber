package com.ti.fabricadosaber.services;

import com.ti.fabricadosaber.exceptions.DataException;
import com.ti.fabricadosaber.exceptions.EntityNotFoundException;
import com.ti.fabricadosaber.models.StudentTeamAssociation;
import com.ti.fabricadosaber.models.Team;
import com.ti.fabricadosaber.models.VacationTeam;
import com.ti.fabricadosaber.repositories.StudentTeamAssociationRepository;
import com.ti.fabricadosaber.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class StudentTeamAssociationService {

    @Autowired
    private StudentTeamAssociationRepository studentTeamAssociationRepository;


    @Autowired
    @Lazy
    private TeamService teamService;


    @Autowired
    @Lazy
    private VacationTeamService vacationTeamService;


    public StudentTeamAssociation findById(StudentTeamAssociation.StudentTeamId id) {

        StudentTeamAssociation studentTeamAssociation =
                this.studentTeamAssociationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                        "Relação entre turma e aluno não encontrada: " + id));

        SecurityUtil.checkUser();

        return studentTeamAssociation;
    }


    public List<StudentTeamAssociation> listAll() {

        List<StudentTeamAssociation> studentTeamAssociations = (List<StudentTeamAssociation>) this.studentTeamAssociationRepository.findAll();

        if (studentTeamAssociations.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma relação entre Turma e Estudante encontrada!");
        }

        return studentTeamAssociations;
    }

    public StudentTeamAssociation processStudentInTeam(StudentTeamAssociation studentTeamAssociation) {

        Long studentId = studentTeamAssociation.getStudent().getId();
        Long teamId = studentTeamAssociation.getTeam().getId();
        boolean isVacationTeam = teamIsVacationTeam(studentTeamAssociation.getTeam());

        if(!isVacationTeam)
            desactivateExistingAssociation(studentId, teamId);


        if(!isStudentAlreadyInTeam(studentId, teamId)) {

            if(isVacationTeam) {
                desactivateAssociationsInVacationTeam((VacationTeam) studentTeamAssociation.getTeam());
            }

            create(studentTeamAssociation);

        } else if (!(isTeamStudentRelationshipActive(studentId, teamId))){
            updateExistingAssociation(studentId, teamId);
        }

        return studentTeamAssociation;
    }



    public StudentTeamAssociation create(StudentTeamAssociation studentTeamAssociation) {

        studentTeamAssociation.setStartDate(LocalDate.now());
        studentTeamAssociation.setEndDate(null);
        studentTeamAssociation.setIsActive(true);

        StudentTeamAssociation studentTeamAssociationSave =
                studentTeamAssociationRepository.save(studentTeamAssociation);

        updateCountStudentInTeam(studentTeamAssociation);

        return studentTeamAssociationSave;
    }


    public StudentTeamAssociation update(StudentTeamAssociation updatedAssociation) {

        StudentTeamAssociation existingAssociation = findById(updatedAssociation.getId());

        existingAssociation.setIsActive(true);
        existingAssociation.setStartDate(LocalDate.now());
        existingAssociation.setEndDate(null);


        StudentTeamAssociation studentTeamAssociationSave =
                studentTeamAssociationRepository.save(existingAssociation);

        updateCountStudentInTeam(existingAssociation);

        return studentTeamAssociationSave;
    }

    private void updateCountStudentInTeam(StudentTeamAssociation studentTeamAssociation) {

        if(teamIsVacationTeam(studentTeamAssociation.getTeam())) {
            VacationTeam convertTeam = (VacationTeam) studentTeamAssociation.getTeam();

            vacationTeamService.updateTeamStudentCount(convertTeam,
                    studentTeamAssociationRepository.countByTeamAndIsActiveIsTrue(convertTeam));
        } else {
            teamService.updateTeamStudentCount(studentTeamAssociation.getTeam(),
                    studentTeamAssociationRepository.countByTeamAndIsActiveIsTrue(studentTeamAssociation.getTeam()));
        }
    }

    private void updateExistingAssociation(Long studentId, Long teamId) {
        StudentTeamAssociation.StudentTeamId associationId = new StudentTeamAssociation.StudentTeamId(studentId, teamId);
        StudentTeamAssociation existingAssociation = findById(associationId);
        update(existingAssociation);
    }


    /**
     * Verificar se o aluno tem relação com uma equipe e desativa-la. Não pode ser chamado quando o id é de um
     * VacationTeam
     * @param studentId
     * @param teamId
     */

    private void desactivateExistingAssociation(Long studentId, Long teamId) {
        Team team = teamService.findById(teamId);

        if (!teamIsVacationTeam(team)) {
            StudentTeamAssociation existingAssociation = studentTeamAssociationRepository.findFirstTeamByStudentIdAndIsActiveIsTrue(studentId)
                    .orElse(null);

            if (existingAssociation != null && !existingAssociation.getTeam().equals(team)) {
                existingAssociation.setIsActive(false);
                existingAssociation.setEndDate(LocalDate.now());
                updateCountStudentInTeam(existingAssociation);
            }
        }
    }


    public void desactivateAssociationsInVacationTeam(VacationTeam vacationTeam) {
        LocalDate currentDate = LocalDate.now();

        if (currentDate.isAfter(vacationTeam.getEndDate())) {

            desactivateStudentVacationTeamAssociations(vacationTeam);
            vacationTeamService.updateTeamStudentCount(vacationTeam, 0);
            throw new DataException("A creche de férias (ID: " + vacationTeam.getId() +
                    ") já terminou. Data de término: " + vacationTeam.getEndDate());

        }
    }

    private void desactivateStudentVacationTeamAssociations(VacationTeam vacationTeam) {
        List<StudentTeamAssociation> studentAssociations = studentTeamAssociationRepository.findByTeamAndIsActive(vacationTeam, true);

        if (!studentAssociations.isEmpty()) {
            for (StudentTeamAssociation association : studentAssociations) {
                association.setIsActive(false);
                association.setEndDate(LocalDate.now());
                update(association);
            }
        }
    }



    private boolean isStudentAlreadyInTeam(Long stutendId, Long teamId) {
        return studentTeamAssociationRepository.existsByStudentIdAndTeamId(stutendId, teamId);
    }

    private boolean teamIsVacationTeam(Team team) {
        return team instanceof VacationTeam;
    }


    public boolean isTeamStudentRelationshipActive(Long studentId, Long teamId) {
        return studentTeamAssociationRepository.existsByStudentIdAndTeamIdAndIsActiveTrue(studentId, teamId);
    }



}
