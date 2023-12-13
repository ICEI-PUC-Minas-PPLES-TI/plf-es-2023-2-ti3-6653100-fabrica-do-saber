package com.ti.fabricadosaber.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Religion {
        CANDOMBLE("Candomblé"),
        CATOLICISMO("Catolicismo"),
        ESPIRITISMO("Espiritismo"),
        PROTESTANTISMO("Protestantismo"),
        UMBANDA("Umbanda"),
        OUTRA("Outra"),
        NAO_POSSUI("Não possui"),
        PREFIRO_NAO_DECLARAR("Prefiro não declarar");

        private final String name;

        Religion(String name) {
            this.name = name;
        }


        public String getName() {
            return name;
        }

}
