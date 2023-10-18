package com.ti.fabricadosaber.services;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import com.ti.fabricadosaber.exceptions.EntityNotFoundException;
import com.ti.fabricadosaber.exceptions.TwoParentsException;
import com.ti.fabricadosaber.models.Parent;
import com.ti.fabricadosaber.security.UserSpringSecurity;
import com.ti.fabricadosaber.services.exceptions.DataBindingViolationException;
import com.ti.fabricadosaber.utils.SecurityUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.ti.fabricadosaber.models.Student;
import com.ti.fabricadosaber.models.Team;
import com.ti.fabricadosaber.repositories.StudentRepository;

import java.util.Set;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    @Lazy
    private TeamService teamService;


    @Autowired
    private ParentService parentService;


    public Student findById(Long id) {
        SecurityUtils.checkUser();

        Student student =
                this.studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                        "Aluno não encontrado! Id: " + id + ", Tipo: " + Student.class.getName()));

        return student;
    }

    public List<Student> listAllStudents() {
        SecurityUtils.checkUser();

        List<Student> students = this.studentRepository.findAll();
        if (students.isEmpty()) {
            throw new EntityNotFoundException("Nenhum estudante cadastrado");
        }
        return students;
    }

    public Set<Parent> listParents(Long id) {
        SecurityUtils.checkUser();

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estudante com o ID " + id + " não encontrado"));

        return student.getParents();
    }

    @Transactional
    public Student create(Student obj) {
        SecurityUtils.checkUser();

        twoParents(obj);
        Team team = this.teamService.findById(obj.getTeam().getId());
        obj.setId(null);
        obj.setTeam(team);
        Set<Parent> parents = saveParents(obj);
        obj.setParents(parents);
        obj.setRegistrationDate(LocalDate.now());

        Student createdStudent = studentRepository.save(obj);
        this.teamService.updateTeamStudentCount(team);

        return createdStudent;
    }

    public Set<Parent> saveParents(Student obj) {
        String[] ignoreProperties = { "id", "registrationDate" };
        Set<Parent> parents = new HashSet<>();

        Parent currentParent;
        for (Parent parent : obj.getParents()) {
            String cpfParent = parent.getCpf();

            if (parentService.existsByCpf(cpfParent)) {
                currentParent = this.parentService.findByCpf(cpfParent);
                BeanUtils.copyProperties(parent, currentParent, ignoreProperties);
                currentParent = this.parentService.update(currentParent);
            } else {
                currentParent = parentService.create(parent);
            }
            parents.add(currentParent);
        }

        return parents;
    }

    public void twoParents(Student obj) {
        if (obj.getParents().size() != 2) {
            throw new TwoParentsException("O estudante deve ter dois responsáveis.");
        }
    }

    public Student update(Student obj) {
        twoParents(obj);

        Student newObj = findById(obj.getId());
        String[] ignoreProperties = { "id", "registrationDate", "parents" };

        Team oldTeam = newObj.getTeam();
        Team newTeam = this.teamService.findById(obj.getTeam().getId());

        BeanUtils.copyProperties(obj, newObj, ignoreProperties);

        Set<Parent> updatedParents = saveParents(obj);
        newObj.setParents(updatedParents);
        newObj.setRegistrationDate(LocalDate.now());
        newObj.setTeam(newTeam);

        if (oldTeam != null && !oldTeam.equals(newTeam)) {
            oldTeam.getStudents().remove(newObj);
            this.teamService.updateTeamStudentCount(oldTeam);
        }

        newTeam.getStudents().add(newObj);
        this.teamService.updateTeamStudentCount(newTeam);

        return studentRepository.save(newObj);
    }


    public void delete(Long id) {
        Student student = findById(id);
        try {
            this.studentRepository.delete(student);
            this.teamService.updateStudent(student);
        } catch (Exception e) {
            throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas");
        }
    }
}
