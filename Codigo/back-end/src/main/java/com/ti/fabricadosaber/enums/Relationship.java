package com.ti.fabricadosaber.enums;


public enum Relationship {
    PAI, MAE;

    public static Relationship recoverRelationship(String text) {
        for (Relationship guard : Relationship.values()) {
            if (guard.toString().equalsIgnoreCase(text)) {
                return guard;
            }
        }
        throw new IllegalArgumentException("Relação inválida: " + text);
    }
}
