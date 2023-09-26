package com.ti.fabricadosaber.repositories;

import com.ti.fabricadosaber.models.Father;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FatherRepository extends JpaRepository<Father, Long> {

}
