package com.ti.fabricadosaber.services;

import java.util.List;
import java.util.Optional;

import jakarta.validation.ValidationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ti.fabricadosaber.models.Student;
import com.ti.fabricadosaber.repositories.StudentRepository;

import jakarta.transaction.Transactional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student findById(Long id) {
        Optional<Student> student = this.studentRepository.findById(id);
        return student.orElseThrow(() -> new RuntimeException(
                "Aluno não encontrado! Id: " + id + ", Tipo: " + Student.class.getName()));
    }

    public List<Student> findAll() {

        return studentRepository.findAll();
    }

    @Transactional
    public Student create(Student obj) {
        // Verificar se o número de responsáveis está dentro do limite
        if (obj.getResponsibles() != null && obj.getResponsibles().size() <= 2) {
            obj.setId(null);
            obj = this.studentRepository.save(obj);
            return obj;
        } else {
            throw new ValidationException("Um estudante pode ter no máximo dois responsáveis.");
        }
    }

/*    @Transactional
    public Student update(Student obj) {
        Student newObj = findById(obj.getId());
        newObj.setResponsible1(obj.getResponsible1());
        newObj.setResponsible2(obj.getResponsible2());
        newObj.setFullName(obj.getFullName());
        newObj.setYearRegistration(obj.getYearRegistration());
        newObj.setGrade(obj.getGrade());
        newObj.setEducation(obj.getEducation());
        newObj.setDateOfBirth(obj.getDateOfBirth());
        newObj.setCityBirth(obj.getCityBirth());
        newObj.setState(obj.getState());
        newObj.setNationality(obj.getNationality());
        newObj.setReligion(obj.getReligion());
        newObj.setRace(obj.getRace());
        return this.studentRepository.save(newObj);
    }*/

    public Student update(Student obj) {
        Student newObj = findById(obj.getId());

        if (obj.getResponsibles() != null && obj.getResponsibles().size() <= 2) {
            BeanUtils.copyProperties(obj, newObj, "id");
            return this.studentRepository.save(newObj);
        } else {
            throw new ValidationException("Um estudante pode ter no máximo dois responsáveis.");
        }
    }




    public void delete(Long id) {
        Student student = findById(id);
        try {
            this.studentRepository.delete(student);
        } catch (Exception e) {
            throw new RuntimeException("Não é possível excluir pois há entidades relacionadas");
        }
    }
}
