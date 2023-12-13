package com.ti.fabricadosaber.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ti.fabricadosaber.enums.Grade;
import com.ti.fabricadosaber.models.Teacher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TeamAndVacationTeamDTO {
    private Long id;
    private String name;
    private String classroom;
    private Grade grade;
    private Integer numberStudents;
    private List<Long> studentIds;
    private Teacher teacher;
    private String type;



    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate startDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate endDate;
}
