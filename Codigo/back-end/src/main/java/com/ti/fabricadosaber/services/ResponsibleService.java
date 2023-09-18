package com.ti.fabricadosaber.services;

import com.ti.fabricadosaber.models.Responsible;
import com.ti.fabricadosaber.models.Student;
import com.ti.fabricadosaber.repositories.ResponsibleRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public Responsible createResponsible(Responsible obj) {

        obj.setId(null);
        obj = this.responsibleRepository.save(obj);
        return obj;
    }

    // atualizando os dados do responsavel
    @Transactional
    public Responsible updaResponsible(Responsible obj) {

        Responsible newObj = findById(obj.getId());

        newObj.setNome(obj.getNome());
        newObj.setCpf(obj.getCpf());
        newObj.setEmail(obj.getEmail());
        newObj.setOccupation(obj.getOccupation());
        newObj.setPhoneNumber(obj.getPhoneNumber());
        newObj.setCompany(obj.getCompany());

        return this.responsibleRepository.save(newObj);
    }

    public void deleteResonsible(Long id) {
        Responsible responsible = findById(id);
        try {
            this.responsibleRepository.delete(responsible);
        } catch (Exception error) {
            throw new RuntimeException("Não é possível excluir pois há entidades relacionadas");
        }
    }

    public void delete(Long id) {
    }

    public void update(@Valid Student obj) {
    }
}
