package com.ti.fabricadosaber.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ti.fabricadosaber.models.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
    
}
