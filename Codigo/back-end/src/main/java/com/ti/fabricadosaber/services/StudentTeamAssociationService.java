package com.ti.fabricadosaber.services;

import com.ti.fabricadosaber.exceptions.DataException;
import com.ti.fabricadosaber.exceptions.EntityNotFoundException;
import com.ti.fabricadosaber.models.Student;
import com.ti.fabricadosaber.models.StudentTeamAssociation;
import com.ti.fabricadosaber.models.Team;
import com.ti.fabricadosaber.models.VacationTeam;
import com.ti.fabricadosaber.repositories.StudentTeamAssociationRepository;
import com.ti.fabricadosaber.services.exceptions.DataBindingViolationException;
import com.ti.fabricadosaber.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentTeamAssociationService {

    @Autowired
    private StudentTeamAssociationRepository studentTeamAssociationRepository;

    private static boolean isVacationTeam;

    @Autowired
    private TeamService teamService;

    @Autowired
    private StudentService studentService;

    @Autowired
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


    // TODO(1): método cadastro de turmas e alunos
    public StudentTeamAssociation enrollStudentOnTeam(StudentTeamAssociation studentTeamAssociation, boolean enrollStudent) {

        Long studentId = studentTeamAssociation.getStudent().getId();
        Long teamId = studentTeamAssociation.getTeam().getId();
        isVacationTeam = teamIsVacationTeam(studentTeamAssociation.getTeam());


        if (!enrollStudent)
            if (!isVacationTeam)
                disableExistingAssociation(studentId, teamId);

         else
            if (isVacationTeam)
                checkTeamDeadline((VacationTeam) studentTeamAssociation.getTeam());


        create(studentTeamAssociation);

        return studentTeamAssociation;
    }



    // TODO(2): atualização dos relacionamentos de um estudante com as turmas dele
    public void updateStudentOnAssociation(Set<Long> teamIds, Student student) {


            List<Long> teamIdsList = (teamIds != null) ?
                    new ArrayList<>(teamIds) : Collections.emptyList();
            Team team;

            // Desativar todas as relações que existem no BD e não existe na lista
            disableById(teamIdsList, student);

            if(!teamIdsList.isEmpty()) {

            // Lista dos que estão na lista e não tem no BD (criar novas relações)
            List<Long> newTeams = findUnrelatedTeamIds(teamIdsList, student);

            if(newTeams != null && !newTeams.isEmpty())
                for (Long newTeam : newTeams) {
                    team = teamService.findById(newTeam);
                    if(teamIsVacationTeam(team))
                        checkTeamDeadline((VacationTeam) team);
                    create(new StudentTeamAssociation(student, team));
                }

            // Relações que existem na lista e no banco, mas estão inativas.
            List<Long> updateTeams = findInactiveRelatedTeamIds(teamIdsList, student);

            if(updateTeams != null && !updateTeams.isEmpty()) {
                for(Long updateTeam : updateTeams) {

                    team = teamService.findById(updateTeam);

                    Optional<StudentTeamAssociation> existingStudentTeamAssociation =
                            studentTeamAssociationRepository.findByStudentAndTeam(student,
                                    team);
                    if(teamIsVacationTeam(team))
                        checkTeamDeadline((VacationTeam) team);
                    existingStudentTeamAssociation.ifPresent(this::update);
                }
            }
        }

    }


    //TODO(3): atualizar relacionamentos de uma turma (ou creche de férias) com os estudantes dela
    public void updateTeamOnAssociation(Set<Long> studentIds, Team team) {

        isVacationTeam = teamIsVacationTeam(team);

        if(isVacationTeam)
            checkTeamDeadline((VacationTeam) team);

        Student student;
        List<Long> studentIdsList = (studentIds != null) ?
                new ArrayList<>(studentIds) : Collections.emptyList();

        checkNullOrEmpty(studentIdsList, team);
        // Desativar todas as relações que existem no banco de dados mas não existem na lista
        disableById(studentIdsList, team);

        if(studentIds == null || studentIds.isEmpty()) {
            if (isVacationTeam) {
                VacationTeam convertTeam = (VacationTeam) team;
                vacationTeamService.updateTeamStudentCount(convertTeam,
                        studentTeamAssociationRepository.countByTeamAndIsActiveIsTrue(convertTeam));
            } else {
                teamService.updateTeamStudentCount(team,
                        studentTeamAssociationRepository.countByTeamAndIsActiveIsTrue(team));
            }
        }

        if(!studentIdsList.isEmpty()) {

            List<Long> newStudents = findUnrelatedStudentIds(studentIdsList, team);


            if(newStudents != null && !newStudents.isEmpty()) {

                if(!isVacationTeam)
                    disableStudentAssociationList(newStudents);

                for (Long newStudent : newStudents) {
                    student = studentService.findById(newStudent);
                    create(new StudentTeamAssociation(student, team));
                }

            }

            // Relações que existem na lista e no Banco de Dados, mas estão inativas
            List<Long> updateStudents = findInactiveRelatedTeamIds(studentIdsList, team);

            if(updateStudents != null && !updateStudents.isEmpty()) {

                if(!isVacationTeam)
                    disableStudentAssociationList(updateStudents);

                for (Long updateStudent : updateStudents) {
                    student = studentService.findById(updateStudent);
                    Optional<StudentTeamAssociation> existingStudentTeamAssociation =
                            studentTeamAssociationRepository.findByStudentAndTeam(student,
                                    team);

                    existingStudentTeamAssociation.ifPresent(this::update);

                }

            }
        }

    }

    public void checkNullOrEmpty(List<Long> studentIds, Team team) {

        if(studentIds == null || studentIds.isEmpty()) {
            if (isVacationTeam) {
                VacationTeam convertTeam = (VacationTeam) team;
                vacationTeamService.updateTeamStudentCount(convertTeam,
                        studentTeamAssociationRepository.countByTeamAndIsActiveIsTrue(convertTeam));
            } else {
                teamService.updateTeamStudentCount(team,
                        studentTeamAssociationRepository.countByTeamAndIsActiveIsTrue(team));
            }
        }
    }

    // Precisa garantir que ...
    public void disableStudentAssociationList(List<Long> students) {
        for (Long student : students) {

            Optional<StudentTeamAssociation> studentTeamAssociationOptional =
                    studentTeamAssociationRepository.findFirstTeamByStudentIdAndIsActiveIsTrue(student);

            studentTeamAssociationOptional.ifPresent(studentTeamAssociation -> {
                studentTeamAssociation.setIsActive(false);
                studentTeamAssociation.setEndDate(LocalDate.now());
                updateCountStudentInTeam(studentTeamAssociation);
            });
        }

    }





    public List<Long> findInactiveRelatedTeamIds(List<Long> studentIds, Team team) {

        // Ids dos associações que estão inativas
        List<StudentTeamAssociation> associations =
                studentTeamAssociationRepository.findAllInactiveAssociationsByTeamId(team.getId());

        //IDs das estudantes que está relacionado com o turma, mas tem relacionamento inativo
        List<Long> relatedStudentIds = associations.stream()
                .map(association -> association.getStudent().getId())
                .toList();

        //IDs de estudantes que tem na lista e tem no banco de dados e que está inativo
        List<Long> relatedTeam = studentIds.stream()
                .filter(relatedStudentIds::contains)
                .collect(Collectors.toList());

        return relatedTeam;
    }



    private void updateCountStudentInTeam(StudentTeamAssociation studentTeamAssociation) {
        isVacationTeam = teamIsVacationTeam(studentTeamAssociation.getTeam());
        if(isVacationTeam) {
            VacationTeam convertTeam = (VacationTeam) studentTeamAssociation.getTeam();

            vacationTeamService.updateTeamStudentCount(convertTeam,
                    studentTeamAssociationRepository.countByTeamAndIsActiveIsTrue(convertTeam));
        } else {
            teamService.updateTeamStudentCount(studentTeamAssociation.getTeam(),
                    studentTeamAssociationRepository.countByTeamAndIsActiveIsTrue(studentTeamAssociation.getTeam()));
        }
    }

    private void disableExistingAssociation(Long studentId, Long teamId) {
        Team team = teamService.findById(teamId);
        isVacationTeam = teamIsVacationTeam(team);
        if (!isVacationTeam) {
            StudentTeamAssociation existingAssociation = studentTeamAssociationRepository.findFirstTeamByStudentIdAndIsActiveIsTrue(studentId)
                    .orElse(null);

            if (existingAssociation != null && !existingAssociation.getTeam().equals(team)) {
                existingAssociation.setIsActive(false);
                existingAssociation.setEndDate(LocalDate.now());
                updateCountStudentInTeam(existingAssociation);
            }
        }
    }


    public void checkTeamDeadline(VacationTeam vacationTeam) {
        LocalDate currentDate = LocalDate.now();

        if (currentDate.isAfter(vacationTeam.getEndDate())) {

            disableStudentVacationTeamAssociations(vacationTeam);
            vacationTeamService.updateTeamStudentCount(vacationTeam, 0);
            throw new DataException("A creche de férias (ID: " + vacationTeam.getId() +
                    ") já terminou. Data de término: " + vacationTeam.getEndDate());

        }
    }

    private void disableStudentVacationTeamAssociations(VacationTeam vacationTeam) {
        List<StudentTeamAssociation> studentAssociations = studentTeamAssociationRepository.findByTeamAndIsActive(vacationTeam, true);

        if (!studentAssociations.isEmpty()) {
            for (StudentTeamAssociation association : studentAssociations) {
                association.setIsActive(false);
                association.setEndDate(LocalDate.now());

            }
        }
        studentTeamAssociationRepository.saveAll(studentAssociations);
    }


    // Associações que não estão na lista serão desativadas
    public void disableById(List<Long> studentIds, Team team) {

        boolean isVacationTeam = teamIsVacationTeam(team);
        List<StudentTeamAssociation> existingAssociations =
                studentTeamAssociationRepository.findAllActiveAssociationsByTeamId(team.getId());

        for (StudentTeamAssociation association : existingAssociations) {

            if (studentIds == null || studentIds.isEmpty() || !studentIds.contains(association.getStudent().getId())) {
                association.setIsActive(false);
                association.setEndDate(LocalDate.now());
                updateCountStudentInTeam(association);
            }
        }


        studentTeamAssociationRepository.saveAll(existingAssociations);
    }



    public void disableById(List<Long> teamIds, Student student) {
        List<StudentTeamAssociation> associations = studentTeamAssociationRepository.findAllActiveAssociationsByStudentId(student.getId());

        for (StudentTeamAssociation association : associations) {

            if (teamIds == null || teamIds.isEmpty() || !teamIds.contains(association.getTeam().getId())) {
                association.setIsActive(false);
                association.setEndDate(LocalDate.now());
                updateCountStudentInTeam(association);
            }
        }

        studentTeamAssociationRepository.saveAll(associations);
    }


    public List<Long> findUnrelatedStudentIds(List<Long> studentIds, Team team) {

        // Obtém todas as associações ativas e inativas
        List<StudentTeamAssociation> existingAssociations =
                studentTeamAssociationRepository.findAllAssociationsByTeamId((team.getId()));

        //IDs de todos os estudantes que está relacionado com a turma de forma ativa ou inativa
        List<Long> relatedStudentIds = existingAssociations.stream()
                .map(association -> association.getStudent().getId())
                .toList();

        // Ids de estudantes que tem na lista, mas não está relacionado com a turma no banco
        List<Long> unrelatedStudentIds = studentIds.stream()
                .filter(studentId -> !relatedStudentIds.contains(studentId))
                .toList();

        return unrelatedStudentIds;
    }

    public List<Long> findUnrelatedTeamIds(List<Long> teamIds, Student student) {

        // Obtém todas as associações ativas e inativas do estudante
        List<StudentTeamAssociation> associations = studentTeamAssociationRepository.findAllAssociationsByStudentId(student.getId());


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


    public List<Long> findInactiveRelatedTeamIds(List<Long> teamIds, Student student) {
        List<StudentTeamAssociation> associations =
                studentTeamAssociationRepository.findAllInactiveAssociationsByStudentId(student.getId());


        //IDs das turmas que está relacionado com inativo
        List<Long> relatedTeamIds = associations.stream()
                .map(association -> association.getTeam().getId())
                .toList();

        //IDs de turmas que tem na lista e tem no banco de dados
        List<Long> relatedTeam = teamIds.stream()
                .filter(teamId -> relatedTeamIds.contains(teamId))
                .collect(Collectors.toList());

        return relatedTeam;
    }


    private boolean teamIsVacationTeam(Team team) {
        return team instanceof VacationTeam;
    }


    public List<Team> teamsAssociatedWithTheStudent(Student student) {
        return studentTeamAssociationRepository.findDistinctActiveTeamsByStudentId(student.getId());
    }


    public List<Long> findStudentIdsByTeamId(Long teamId) {
        return studentTeamAssociationRepository.findActiveStudentIdsByTeamId(teamId);
    }

    public List<Long> findTeamsAndVacationTeams(Long studentId) {
        return studentTeamAssociationRepository.findActiveTeamIdsByStudentId(studentId);
    }



    public List<Long> findStudentIdsByVacationTeamId(Long vacationTeamId) {
        return studentTeamAssociationRepository.findActiveStudentIdsByVacationTeamId(vacationTeamId);
    }

    public List<Long> findStudentsIdsByTeamAndVacationTeamId(Long teamId) {
        return studentTeamAssociationRepository.findActiveStudentIdsByTeamIdAndType(teamId);
    }

    public Team findTeamOfStudent(Long studentId) {
        return studentTeamAssociationRepository.findActiveTeamByStudentId(studentId);
    }


    public List<Student> findStudentsActiveOnTeam(Long teamId) {
        return studentTeamAssociationRepository.findActiveStudentsByTeamId(teamId);
    }

    public List<Student> findStudentsActiveOnVacationTeam(Long vacationId) {
        return studentTeamAssociationRepository.findActiveStudentsByVacationTeamId(vacationId);
    }

    public List<VacationTeam> findVacationTeamOfStudent(Long studentId) {
        return studentTeamAssociationRepository.findActiveVacationTeamByStudentId(studentId);
    }

    public List<Team> findTeamAndVacationTeamOfStudent(Long studentId) {
        return studentTeamAssociationRepository.findActiveTeamsByStudentId(studentId);
    }

    public List<StudentTeamAssociation> findAllAssociationsByStudent(Long studentId) {
        return studentTeamAssociationRepository.findAllAssociationsByStudentId(studentId);
    }


    public void delete(StudentTeamAssociation.StudentTeamId id) {
        StudentTeamAssociation studentTeamAssociation = findById(id);
        try {
            this.studentTeamAssociationRepository.delete(studentTeamAssociation);
        } catch (Exception e) {
            throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas");
        }
    }

    public void deleteStudent(Long studentId) {
        List<StudentTeamAssociation> studentTeamAssociations = findAllAssociationsByStudent(studentId);
        for (StudentTeamAssociation studentTeamAssociation : studentTeamAssociations) {
            delete(studentTeamAssociation.getId());
        }
    }



}
