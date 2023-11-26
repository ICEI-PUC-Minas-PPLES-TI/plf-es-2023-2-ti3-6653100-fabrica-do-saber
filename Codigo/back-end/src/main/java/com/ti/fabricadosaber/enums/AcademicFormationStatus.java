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

    public String getName() {

        return name;
    }


}
