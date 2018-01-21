package de.msg.gbg.hackathon18.navigalypse.service;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;

@Component
public class GoogleMapsNavigationService implements NavigationService {

    private static final Logger LOG = LoggerFactory.getLogger(GoogleMapsNavigationService.class);

    @Override
    public List<Wegpunkt> getWegpunkte(String origin, String destination, Instant departureTime) {
        GeoApiContext context =
            new GeoApiContext.Builder().apiKey("AIzaSyCkolP_cWCyGGM1y2RhYvPHX04a3U3EmkM").build();

        DirectionsApiRequest request = DirectionsApi.newRequest(context);
        request.destination(destination);
        request.origin(origin);
        request.departureTime(toJodaTime(departureTime));
        request.language("de");
        request.mode(TravelMode.DRIVING);
        request.units(Unit.METRIC);
        request.optimizeWaypoints(true);
        DirectionsResult directionsResults;
        try {
            directionsResults = request.await();
        } catch (ApiException | InterruptedException | IOException e) {
            LOG.error("Fehler beim Berechnen der Wegpunkte", e);
            return Collections.emptyList();
        }
        return convertToLatLng(directionsResults);
    }

    private List<Wegpunkt> convertToLatLng(DirectionsResult directionsResults) {
        List<Wegpunkt> wegpunkte = new ArrayList<>();

        for (DirectionsRoute route : directionsResults.routes) {
            for (DirectionsLeg leg : route.legs) {
                for (DirectionsStep step : leg.steps) {
                    wegpunkte.add(new Wegpunkt(step.startLocation, step.duration.inSeconds));
                }
            }
        }
        return wegpunkte;
    }

    private ReadableInstant toJodaTime(Instant instant) {
        return new DateTime(instant.toEpochMilli());
    }

}
