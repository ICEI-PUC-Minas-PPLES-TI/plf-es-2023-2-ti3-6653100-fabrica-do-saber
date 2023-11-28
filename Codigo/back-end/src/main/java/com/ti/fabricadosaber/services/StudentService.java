package com.ti.fabricadosaber.services;

import java.time.LocalDate;
import java.util.*;

import com.ti.fabricadosaber.dto.StudentResponseDTO;
import com.ti.fabricadosaber.dto.TeamAndVacationTeamDTO;
import com.ti.fabricadosaber.dto.TeamResponseDTO;
import com.ti.fabricadosaber.dto.VacationTeamResponseDTO;
import com.ti.fabricadosaber.exceptions.EntityNotFoundException;
import com.ti.fabricadosaber.exceptions.StudentTeamAssociationException;
import com.ti.fabricadosaber.exceptions.TwoParentsException;
import com.ti.fabricadosaber.models.*;
import com.ti.fabricadosaber.services.exceptions.DataBindingViolationException;
import com.ti.fabricadosaber.utils.SecurityUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.ti.fabricadosaber.repositories.StudentRepository;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ParentService parentService;

    @Autowired
    @Lazy
    private TeamService teamService;

    @Autowired
    @Lazy
    private VacationTeamService vacationTeamService;

    @Autowired
    @Lazy
    private StudentTeamAssociationService studentTeamAssociationService;




    public Student findById(Long id) {
        SecurityUtil.checkUser();

        Student student =
                this.studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                        "Aluno não encontrado! Id: " + id + ", Tipo: " + Student.class.getName()));

        return student;
    }

    public StudentResponseDTO findByIdDTO(Long id) {
        Student student = findById(id);

        SecurityUtil.checkUser();

        List<Long> teamId = studentTeamAssociationService.findTeamsAndVacationTeams(id);


        StudentResponseDTO studentResponseDTO = convertToStudentResponseDTO(student, teamId);

        return studentResponseDTO;
    }


    public List<StudentResponseDTO> listAllStudents() {
        SecurityUtil.checkUser();

        List<Student> students = this.studentRepository.findAll();
        List<StudentResponseDTO> studentResponseDTOS = new ArrayList<>();

        if(students.isEmpty())
            throw new EntityNotFoundException("Nenhum estudante cadastrado");

        for (Student student : students) {
            List<Long> teamId = studentTeamAssociationService.findTeamsAndVacationTeams(student.getId());
            StudentResponseDTO studentResponseDTO = convertToStudentResponseDTO(student, teamId);
            studentResponseDTOS.add(studentResponseDTO);
        }

        return studentResponseDTOS;
    }


    public TeamResponseDTO findTeamActive(Long id) {
        Student student = findById(id);
        SecurityUtil.checkUser();
        Team team = studentTeamAssociationService.findTeamOfStudent(id);

        if(team == null) {
            throw new EntityNotFoundException("O estudante não está ativo em nenhuma turma");
        }

        List<Long> studentsIds = studentTeamAssociationService.findStudentIdsByTeamId(team.getId());

        return teamService.convertToTeamResponseDTO(team,studentsIds);
    }


    public List<VacationTeamResponseDTO> findVacationTeamActive(Long id) {
        Student student = findById(id);
        List<VacationTeam> vacationTeams = studentTeamAssociationService.findVacationTeamOfStudent(id);

        if(vacationTeams.isEmpty())
            throw new EntityNotFoundException("O estudante não está ativo em nenhuma creche de férias");

        List<VacationTeamResponseDTO> vacationTeamResponseDTOS = new ArrayList<>();

        for (VacationTeam vacationTeam : vacationTeams) {
            List<Long> studentsIds = studentTeamAssociationService.findStudentIdsByVacationTeamId(vacationTeam.getId());
            VacationTeamResponseDTO vacationTeamResponseDTO =
                    vacationTeamService.convertToTeamResponseDTO(vacationTeam, studentsIds);
            vacationTeamResponseDTOS.add(vacationTeamResponseDTO);
        }


        return vacationTeamResponseDTOS;
    }

    public List<TeamAndVacationTeamDTO> findTeamsAndVacationTeams(Long id) {
        Student student = findById(id);

        List<Team> teams = studentTeamAssociationService.findTeamAndVacationTeamOfStudent(id);

        if(teams.isEmpty()) {
            throw new EntityNotFoundException("O Aluno de id: " + id + " não está cadastrado em nenhuma turma ou " +
                    "creche de férias");
        }

        List<TeamAndVacationTeamDTO> teamAndVacationTeamDTOS = new ArrayList<>();

        for (Team team : teams) {

            List<Long> studentsIds = studentTeamAssociationService.findStudentsIdsByTeamAndVacationTeamId(team.getId());

            TeamAndVacationTeamDTO teamAndVacationTeamDTO = convertToTeamsAndVacationTeamsResponseDTO(team,
                    studentsIds);

            teamAndVacationTeamDTOS.add(teamAndVacationTeamDTO);
        }


        return teamAndVacationTeamDTOS;

    }








    public Set<Parent> listParents(Long id) {
        SecurityUtil.checkUser();

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estudante com o ID " + id + " não encontrado"));

        return student.getParents();
    }



    @Transactional
    public Student create(Student obj) {
        SecurityUtil.checkUser();
        exceptionsOfStudentOnTeam(obj);

        twoParents(obj);
        obj.setId(null);

        Set<Parent> parents = saveParents(obj);
        obj.setParents(parents);
        obj.setRegistrationDate(LocalDate.now());

       Student createdStudent = studentRepository.save(obj);

        enrollStudent(createdStudent);

        return createdStudent;
    }


    public void enrollStudent(Student obj) {

        Set<Long> teamIds = obj.getTeamIds();

        for (Long teamId : teamIds) {

            Team existingTeam = teamService.findById(teamId);

            studentTeamAssociationService.enrollStudentOnTeam(new StudentTeamAssociation(obj, existingTeam),
            true);
        }
    }

    public void exceptionsOfStudentOnTeam(Student obj) {
        Set<Long> teamIds = obj.getTeamIds();
        int countTeam = 0;

        for (Long teamId : teamIds) {
            Team existingTeam = teamService.findById(teamId);

            if(!(existingTeam instanceof VacationTeam))
                countTeam++;

        }

        if(countTeam > 1)
            throw new StudentTeamAssociationException("O estudante só pode se matricular em uma turma por vez");
    }


    @Transactional
    public Student update(Student obj) {
        twoParents(obj);

        Student newObj = findById(obj.getId());
        exceptionsOfStudentOnTeam(obj);

        String[] ignoreProperties = { "id", "registrationDate", "parents"};
        BeanUtils.copyProperties(obj, newObj, ignoreProperties);

        Set<Parent> updatedParents = saveParents(obj);
        newObj.setParents(updatedParents);
        newObj.setRegistrationDate(LocalDate.now());


        //persistir o objeto atualizado no BD
        Student saveStudent = studentRepository.save(newObj);

        // Lidar com a relação
        updateStudentOnTeam(saveStudent);

        return saveStudent;
    }

    private void updateStudentOnTeam(Student student) {
        studentTeamAssociationService.updateStudentOnAssociation(student.getTeamIds(), student);
    }



    public Set<Parent> saveParents(Student obj) {
        String[] ignoreProperties = { "id", "registrationDate", "cpf"};
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


    public void delete(Long id) {
        Student student = findById(id);
        try {
            updateNumberStudentsBeforeStudentDeletion(student);
            studentTeamAssociationService.deleteStudent(id);
            this.studentRepository.delete(student);
        } catch (Exception e) {
            throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas");
        }
    }


    public void updateNumberStudentsBeforeStudentDeletion(Student student) {
        List<Team> teamsAssociated = studentTeamAssociationService.teamsAssociatedWithTheStudent(student);

        for (Team team : teamsAssociated) {

            if (team instanceof VacationTeam) {
                vacationTeamService.updateTeamStudentCount((VacationTeam) team,team.getNumberStudents() - 1);
            } else {
                teamService.updateTeamStudentCount(team, team.getNumberStudents() - 1);
            }
        }
    }


    public StudentResponseDTO convertToStudentResponseDTO(Student student, List<Long> teamIds) {

        StudentResponseDTO dto = new StudentResponseDTO();
        dto.setId(student.getId());
        dto.setHometown(student.getHometown());
        dto.setRace(student.getRace());
        dto.setNationality(student.getNationality());
        dto.setReligion(student.getReligion());
        dto.setHomeState(student.getHomeState());
        dto.setParents(student.getParents());
        dto.setTeamIds(teamIds);

        return dto;
    }

    public TeamAndVacationTeamDTO convertToTeamsAndVacationTeamsResponseDTO(Team team, List<Long> studentIds) {

        TeamAndVacationTeamDTO dto = new TeamAndVacationTeamDTO();
        dto.setId(team.getId());
        dto.setName(team.getName());
        dto.setClassroom(team.getClassroom());
        dto.setGrade(team.getGrade());
        dto.setNumberStudents(team.getNumberStudents());
        dto.setTeacher(team.getTeacher());
        dto.setStudentIds(studentIds);

        if(team instanceof VacationTeam) {
            VacationTeam convertTeamForVacationTeam = (VacationTeam) team;
            dto.setStartDate(convertTeamForVacationTeam.getStartDate());
            dto.setEndDate(convertTeamForVacationTeam.getEndDate());
            dto.setType("VacationTeam");
        } else {
            dto.setStartDate(null);
            dto.setEndDate(null);
            dto.setType("Team");
        }


        return dto;
    }


}
