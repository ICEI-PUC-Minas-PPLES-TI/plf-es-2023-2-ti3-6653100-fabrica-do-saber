package com.ti.fabricadosaber.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ti.fabricadosaber.models.Team;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query("SELECT t FROM Team t WHERE TYPE(t) = Team")
    List<Team> findAllTeams();

}
