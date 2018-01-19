package de.msg.gbg.hackathon18.navigalypse.data.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrtRepository extends JpaRepository<Ort, Long> {

    Ort findByStationskennung(String stationskennung);

    List<Ort> findByLatitudeBetweenAndLongitudeBetween(Double minLat, Double maxLat, Double minLong,
        Double maxLong);

}
