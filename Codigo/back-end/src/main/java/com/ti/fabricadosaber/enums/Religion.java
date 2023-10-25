package com.ti.fabricadosaber.enums;

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

        public static Religion recoverReligion(String name){

            for(Religion religion : values()){
                if (religion.getName().equalsIgnoreCase(name)) {
                    return religion;
                }
            }
            throw new IllegalArgumentException("Religião não encontrada: "+name);
        }
}
