package com.ti.fabricadosaber.services;


import java.util.List;
import java.util.Set;
import com.ti.fabricadosaber.exceptions.EntityNotFoundException;
import com.ti.fabricadosaber.models.*;
import com.ti.fabricadosaber.services.exceptions.DataBindingViolationException;
import com.ti.fabricadosaber.services.interfaces.TeamOperations;
import com.ti.fabricadosaber.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.ti.fabricadosaber.repositories.TeamRepository;
import jakarta.transaction.Transactional;

@Service
public class TeamService implements TeamOperations {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    @Lazy
    private StudentService studentService;

    @Autowired
    @Lazy
    private StudentTeamAssociationService studentTeamAssociationService;


    @Override
    public Team findById(Long id) {
        Team team = this.teamRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "Turma não encontrada! Id: " + id + ", Tipo: " + Team.class.getName()));

        SecurityUtil.checkUser();
        return team;
    }

    public List<Team> listAllTeams() {
        SecurityUtil.checkUser();

        List<Team> team = this.teamRepository.findAll();
        if (team.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma turma cadastrada");
        }
        return team;
    }

    public List<Team> findAll() {
        return teamRepository.findAll();
    }

/*    public List<Student> listStudents(Long id) {
        SecurityUtil.checkUser();

        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Turma com o id " + id + " não encontrada."));

        return team.getStudents();
    }*/

/*    public void associateStudents(Team obj) {
        if (obj.getStudents() != null) {
            for (Student student : obj.getStudents()) {
                student.setTeam(obj);
            }
        }
    }*/


    @Transactional
    public Team create(Team obj) {
        obj.setId(null);
        obj.setTeacher(checkTeacher(obj.getTeacher()));

        obj = this.teamRepository.save(obj);

        teamStudents(obj);

        return obj;
    }

    @Override
    public Teacher checkTeacher(Teacher teacher) {
        Teacher existingTeacher = null;

        if(teacher != null)
            existingTeacher = teacherService.findById(teacher.getId());

        return existingTeacher;
    }


    public void teamStudents(Team obj) {

        Set<Long> studentIds = obj.getStudentIds();

        if(studentIds != null && !studentIds.isEmpty()) {

            for(Long studentId : studentIds) {
                Student existingStudent = studentService.findById(studentId);

                studentTeamAssociationService.enrollStudentOnTeam(new StudentTeamAssociation(existingStudent, obj),
                        false);
            }
        }
    }




 /*   public void processStudentInCreation2(Team obj) {
        Set<Long> studentsIds = obj.getStudentIds();

        if (studentsIds != null && !studentsIds.isEmpty()) {

            for (Long studentId : studentsIds) {

                Student existingStudent = studentService.findById(studentId);

                studentTeamAssociationService.create(new StudentTeamAssociation(existingStudent, obj));
            }
            obj.setNumberStudents(studentsIds.size());
        } else {
            //  Como a função é auxiliar do método create, não é necessário desativar relação, visto que ele não tem
            //  nenhuma ainda.
            obj.setNumberStudents(0);
        }
    }

    public void processStudentInCreation(Team obj) {
        List<Student> students = obj.getStudents();

        if (students != null && !students.isEmpty()) {
            List<Student> updatedStudents = new ArrayList<>();
            for (Student student : students) {

                Student existingStudent = studentService.findById(student.getId());

                updateStudent(existingStudent);
                updatedStudents.add(existingStudent);

                obj.setStudents(updatedStudents);
                obj.setNumberStudents(updatedStudents.size());
            }
        } else {
            obj.setNumberStudents(0);
        }
    }*/

    @Transactional
    public Team update(Team obj) {
        Team newObj = findById(obj.getId());
        newObj.setName(obj.getName());
        newObj.setClassroom(obj.getClassroom());
        newObj.setGrade(obj.getGrade());
        newObj.setTeacher(obj.getTeacher());

        //processStudentOnUpdate(obj, newObj);


        newObj.setNumberStudents(obj.getNumberStudents());
        //newObj.setStudents(obj.getStudents());


        newObj = this.teamRepository.save(newObj);

        //associateStudents(newObj);

        return newObj;
    }


    @Override
    public void delete(Long id) {
        Team team = findById(id);
        try {
            this.teamRepository.delete(team);
        } catch (Exception e) {
            throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas");
        }
    }



  /*  public void processStudentOnUpdate(Team obj, Team newObj) {
        processStudentInCreation(obj);
        List<Student> students = newObj.getStudents();

        if(students != null) {
            if(obj.getNumberStudents() != 0) {
                for (Student student : students) {
                    if (!obj.getStudents().contains(student))
                        student.setTeam(null);
                }
            } else {
                Predicate<Student> alwaysTrue = student -> {
                    student.setTeam(null);
                    return true;
                };
                students.removeIf(alwaysTrue);
            }
        }

    }



    public void updateStudent(Student student) {
        Team team = student.getTeam();
        if (team != null) {
            student.getTeam().getStudents().remove(student);
            student.getTeam().setNumberStudents(student.getTeam().getStudents().size());
            teamRepository.save(team);
        }
    }*/


    public void updateTeamStudentCount(Team team, Integer studentCount) {
        team.setNumberStudents(studentCount);
        teamRepository.save(team);
    }


    // O controller acessa esse método
  /*  public Team deleteStudentFromTeam(Long teamId, List<Long> idsStudent) {
        Team team = findById(teamId);

        for (Long idStudent : idsStudent) {

            Student student = studentService.findById(idStudent);
            if (!(team.getStudents().contains(student))) {
                throw new StudenteOnTeamException("Aluno não está vinculado a turma " + team.getName());
            }

            updateStudent(student);
            student.setTeam(null);
            team.getStudents().remove(student);
        }

        //updateTeamStudentCount(team);
        return team;
    }*/




   /* public TeamResponseDTO convertToTeamResponseDTO(Team team) {

        TeamResponseDTO dto = new TeamResponseDTO();
        dto.setId(team.getId());
        dto.setName(team.getName());
        dto.setClassroom(team.getClassroom());
        dto.setGrade(team.getGrade());
        dto.setNumberStudents(team.getNumberStudents());
        dto.setTeacherId(team.getTeacher().getId());

        if (team.getStudents() != null) {
            List<Long> studentIds = team.getStudents().stream()
                    .map(Student::getId)
                    .collect(Collectors.toList());
            dto.setStudentIds(studentIds);
        }

        return dto;
    }*/

}
