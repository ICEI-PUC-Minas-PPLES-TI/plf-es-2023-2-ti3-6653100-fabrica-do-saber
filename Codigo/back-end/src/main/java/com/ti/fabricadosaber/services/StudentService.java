package com.ti.fabricadosaber.services;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import com.ti.fabricadosaber.models.Parent;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import com.ti.fabricadosaber.models.Student;
import com.ti.fabricadosaber.repositories.StudentRepository;
import javax.persistence.EntityNotFoundException;
import java.util.Set;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ParentService parentService;


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


    public Set<Parent> listParents(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estudante com o ID " + id + " não encontrado"));

        return student.getParents();
    }


    @Transactional
    public Student create(Student obj) {

        if (obj.getParents().size() < 2) {
            throw new RuntimeException("O estudante deve ter dois responsaveis.");
        }

        obj.setId(null);

        Set<Parent> createdParents = new HashSet<>();


        for (Parent parent : obj.getParents()) {
            Parent createdParent = parentService.create(parent);
            createdParents.add(createdParent);
        }

        obj.setRegistrationDate(LocalDate.now());
        obj.setParents(createdParents);
        obj = this.studentRepository.save(obj);
        return obj;
    }



    public Student update(Student obj) {

        Student newObj = findById(obj.getId());
        String[] ignoreProperties = {"id", "registrationDate"};

        for (Parent p : obj.getParents()) {
            this.parentService.update(p);
        }

        BeanUtils.copyProperties(obj, newObj, ignoreProperties);

        return this.studentRepository.save(newObj);
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
