package com.ti.fabricadosaber.dto;

import java.time.LocalDate;
import java.util.List;

import com.ti.fabricadosaber.enums.Grade;

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
    private LocalDate startDate;
    private LocalDate endDate;
}
