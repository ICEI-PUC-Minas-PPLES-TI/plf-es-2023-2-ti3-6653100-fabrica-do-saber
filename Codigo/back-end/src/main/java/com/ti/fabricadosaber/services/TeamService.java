package com.ti.fabricadosaber.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.ti.fabricadosaber.models.Teacher;
import com.ti.fabricadosaber.models.Team;
import com.ti.fabricadosaber.repositories.TeamRepository;

import jakarta.transaction.Transactional;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeacherService teacherService;

    public Team findById(Long id) {
        Optional<Team> team = this.teamRepository.findById(id);
        return team.orElseThrow(() -> new RuntimeException(
            "Turma não encontrada! Id: " + id + ", Tipo: " + Team.class.getName()));
    }

    public List<Team> listAllTeams() {
        try {
            return teamRepository.findAll();
        } catch(EmptyResultDataAccessException error) {
            throw new RuntimeException("Nenhuma turma cadastrada", error);
        }
    }

    @Transactional
    public Team create(Team obj) {
        Teacher teacher = this.teacherService.findById(obj.getTeacher().getId());
        obj.setId(null);
        obj.setTeacher(teacher);
        obj = this.teamRepository.save(obj);
        return obj;
    }

    public Team update(Team obj) {
        Team newObj = findById(obj.getId());

        BeanUtils.copyProperties(obj, newObj, "id");

        return this.teamRepository.save(newObj);
    }

    public void delete(Long id) {
        Team team = findById(id);

        try {
            this.teamRepository.delete(team);
        } catch (Exception e) {
            throw new RuntimeException("Não é possível excluir pois há entidades relacionadas");
        }
    }
    
}
