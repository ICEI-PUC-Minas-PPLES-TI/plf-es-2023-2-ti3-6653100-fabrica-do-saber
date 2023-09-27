package com.ti.fabricadosaber.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ti.fabricadosaber.models.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long>{
    
}
