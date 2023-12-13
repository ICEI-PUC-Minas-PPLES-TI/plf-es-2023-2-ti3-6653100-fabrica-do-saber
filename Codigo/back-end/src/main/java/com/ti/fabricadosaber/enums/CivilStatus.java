package com.ti.fabricadosaber.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CivilStatus {
    PAI("pai"), MAE("mãe");

    private String civilStatus;


    CivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
    }


    public String getCivilStatus() {
        return civilStatus;
    }

}
