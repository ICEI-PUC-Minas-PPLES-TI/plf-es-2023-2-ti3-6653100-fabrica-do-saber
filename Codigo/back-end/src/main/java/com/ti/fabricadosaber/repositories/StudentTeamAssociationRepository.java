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


    @Query("SELECT sta FROM StudentTeamAssociation sta JOIN Team t ON sta.id.teamId = t.id WHERE sta.id.studentId = :studentId AND TYPE(t) = Team AND sta.isActive = true")
        Optional<StudentTeamAssociation> findFirstTeamByStudentIdAndIsActiveIsTrue(@Param("studentId") Long studentId);


    @Query("SELECT sta FROM StudentTeamAssociation sta WHERE sta.id.studentId = :studentId AND sta.isActive = true")
    List<StudentTeamAssociation> findAllActiveAssociationsByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT DISTINCT t FROM StudentTeamAssociation sta JOIN Team t ON sta.id.teamId = t.id WHERE sta.id.studentId = :studentId AND sta.isActive = true")
    List<Team> findTeamsByStudentId(@Param("studentId") Long studentId);


    @Query("SELECT sta FROM StudentTeamAssociation sta WHERE sta.id.teamId = :teamId AND sta.isActive = true")
    List<StudentTeamAssociation> findAllActiveAssociationsByTeamId(@Param("teamId") Long teamId);

    @Query("SELECT sta FROM StudentTeamAssociation sta WHERE sta.id.teamId = :teamId")
    List<StudentTeamAssociation> findAllAssociationsByTeamId(@Param("teamId") Long teamId);


    @Query("SELECT sta FROM StudentTeamAssociation sta WHERE sta.id.studentId = :studentId AND sta.isActive = false")
    List<StudentTeamAssociation> findAllInactiveAssociationsByStudentId(@Param("studentId") Long studentId);


    @Query("SELECT sta FROM StudentTeamAssociation  sta WHERE sta.id.teamId = :teamId AND sta.isActive = false")
    List<StudentTeamAssociation> findAllInactiveAssociationsByTeamId(@Param("teamId") Long TeamId);

    Integer countByTeamAndIsActiveIsTrue(VacationTeam vacationTeam);

    Integer countByTeamAndIsActiveIsTrue(Team team);
}


