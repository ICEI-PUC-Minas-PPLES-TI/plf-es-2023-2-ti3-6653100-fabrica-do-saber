package com.ti.fabricadosaber.enums;


public enum CivilStatus {
    PAI, MAE;

    public static CivilStatus recoverCivilStatus(String text) {
        for (CivilStatus guard : CivilStatus.values()) {
            if (guard.toString().equalsIgnoreCase(text)) {
                return guard;
            }
        }
        throw new IllegalArgumentException("Relação inválida: " + text);
    }
}
