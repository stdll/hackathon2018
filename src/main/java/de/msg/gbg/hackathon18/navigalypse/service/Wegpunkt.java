package de.msg.gbg.hackathon18.navigalypse.service;

import com.google.maps.model.LatLng;

public class Wegpunkt {

    private LatLng koordinaten;

    private long sekunden;

    Wegpunkt(LatLng koordinaten, long sekunden) {
        this.koordinaten = koordinaten;
        this.sekunden = sekunden;
    }

    public LatLng getKoordinaten() {
        return koordinaten;
    }

    public long getSekunden() {
        return sekunden;
    }
}
