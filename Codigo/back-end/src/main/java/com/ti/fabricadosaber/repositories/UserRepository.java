package com.ti.fabricadosaber.repositories;

import com.ti.fabricadosaber.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    
}
