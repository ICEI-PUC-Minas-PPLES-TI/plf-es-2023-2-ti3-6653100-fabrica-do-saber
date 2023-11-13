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

    @JsonValue
    public String getFlowType() {
        return flow;
    }

    @JsonCreator
    public static FinancialFlowType recoverFlow(String text) {
        for (FinancialFlowType flow : FinancialFlowType.values()) {
            if (flow.getFlowType().equalsIgnoreCase(text)) {
                return flow;
            }
        }
        throw new IllegalArgumentException("Fluxo financeiro inválido: " + text);
    }
}
