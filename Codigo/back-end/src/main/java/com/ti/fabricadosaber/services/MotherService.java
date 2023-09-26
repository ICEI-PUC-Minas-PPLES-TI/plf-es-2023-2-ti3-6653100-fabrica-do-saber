package com.ti.fabricadosaber.services;


import com.ti.fabricadosaber.models.Mother;
import com.ti.fabricadosaber.repositories.MotherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MotherService {

    @Autowired
    private MotherRepository motherRepository;

    public Mother findById(Long id) {
        Optional<Mother> mother = this.motherRepository.findById(id);

        return mother.orElseThrow(() -> new RuntimeException(
                "Mãe não encontrado! id: " + id + ", Tipo: " + Mother.class.getName()
        ));
    }

    @Transactional
    public Mother create(Mother obj) {
        obj.setId(null);
        obj = this.motherRepository.save(obj);
        return obj;
    }


    public Mother update (Mother obj) {
        Mother newObj = findById(obj.getId());

        BeanUtils.copyProperties(obj, newObj, "id");

        return this.motherRepository.save(newObj);
    }

    public void delete(Long id) {
        Mother mother = findById(id);

        try{
            this.motherRepository.delete(mother);
        } catch (Exception error) {
            throw new RuntimeException("Não é possível excluir pois há entidades relacionadas");
        }
    }

}
