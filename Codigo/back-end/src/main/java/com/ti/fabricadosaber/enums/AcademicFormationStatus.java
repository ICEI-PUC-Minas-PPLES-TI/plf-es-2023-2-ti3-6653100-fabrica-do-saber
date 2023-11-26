package com.ti.fabricadosaber.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AcademicFormationStatus {
    
    CURSANDO("Cursando"),
    CONCLUIDO("Concluído");

    private final String name;

    AcademicFormationStatus(String name) {

        this.name = name;
    }

    @JsonValue
    public String getName() {

        return name;
    }

    @JsonCreator
    public static AcademicFormationStatus recoveAcademicFormationStatus(String text) {
        for (AcademicFormationStatus formation : AcademicFormationStatus.values()) {
            if (formation.toString().equalsIgnoreCase(text)) {
                return formation;
            }
        }
        throw new IllegalArgumentException("Situação da formação inválida: " + text);
    }

}
