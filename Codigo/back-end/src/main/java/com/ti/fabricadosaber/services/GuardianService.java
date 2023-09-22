package com.ti.fabricadosaber.services;

import com.ti.fabricadosaber.models.Guardian;
import com.ti.fabricadosaber.repositories.GuardianRepository;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GuardianService {

    @Autowired
    private GuardianRepository guardianRepository;

    // @Autowired
    public Guardian findById(Long id) {
        // lidando com null usando Optional
        Optional<Guardian> guardian = this.guardianRepository.findById(id);

        return guardian.orElseThrow(() -> new RuntimeException(
                "Responsavel não encontrado! id: " + id + ", Tipo: " + Guardian.class.getName()));
    }

    @Transactional
    public Guardian create(Guardian obj) {
        obj.setId(null);
        return this.guardianRepository.save(obj);
    }

    @Transactional
    public Guardian update(Guardian obj) {

        Guardian newObj = findById(obj.getId());

        newObj.setFullName(obj.getFullName());
        newObj.setCpf(obj.getCpf());
        newObj.setEmail(obj.getEmail());
        newObj.setOccupation(obj.getOccupation());
        newObj.setPhoneNumber(obj.getPhoneNumber());
        newObj.setCompany(obj.getCompany());

        return this.guardianRepository.save(newObj);
    }

    public void delete(Long id) {
        Guardian guardian = findById(id);
        try {
            this.guardianRepository.delete(guardian);
        } catch (Exception error) {
            throw new RuntimeException("Não é possível excluir pois há entidades relacionadas");
        }
    }

    public List<Guardian> listAllGuardians() {
        try {
            return guardianRepository.findAll();
        } catch (EmptyResultDataAccessException ex) {
            throw new RuntimeException("Nenhum responsável cadastrado.", ex);
        }
    }

}
