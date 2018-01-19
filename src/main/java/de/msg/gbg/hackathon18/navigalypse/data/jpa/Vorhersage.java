package de.msg.gbg.hackathon18.navigalypse.data.jpa;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.annotation.PersistenceConstructor;

@Entity
public class Vorhersage {

    @Id
    @GeneratedValue
    private long id;

    private String stationskennung;

    private LocalDateTime zeitpunkt;

    private Integer temperatur;

    private Integer windrichtung;

    private Integer windgeschwindigkeit;

    private Double regen;

    private Double schnee;

    public Vorhersage(String stationskennung, LocalDateTime zeitpunkt, Integer temperatur,
        Integer windrichtung, Integer windgeschwindigkeit, Double regen, Double schnee) {
        this.stationskennung = stationskennung;
        this.zeitpunkt = zeitpunkt;
        this.temperatur = temperatur;
        this.windrichtung = windrichtung;
        this.windgeschwindigkeit = windgeschwindigkeit;
        this.regen = regen;
        this.schnee = schnee;
    }

    @PersistenceConstructor
    Vorhersage() {
    }

    public long getId() {
        return id;
    }

    public String getStationskennung() {
        return stationskennung;
    }

    public void setStationskennung(String stationskennung) {
        this.stationskennung = stationskennung;
    }

    public LocalDateTime getZeitpunkt() {
        return zeitpunkt;
    }

    public Integer getTemperatur() {
        return temperatur;
    }

    public Integer getWindrichtung() {
        return windrichtung;
    }

    public Integer getWindgeschwindigkeit() {
        return windgeschwindigkeit;
    }

    public Double getRegen() {
        return regen;
    }

    public Double getSchnee() {
        return schnee;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Vorhersage{");
        sb.append("stationskennung='").append(stationskennung).append('\'');
        sb.append(", zeitpunkt=").append(zeitpunkt);
        sb.append(", temperatur=").append(temperatur);
        sb.append(", windrichtung=").append(windrichtung);
        sb.append(", windgeschwindigkeit=").append(windgeschwindigkeit);
        sb.append(", regen=").append(regen);
        sb.append(", schnee=").append(schnee);
        sb.append('}');
        return sb.toString();
    }
}
