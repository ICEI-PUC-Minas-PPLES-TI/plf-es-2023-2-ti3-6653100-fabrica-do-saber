package com.ti.fabricadosaber.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum FinancialFlowType {
    INPUT("Entrada"),
    OUTPUT("Saída");

    private String flow;

    FinancialFlowType(String flow) {
        this.flow = flow;
    }


    public String getFlowType() {
        return flow;
    }


}
