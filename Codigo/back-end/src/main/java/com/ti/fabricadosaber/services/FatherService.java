package com.ti.fabricadosaber.services;

import com.ti.fabricadosaber.models.Father;
import com.ti.fabricadosaber.repositories.FatherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FatherService {

    @Autowired
    private FatherRepository fatherRepository;

    public Father findById(Long id) {
        Optional<Father> father = this.fatherRepository.findById(id);
        return father.orElseThrow(() -> new RuntimeException(
                "Pai não encontrado! id: " + id + ", Tipo: " + Father.class.getName()
        ));
    }


    @Transactional
    public Father create(Father obj) {
        obj.setId(null);
        obj = this.fatherRepository.save(obj);
        return obj;
    }


    public Father update(Father obj) {
        Father newObj = findById(obj.getId());

        BeanUtils.copyProperties(obj, newObj, "id");

        return this.fatherRepository.save(newObj);

    }

    public void delete(Long id) {
       Father father = findById(id);
        // caso a entidade esteja relacionada a outra:
        try{
            this.fatherRepository.delete(father);
        } catch (Exception error) {
            throw new RuntimeException("Não é possível excluir pois há entidades relacionadas");
        }
    }



}
