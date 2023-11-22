package com.ti.fabricadosaber.services;

import com.ti.fabricadosaber.exceptions.DataException;
import com.ti.fabricadosaber.exceptions.EntityNotFoundException;
import com.ti.fabricadosaber.models.Student;
import com.ti.fabricadosaber.models.StudentTeamAssociation;
import com.ti.fabricadosaber.models.Team;
import com.ti.fabricadosaber.models.VacationTeam;
import com.ti.fabricadosaber.repositories.StudentTeamAssociationRepository;
import com.ti.fabricadosaber.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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



    // TODO(1): método cadastro de turmas e alunos
    public StudentTeamAssociation enrollStudentOnTeam(StudentTeamAssociation studentTeamAssociation, boolean enrollStudent) {

        Long studentId = studentTeamAssociation.getStudent().getId();
        Long teamId = studentTeamAssociation.getTeam().getId();
        boolean isVacationTeam = teamIsVacationTeam(studentTeamAssociation.getTeam());

        if(!enrollStudent && !isVacationTeam)
            desactivateExistingAssociation(studentId, teamId);
        else if(enrollStudent && isVacationTeam)
            desactivateAssociationsInVacationTeam((VacationTeam) studentTeamAssociation.getTeam());

        create(studentTeamAssociation);

        return studentTeamAssociation;
    }


    // TODO(2): atualização dos relacionamentos de um estudante com as turmas
    public void updateStudentOnAssociation(Set<Long> teamIds, Student student) {

        List<Long> teamIdsList = new ArrayList<>(teamIds);
        Team team;

        // Desativar todas as relações que existem no BD e não existe na lista
        disableById(teamIdsList, student);


        // Lista dos que estão na lista e não tem no BD (criar novas relações)
        List<Long> newTeams = findUnrelatedTeamIds(teamIdsList, student);


        for (Long newTeam : newTeams) {
            team = teamService.findById(newTeam);
            create(new StudentTeamAssociation(student, team));
        }

        // Relações que existem na lista e no banco, mas estão inativas.
        List<Long> updateTeams = findInactiveRelatedTeamIds(teamIdsList, student);

        if(updateTeams != null && !updateTeams.isEmpty()) {
            for(Long updateTeam : newTeams) {
                team = teamService.findById(updateTeam);
                StudentTeamAssociation studentTeamAssociation = new StudentTeamAssociation(student, team);
                update(studentTeamAssociation);
            }
        }

    }

    //TODO(3): atualizar relacionamentos de uma turma com os estudantes dela
    public void updateTeamOnAssociation(Set<Long> studentIds, Team team) {
        // Fazer código
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

            }
        }
        studentTeamAssociationRepository.saveAll(studentAssociations);
    }



    public void disableById(List<Long> teamIds, Student student) {
        List<StudentTeamAssociation> associations = studentTeamAssociationRepository.findAllActiveAssociationsByStudentId(student.getId());

        if(associations != null && !associations.isEmpty())
            for (StudentTeamAssociation association : associations) {
                    if (!teamIds.contains(association.getTeam().getId())) {
                    association.setIsActive(false);
                    association.setEndDate(LocalDate.now());
                    updateCountStudentInTeam(association);
                }
            }

        studentTeamAssociationRepository.saveAll(associations);
    }

    public List<Long> findUnrelatedTeamIds(List<Long> teamIds, Student student) {

        // Obtém todas as associações ativas do estudante
        List<StudentTeamAssociation> associations = studentTeamAssociationRepository.findAllActiveAssociationsByStudentId(student.getId());


        // IDs das turmas que está relacionado com o estudante
        List<Long> relatedTeamIds = associations.stream()
                .map(association -> association.getTeam().getId())
                .collect(Collectors.toList());

        // IDs de turmas que tem na lista mas não tem no banco
        List<Long> unrelatedTeamIds = teamIds.stream()
                .filter(teamId -> !relatedTeamIds.contains(teamId))
                .collect(Collectors.toList());

        return unrelatedTeamIds;
    }

    //find inactive related team ids
    public List<Long> findInactiveRelatedTeamIds(List<Long> teamIds, Student student) {
        List<StudentTeamAssociation> associations =
                studentTeamAssociationRepository.findAllInactiveAssociationsByStudentId(student.getId());


        //IDs das turmas que está relacionado com inativo
        List<Long> relatedTeamIds = associations.stream()
                .map(association -> association.getTeam().getId())
                .collect(Collectors.toList());

        //IDs de turmas que tem na lista e tem no banco de dados
        List<Long> relatedTeam = teamIds.stream()
                .filter(teamId -> relatedTeamIds.contains(teamId))
                .collect(Collectors.toList());

        return relatedTeam; //turmas que estão relacionadas ao estudante que estão inativas e na lista passada
    }


    private boolean teamIsVacationTeam(Team team) {
        return team instanceof VacationTeam;
    }


}
