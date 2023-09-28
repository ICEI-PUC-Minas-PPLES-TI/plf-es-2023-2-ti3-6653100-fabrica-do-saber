package com.ti.fabricadosaber.enums;

public enum Grade {

    PRIMEIRA_SERIE("1º Série"),
    SEGUNDA_SERIE("2º Série"),
    TERCEIRA_SERIE("3º Série"),
    QUARTA_SERIE("4º Série"),
    QUINTA_SERIE("5º Série");

    private final String name;

    Grade(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
