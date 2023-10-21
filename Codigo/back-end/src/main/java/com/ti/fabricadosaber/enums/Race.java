package com.ti.fabricadosaber.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum Race {
    AMARELO("Amarelo"),
    BRANCO("Branco"),
    INDIGENA("Indígena"),
    PARDO("Pardo"),
    PRETO("Preto"),
    OUTRA("Outra"),
    PREFIRO_NAO_DECLARAR("Prefiro não declarar");

    private final String name;

    Race(String name) {

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Race recoverRace(String name){

            for(Race race : values()){
                if (race.getName().equalsIgnoreCase(name)) {
                    return race;
                }
            }
            throw new IllegalArgumentException("Raça não encontrada: "+name);
        }

}
