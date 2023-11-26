package com.ti.fabricadosaber.dto;

import com.ti.fabricadosaber.enums.Race;
import com.ti.fabricadosaber.enums.Religion;
import com.ti.fabricadosaber.enums.State;
import com.ti.fabricadosaber.models.Parent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentResponseDTO {
    private Long id;
    private List<Long> teamIds;
    private String hometown;
    private String nationality;
    private Race race;
    private State homeState;
    private Religion religion;
    private Set<Parent> parents;
}
