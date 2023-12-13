package com.ti.fabricadosaber.services.interfaces;

import com.ti.fabricadosaber.models.Teacher;
import com.ti.fabricadosaber.models.Team;


public interface TeamOperations {


    Teacher checkTeacher(Teacher teacher);

    Team findById(Long id);


    void delete(Long id);
}
