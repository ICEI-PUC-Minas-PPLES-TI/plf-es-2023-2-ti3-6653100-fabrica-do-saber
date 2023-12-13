package com.ti.fabricadosaber.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Grade {

    //todo: O name é só para exibir em interface e não corresponde o valor do enumeration
    PRIMEIRA_SERIE("1 Série"),
    SEGUNDA_SERIE("2 Série"),
    TERCEIRA_SERIE("3 Série"),
    QUARTA_SERIE("4 Série"),
    QUINTA_SERIE("5 Série");

    private final String name;

    Grade(String name) {

        this.name = name;
    }

    public String getName() {

        return name;
    }


}
