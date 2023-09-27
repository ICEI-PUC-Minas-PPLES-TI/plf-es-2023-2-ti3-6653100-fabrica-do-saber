package com.ti.fabricadosaber.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

public enum Race {
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        AMARELO("Amarelo"),
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        BRANCO("Branco"),
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        INDIGENA("Indígena"),
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        PARDO("Pardo"),
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        PRETO("Preto"),
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        OUTRA("Outra"),
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        PREFIRO_NAO_DECLARAR("Prefiro não declarar");

        private final String name;

        Race(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }


}
