package com.ti.fabricadosaber.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    @Autowired
    private GuardianService guardianService;

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

        Set<Guardian> guardianList = obj.getGuardians();

        if (guardianList.size() <= 2) {

            for(Guardian guardian:guardianList) {
                this.guardianService.create(guardian);
                obj.setGuardians(guardianList);
            }

            obj.setId(null);
            obj.setRegistrationDate(LocalDate.now());
            obj = this.studentRepository.save(obj);
            return obj;
        } else {
            throw new RuntimeException("Um estudante pode ter no máximo dois responsáveis.");
        }
    }

    public Student update(Student obj) {
        Student newObj = findById(obj.getId());

        if (obj.getGuardians().size() <= 2) {
            String[] ignoredProperties = {"id", "registrationDate"};
            BeanUtils.copyProperties(obj, newObj, ignoredProperties);
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
