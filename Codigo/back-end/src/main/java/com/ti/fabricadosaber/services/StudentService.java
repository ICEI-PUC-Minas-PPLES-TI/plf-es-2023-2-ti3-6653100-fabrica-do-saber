package com.ti.fabricadosaber.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.ti.fabricadosaber.models.Guardian;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

    public List<Student> listAllStudents() {
        try {
            return studentRepository.findAll();
        } catch(EmptyResultDataAccessException error) {
            throw new RuntimeException("Nenhum estudante cadastrado", error);
        }
    }

    public Set<Guardian> listGuardians(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estudante com o ID " + id + " não encontrado"));


        return student.getGuardians();
    }

    @Transactional
    public Student create(Student obj) {

        /*todo: essa verificao deveria ser uma funcao a parte. A funcao create deve apenas criar o obj*/
        if (obj.getGuardians().size() <= 2) {
            obj.setId(null);
            obj = this.studentRepository.save(obj);
            return obj;
        } else {
            throw new RuntimeException("Um estudante pode ter no máximo dois responsáveis.");
        }
    }

    public Student update(Student obj) {
        Student newObj = findById(obj.getId());

        if (obj.getGuardians().size() <= 2) {
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
