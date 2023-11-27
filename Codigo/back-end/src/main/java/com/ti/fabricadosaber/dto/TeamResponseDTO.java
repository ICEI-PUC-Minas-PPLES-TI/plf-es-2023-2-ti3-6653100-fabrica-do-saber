package com.ti.fabricadosaber.dto;

import com.ti.fabricadosaber.enums.Grade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TeamResponseDTO {

    private Long id;
    private String name;
    private String classroom;
    private Grade grade;
    private Integer numberStudents;
    private List<Long> studentIds;
    private Long teacherId;
    private String type;

}
