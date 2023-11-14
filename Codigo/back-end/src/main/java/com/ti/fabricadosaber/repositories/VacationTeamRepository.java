package com.ti.fabricadosaber.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ti.fabricadosaber.models.VacationTeam;

@Repository
public interface VacationTeamRepository extends JpaRepository<VacationTeam, Long> {
    
}
