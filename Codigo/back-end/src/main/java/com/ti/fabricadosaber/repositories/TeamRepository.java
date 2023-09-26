package com.ti.fabricadosaber.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ti.fabricadosaber.models.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    
}
