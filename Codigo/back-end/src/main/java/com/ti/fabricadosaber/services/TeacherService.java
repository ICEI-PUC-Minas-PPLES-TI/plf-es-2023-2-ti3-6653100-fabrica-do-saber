package com.ti.fabricadosaber.services;

import java.time.LocalDate;
import java.util.List;

import com.ti.fabricadosaber.exceptions.EntityNotFoundException;
import com.ti.fabricadosaber.services.exceptions.DataBindingViolationException;
import com.ti.fabricadosaber.utils.SecurityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.ti.fabricadosaber.models.Teacher;
import com.ti.fabricadosaber.models.Team;
import com.ti.fabricadosaber.repositories.TeacherRepository;
import jakarta.transaction.Transactional;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    @Lazy
    private TeamService teamService;

   public Teacher findById(Long id) {
        Teacher teacher = this.teacherRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
            "Professor(a) não encontrado(a)! Id: " + id + ", Tipo: " + Teacher.class.getName()));

        SecurityUtil.checkUser();

        return teacher;
    }

    public List<Teacher> listAllTeachers() {
        SecurityUtil.checkUser();

        List<Teacher> teacher = this.teacherRepository.findAll();
        if (teacher.isEmpty()) {
            throw new EntityNotFoundException("Nenhum professor(a) cadastrado(a)");
        }
        return teacher;
    }

    public List<Team> listTeams(Long id) {
        SecurityUtil.checkUser();

        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
            "Id: " + id + " não encontrado"
        ));

        return teacher.getTeams();
    }

    @Transactional
    public Teacher create(Teacher obj) {
        SecurityUtil.checkUser();

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
            removeTeacherFromTeam(teacher);
            this.teacherRepository.delete(teacher);
        } catch (Exception e) {
            throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas");
        }
    }

    public void removeTeacherFromTeam (Teacher teacher) {
       List<Team> teams = teacher.getTeams();
       teams.forEach(x -> teamService.teacherExcluded(x));
    }



}


