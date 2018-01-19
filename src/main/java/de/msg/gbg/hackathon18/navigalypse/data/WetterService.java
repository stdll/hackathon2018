package de.msg.gbg.hackathon18.navigalypse.data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import de.msg.gbg.hackathon18.navigalypse.data.jpa.Ort;
import de.msg.gbg.hackathon18.navigalypse.data.jpa.OrtRepository;
import de.msg.gbg.hackathon18.navigalypse.data.jpa.Vorhersage;
import org.springframework.stereotype.Service;

@Service
public class WetterService {

    private final OrtRepository repository;

    public WetterService(OrtRepository repository) {
        this.repository = repository;
    }

    public Vorhersage findeVorhersage(Double latitude, Double longitude, LocalDateTime zeitpunkt) {
        Double minLat = latitude - 0.5;
        Double maxLat = latitude + 0.5;
        Double minLong = longitude - 0.5;
        Double maxLong = longitude + 0.5;

        List<Ort> orte =
            repository.findByLatitudeBetweenAndLongitudeBetween(minLat, maxLat, minLong, maxLong);

        Map<String, Double> distances = orte.stream().collect(Collectors.toMap(Ort::getStationskennung,
            o -> Math
                .sqrt(Math.pow(o.getLatitude() - latitude, 2) + Math.pow(o.getLongitude() - longitude, 2))));

        Map.Entry<String, Double> min = null;
        for (Map.Entry<String, Double> entry : distances.entrySet()) {
            if (min == null || min.getValue() > entry.getValue()) {
                min = entry;
            }
        }

        String minKennung = min.getKey();

        Ort minOrt =
            orte.stream().filter(o -> o.getStationskennung().equals(minKennung)).findFirst().orElse(null);

        Vorhersage vo = minOrt.getVorhersagen().stream().filter(v -> v.getZeitpunkt().equals(zeitpunkt)).findFirst()
            .orElse(null);

        if (vo != null) {
            vo.setStationskennung(minOrt.getName());
        }
        return vo;
    }
}
