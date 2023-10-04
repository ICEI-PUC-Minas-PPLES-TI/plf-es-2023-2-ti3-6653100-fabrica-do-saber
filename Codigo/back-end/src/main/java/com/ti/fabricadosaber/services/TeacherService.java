package com.ti.fabricadosaber.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.ti.fabricadosaber.models.Employee;
import com.ti.fabricadosaber.security.UserSpringSecurity;
import com.ti.fabricadosaber.services.exceptions.DataBindingViolationException;
import com.ti.fabricadosaber.services.exceptions.ObjectNotFoundException;
import com.ti.fabricadosaber.utils.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.ti.fabricadosaber.models.Teacher;
import com.ti.fabricadosaber.models.Team;
import com.ti.fabricadosaber.repositories.TeacherRepository;
import jakarta.transaction.Transactional;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    public Teacher findById(Long id) {
        Teacher teacher = this.teacherRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
            "Professor(a) não encontrado(a)! Id: " + id + ", Tipo: " + Teacher.class.getName()));

        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        SecurityUtils.checkUser(userSpringSecurity);

        return teacher;
    }

    public List<Teacher> listAllTeachers() {
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        SecurityUtils.checkUser(userSpringSecurity);
        List<Teacher> teacher = this.teacherRepository.findAll();
        if (teacher.isEmpty()) {
            throw new ObjectNotFoundException("Nenhum professor(a) cadastrado(a)");
        }
        return teacher;
    }

    public List<Team> listTeams(Long id) {
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        SecurityUtils.checkUser(userSpringSecurity);
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new RuntimeException(
            "Id: " + id + " não encontrado"
        ));

        return teacher.getTeams();
    }

    @Transactional
    public Teacher create(Teacher obj) {
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        SecurityUtils.checkUser(userSpringSecurity);
        obj.setId(null);
        obj.setRegistrationDate(LocalDate.now());
        obj = this.teacherRepository.save(obj);
        return obj;
    }

    public Teacher update(Teacher obj) {
        Teacher newObj = findById(obj.getId());

        BeanUtils.copyProperties(obj, newObj, "id", "registrationDate");

        return this.teacherRepository.save(newObj);
    }

    public void delete(Long id) {
        Teacher teacher = findById(id);

        try {
            this.teacherRepository.delete(teacher);
        } catch (Exception e) {
            throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas");
        }
    }
}
