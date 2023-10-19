package com.ti.fabricadosaber.components;

import com.ti.fabricadosaber.exceptions.TwoParentsException;
import com.ti.fabricadosaber.models.Parent;
import com.ti.fabricadosaber.models.Student;
import com.ti.fabricadosaber.models.Team;
import com.ti.fabricadosaber.services.ParentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.Set;

@Component
public class StudentTeamOperation {


    @Autowired
    private ParentService parentService;


    public void associateStudents(Team obj) {
        if (obj.getStudents() != null) {
            for (Student student : obj.getStudents()) {
                student.setTeam(obj);
            }
        }
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




}
