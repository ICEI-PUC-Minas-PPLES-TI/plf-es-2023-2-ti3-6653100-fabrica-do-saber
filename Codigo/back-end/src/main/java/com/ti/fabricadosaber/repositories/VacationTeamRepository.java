package com.ti.fabricadosaber.repositories;

import com.ti.fabricadosaber.models.Student;
import com.ti.fabricadosaber.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ti.fabricadosaber.models.VacationTeam;

import java.util.List;

@Repository
public interface VacationTeamRepository extends JpaRepository<VacationTeam, Long> {

    @Query("SELECT t FROM Team t WHERE TYPE(t) = VacationTeam ")
    List<VacationTeam> findAllTeams();


}
