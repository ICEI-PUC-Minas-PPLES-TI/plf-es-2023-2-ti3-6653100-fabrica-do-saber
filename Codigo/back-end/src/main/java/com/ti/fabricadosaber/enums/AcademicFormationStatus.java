package com.ti.fabricadosaber.enums;

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
