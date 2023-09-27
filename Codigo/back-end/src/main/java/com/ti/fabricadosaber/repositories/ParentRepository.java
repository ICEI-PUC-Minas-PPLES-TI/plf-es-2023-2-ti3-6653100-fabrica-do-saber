package com.ti.fabricadosaber.repositories;

import com.ti.fabricadosaber.models.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentRepository extends JpaRepository<Parent, Long> {
    boolean existsByCpf(String cpf);

    Parent findByCpf(String cpf);
}
