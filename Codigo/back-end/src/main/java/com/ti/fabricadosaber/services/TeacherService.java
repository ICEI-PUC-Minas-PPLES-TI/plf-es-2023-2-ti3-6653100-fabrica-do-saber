package com.ti.fabricadosaber.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.ti.fabricadosaber.models.Teacher;
import com.ti.fabricadosaber.repositories.TeacherRepository;
import jakarta.transaction.Transactional;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    public Teacher findById(Long id) {
        Optional<Teacher> teacher = this.teacherRepository.findById(id);
        return teacher.orElseThrow(() -> new RuntimeException(
            "Professor não encontrado! Id: " + id + ", Tipo: " + Teacher.class.getName()));
    }

    public List<Teacher> listAllTeachers() {
        try {
            return teacherRepository.findAll();
        } catch(EmptyResultDataAccessException error) {
            throw new RuntimeException("Nenhum professor cadastrado", error);
        }
    }

    @Transactional
    public Teacher create(Teacher obj) {
        obj.setId(null);
        obj = this.teacherRepository.save(obj);
        return obj;
    }

    public Teacher update(Teacher obj) {
        Teacher newObj = findById(obj.getId());

        BeanUtils.copyProperties(obj, newObj, "id");

        return this.teacherRepository.save(newObj);
    }

    public void delete(Long id) {
        Teacher teacher = findById(id);

        try {
            this.teacherRepository.delete(teacher);
        } catch (Exception e) {
            throw new RuntimeException("Não é possível excluir pois há entidades relacionadas");
        }
    }
}