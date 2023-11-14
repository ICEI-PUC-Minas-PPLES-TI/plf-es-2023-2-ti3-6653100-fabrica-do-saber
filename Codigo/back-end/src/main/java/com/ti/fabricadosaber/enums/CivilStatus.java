package com.ti.fabricadosaber.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CivilStatus {
    PAI("pai"), MAE("mãe");

    private String civilStatus;


    CivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
    }

    @JsonValue
    public String getCivilStatus() {
        return civilStatus;
    }

    @JsonCreator
    public static CivilStatus recoverCivilStatus(String text) {
        for (CivilStatus guard : CivilStatus.values()) {
            if (guard.getCivilStatus().equalsIgnoreCase(text)) {
                return guard;
            }
        }
        throw new IllegalArgumentException("Relação inválida: " + text);
    }
}
