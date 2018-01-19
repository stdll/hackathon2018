package de.msg.gbg.hackathon18.navigalypse.service;

import com.google.maps.model.LatLng;
import org.joda.time.ReadableInstant;
import java.util.ArrayList;

public interface NavigationServices {

    ArrayList<LatLng> getWayPoints(String origin, String destination, ReadableInstant departureTime);

}
