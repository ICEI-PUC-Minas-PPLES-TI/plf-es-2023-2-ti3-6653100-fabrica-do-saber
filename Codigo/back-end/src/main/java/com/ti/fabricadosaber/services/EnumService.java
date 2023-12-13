package com.ti.fabricadosaber.services;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.aspectj.asm.internal.Relationship;
import org.springframework.stereotype.Service;

import com.ti.fabricadosaber.enums.CivilStatus;
import com.ti.fabricadosaber.enums.Grade;
import com.ti.fabricadosaber.enums.Race;
import com.ti.fabricadosaber.enums.CivilStatus;
import com.ti.fabricadosaber.enums.Religion;
import com.ti.fabricadosaber.enums.State;

@Service
public class EnumService {

    public List<String> recoverGrade() {
        return Arrays.stream(Grade.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    public List<String> recoverRace() {
        return Arrays.stream(Race.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

      public List<String> recoverCivilStatus() {
        return Arrays.stream(CivilStatus.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    } 
    
    public List<String> recoverReligion() {
        return Arrays.stream(Religion.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

        public List<String> recoverState() {
        return Arrays.stream(State.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

}
