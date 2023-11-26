package com.ti.fabricadosaber.repositories;

import com.ti.fabricadosaber.models.Student;
import com.ti.fabricadosaber.models.StudentTeamAssociation;
import com.ti.fabricadosaber.models.Team;
import com.ti.fabricadosaber.models.VacationTeam;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentTeamAssociationRepository extends CrudRepository<StudentTeamAssociation,
        StudentTeamAssociation.StudentTeamId> {

    Optional<StudentTeamAssociation> findByStudentAndTeam(Student student, Team team);

    List<StudentTeamAssociation> findByTeamAndIsActive(Team team, boolean isActive);


    @Query("SELECT sta.id.studentId FROM StudentTeamAssociation sta JOIN Team t ON sta.id.teamId = t.id WHERE t.id = :teamId AND TYPE(t) = Team AND sta.isActive = true")
    List<Long> findActiveStudentIdsByTeamId(@Param("teamId") Long teamId);


    @Query("SELECT t FROM StudentTeamAssociation sta " +
            "JOIN Team t ON sta.team.id = t.id " +
            "WHERE sta.id.studentId = :studentId AND sta.isActive = true")
    List<Team> findActiveTeamsByStudentId(@Param("studentId") Long studentId);



    @Query("SELECT t FROM StudentTeamAssociation sta " +
            "JOIN Team t ON sta.team.id = t.id " +
            "WHERE sta.id.studentId = :studentId AND sta.isActive = true AND TYPE(t) = Team")
    Team findActiveTeamByStudentId(@Param("studentId") Long studentId);



    @Query("SELECT t FROM StudentTeamAssociation sta " +
            "JOIN Team t ON sta.team.id = t.id " +
            "WHERE sta.id.studentId = :studentId AND sta.isActive = true AND TYPE(t) = VacationTeam")
    List<VacationTeam> findActiveVacationTeamByStudentId(@Param("studentId") Long studentId);



    //Ids dos Team e VacationTeam
    @Query("SELECT t.id FROM StudentTeamAssociation sta " +
            "JOIN Team t ON sta.team.id = t.id " +
            "WHERE sta.id.studentId = :studentId AND sta.isActive = true")
    List<Long> findActiveTeamIdsByStudentId(@Param("studentId") Long studentId);



    @Query("SELECT sta.id.studentId FROM StudentTeamAssociation sta JOIN Team t ON sta.id.teamId = t.id WHERE t.id = " +
            ":teamId AND TYPE(t) = VacationTeam AND sta.isActive = true")
    List<Long> findActiveStudentIdsByVacationTeamId(@Param("teamId") Long teamId);

    @Query("SELECT sta.id.studentId FROM StudentTeamAssociation sta " +
            "JOIN Team t ON sta.id.teamId = t.id " +
            "WHERE t.id = :teamId AND sta.isActive = true")
    List<Long> findActiveStudentIdsByTeamIdAndType(@Param("teamId") Long teamId);




    @Query("SELECT sta FROM StudentTeamAssociation sta JOIN Team t ON sta.id.teamId = t.id WHERE sta.id.studentId = :studentId AND TYPE(t) = Team AND sta.isActive = true")
        Optional<StudentTeamAssociation> findFirstTeamByStudentIdAndIsActiveIsTrue(@Param("studentId") Long studentId);


    @Query("SELECT sta FROM StudentTeamAssociation sta WHERE sta.id.studentId = :studentId AND sta.isActive = true")
    List<StudentTeamAssociation> findAllActiveAssociationsByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT DISTINCT t FROM StudentTeamAssociation sta JOIN Team t ON sta.id.teamId = t.id WHERE sta.id.studentId = :studentId AND sta.isActive = true")
    List<Team> findTeamsByStudentId(@Param("studentId") Long studentId);


    @Query("SELECT sta FROM StudentTeamAssociation sta WHERE sta.id.teamId = :teamId AND sta.isActive = true")
    List<StudentTeamAssociation> findAllActiveAssociationsByTeamId(@Param("teamId") Long teamId);

    @Query("SELECT sta.student FROM StudentTeamAssociation sta " +
            "JOIN Team t ON sta.team.id = t.id " +
            "WHERE t.id = :teamId AND TYPE(t) = Team AND sta.isActive = true")
    List<Student> findActiveStudentsByTeamId(@Param("teamId") Long teamId);

    @Query("SELECT sta.student FROM StudentTeamAssociation sta " +
            "JOIN Team t ON sta.team.id = t.id " +
            "WHERE t.id = :teamId AND TYPE(t) = VacationTeam AND sta.isActive = true")
    List<Student> findActiveStudentsByVacationTeamId(@Param("teamId") Long teamId);



    @Query("SELECT sta FROM StudentTeamAssociation sta WHERE sta.id.teamId = :teamId")
    List<StudentTeamAssociation> findAllAssociationsByTeamId(@Param("teamId") Long teamId);


    @Query("SELECT sta FROM StudentTeamAssociation sta WHERE sta.id.studentId = :studentId AND sta.isActive = false")
    List<StudentTeamAssociation> findAllInactiveAssociationsByStudentId(@Param("studentId") Long studentId);


    @Query("SELECT sta FROM StudentTeamAssociation  sta WHERE sta.id.teamId = :teamId AND sta.isActive = false")
    List<StudentTeamAssociation> findAllInactiveAssociationsByTeamId(@Param("teamId") Long TeamId);

    Integer countByTeamAndIsActiveIsTrue(VacationTeam vacationTeam);

    Integer countByTeamAndIsActiveIsTrue(Team team);
}


