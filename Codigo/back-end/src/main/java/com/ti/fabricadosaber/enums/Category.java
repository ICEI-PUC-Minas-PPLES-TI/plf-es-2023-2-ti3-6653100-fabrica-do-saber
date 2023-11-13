package com.ti.fabricadosaber.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Category {
    PAYROLL("Pagamento aos funcionários"),
    INFRASTRUCTURE_EXPENSE("Despesas em infraestrutura"),
    INSTITUTIONAL_MARKETING("Marketing institucional"),
    EDUCATIONAL_PROJECTS("Projetos educacionais"),
    ADMINISTRATIVE_COSTS("Custos administrativos"),
    SCHOOL_EVENTS("Eventos escolares"),
    MAINTENANCE_SERVICES("Serviços de manutenção"),
    EDUCATIONAL_MATERIAL("Material escolar");

    private final String description;

    Category(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }

    @JsonCreator
    public static Category recoverCategory(String description) {
        for (Category category : Category.values()) {
            if (category.getDescription().equals(description)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Categoria desconhecida " + description);
    }
}

