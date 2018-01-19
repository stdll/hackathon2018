package de.msg.gbg.hackathon18.navigalypse.data.jpa;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.springframework.data.annotation.PersistenceConstructor;

@Entity
public class Ort {

    @Id
    @GeneratedValue
    private long id;

    private String stationskennung;

    private String name;

    private Double longitude;

    private Double latitude;

    private Integer hoehe;

    @Enumerated(EnumType.STRING)
    private Klassifikation klassifikation;

    private String gebietskennung;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "vorhersage_id")
    private List<Vorhersage> vorhersagen = new ArrayList<>();

    public Ort(String stationskennung, String name, Double longitude, Double latitude, Integer hoehe,
        Klassifikation klassifikation, String gebietskennung) {
        this.stationskennung = stationskennung;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.hoehe = hoehe;
        this.klassifikation = klassifikation;
        this.gebietskennung = gebietskennung;
    }

    @PersistenceConstructor
    Ort() {
    }

    public long getId() {
        return id;
    }

    public String getStationskennung() {
        return stationskennung;
    }

    public String getName() {
        return name;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Integer getHoehe() {
        return hoehe;
    }

    public Klassifikation getKlassifikation() {
        return klassifikation;
    }

    public String getGebietskennung() {
        return gebietskennung;
    }

    public List<Vorhersage> getVorhersagen() {
        return vorhersagen;
    }
}
