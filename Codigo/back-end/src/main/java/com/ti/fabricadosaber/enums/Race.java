package com.ti.fabricadosaber.enums;

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


}
