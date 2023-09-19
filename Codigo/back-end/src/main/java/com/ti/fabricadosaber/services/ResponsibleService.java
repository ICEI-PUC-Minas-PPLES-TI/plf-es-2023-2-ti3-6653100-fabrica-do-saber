package com.ti.fabricadosaber.services;

import com.ti.fabricadosaber.models.Responsible;
import com.ti.fabricadosaber.repositories.ResponsibleRepository;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResponsibleService {

    @Autowired
    private ResponsibleRepository responsibleRepository;

    // @Autowired
    public Responsible findById(Long id) {
        // lidando com null usando Optional
        Optional<Responsible> responsible = this.responsibleRepository.findById(id);

        return responsible.orElseThrow(() -> new RuntimeException(
                "Responsavel não encontrado! id: " + id + ", Tipo: " + Responsible.class.getName()));
    }

    @Transactional
    public Responsible create(Responsible obj) {

        obj.setId(null);
        obj = this.responsibleRepository.save(obj);
        return obj;
    }


    @Transactional
    public Responsible update(Responsible obj) {

        Responsible newObj = findById(obj.getId());

        newObj.setName(obj.getName());
        newObj.setCpf(obj.getCpf());
        newObj.setEmail(obj.getEmail());
        newObj.setOccupation(obj.getOccupation());
        newObj.setPhoneNumber(obj.getPhoneNumber());
        newObj.setCompany(obj.getCompany());

        return this.responsibleRepository.save(newObj);
    }

    public void delete(Long id) {
        Responsible responsible = findById(id);
        try {
            this.responsibleRepository.delete(responsible);
        } catch (Exception error) {
            throw new RuntimeException("Não é possível excluir pois há entidades relacionadas");
        }
    }


    public List<Responsible> listAllResponsibles() {
        try {
            return responsibleRepository.findAll();
        } catch (EmptyResultDataAccessException ex) {
            throw new RuntimeException("Nenhum responsável cadastrado.", ex);
        }
    }

}
