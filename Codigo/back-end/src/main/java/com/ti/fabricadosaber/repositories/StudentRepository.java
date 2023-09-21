package com.ti.fabricadosaber.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ti.fabricadosaber.models.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{
    
}
