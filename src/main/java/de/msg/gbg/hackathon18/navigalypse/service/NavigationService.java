package de.msg.gbg.hackathon18.navigalypse.service;

import java.time.Instant;
import java.util.List;

public interface NavigationService {

    List<Wegpunkt> getWegpunkte(String origin, String destination, Instant departureTime);

}
