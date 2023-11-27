package com.ti.fabricadosaber.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ti.fabricadosaber.enums.Grade;

import com.ti.fabricadosaber.models.Teacher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VacationTeamResponseDTO {
    
    private Long id;
    private String name;
    private String classroom;
    private Grade grade;
    private Integer numberStudents;
    private List<Long> studentIds;
    private Long teacherId;



    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate startDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate endDate;
}
