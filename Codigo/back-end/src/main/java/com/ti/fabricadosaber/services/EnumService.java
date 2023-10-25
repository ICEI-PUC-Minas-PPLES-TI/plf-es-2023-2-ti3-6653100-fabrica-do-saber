package com.ti.fabricadosaber.services;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ti.fabricadosaber.enums.Grade;
import com.ti.fabricadosaber.enums.Race;

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
}
