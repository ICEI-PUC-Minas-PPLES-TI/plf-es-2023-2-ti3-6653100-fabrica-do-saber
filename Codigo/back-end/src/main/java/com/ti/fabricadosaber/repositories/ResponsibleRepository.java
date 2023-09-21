package com.ti.fabricadosaber.repositories;

import com.ti.fabricadosaber.models.Responsible;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//import java.util.List;


@Repository
public interface ResponsibleRepository extends JpaRepository<Responsible, Long> {

    
}
