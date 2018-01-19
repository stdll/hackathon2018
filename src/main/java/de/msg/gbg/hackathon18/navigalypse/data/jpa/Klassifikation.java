package de.msg.gbg.hackathon18.navigalypse.data.jpa;

import java.util.Arrays;

public enum Klassifikation {
    BERG("1"), SEESTATION("2"), KUESTE("3"), STANDARD("0");

    private String wert;

    Klassifikation(String wert) {
        this.wert = wert;
    }

    public static Klassifikation lese(String wert) {
        return Arrays.stream(Klassifikation.values()).filter(e -> e.wert.equals(wert)).findFirst()
            .orElse(null);
    }
}
