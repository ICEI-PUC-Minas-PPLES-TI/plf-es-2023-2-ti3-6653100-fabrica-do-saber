package com.ti.fabricadosaber.services;

import com.ti.fabricadosaber.models.Parent;
import com.ti.fabricadosaber.models.Student;
import com.ti.fabricadosaber.repositories.ParentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ParentService {

    @Autowired
    private ParentRepository parentRepository;

    public Parent findById(Long id) {
        Optional<Parent> parent = this.parentRepository.findById(id);
        return parent.orElseThrow(() -> new RuntimeException(
                "Parente não encontrado! Id: " + id + ", Tipo: " + Student.class.getName()));
    }

    @Transactional
    public Parent create(Parent obj) {

        obj.setId(null);
        obj.setRegistrationDate(LocalDate.now());
        return this.parentRepository.save(obj);
    }

    public Parent update(Parent obj) {

        Parent newObj = findById(obj.getId());
        String[] ignoredProperties = {"id"};
        BeanUtils.copyProperties(obj, newObj, ignoredProperties);

        return this.parentRepository.save(newObj);
    }

    public void delete(Long id) {

        Parent parent = findById(id);

        try {
            this.parentRepository.delete(parent);
        } catch (Exception e) {
            throw new RuntimeException("Não é possível excluir pois há entidades relacionadas");
        }
    }

}
