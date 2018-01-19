package de.msg.gbg.hackathon18.navigalypse.service;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.*;
import de.msg.gbg.hackathon18.navigalypse.service.NavigationServices;
import org.joda.time.ReadableInstant;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class NavigationServicesImp implements NavigationServices {


    @Override
    public ArrayList<LatLng> getWayPoints(String origin, String destination, ReadableInstant departureTime)  {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyCkolP_cWCyGGM1y2RhYvPHX04a3U3EmkM")
                .build();

        DirectionsApiRequest request = DirectionsApi.newRequest(context);
            request.destination(destination);
            request.origin(origin);
            request.departureTime(departureTime);
            request.language("de");
            request.mode(TravelMode.DRIVING);
            request.units(Unit.METRIC);
            request.optimizeWaypoints(true);
            DirectionsResult directionsResults;
            try {
                directionsResults = request.await();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return convertToLatLng(directionsResults);
    }


    private ArrayList<LatLng> convertToLatLng(DirectionsResult directionsResults){
        ArrayList<LatLng> wayPointBuffer = new ArrayList<>();

        for (DirectionsRoute route : directionsResults.routes) {
            for (DirectionsLeg leg : route.legs) {
                for (DirectionsStep step : leg.steps) {
                    wayPointBuffer.add(step.startLocation);
                }
            }
        }
        return wayPointBuffer;
    }

    public String getWayPointsAsString (String origin, String destination, ReadableInstant departureTime){

        ArrayList<LatLng> wayPoints = getWayPoints(origin, destination,departureTime);
        StringBuilder stringWaypoints = new StringBuilder();
        for (LatLng wayPoint : wayPoints) {
            stringWaypoints.append(" ");
            stringWaypoints.append(wayPoint.lat);
            stringWaypoints.append(" ");
            stringWaypoints.append(wayPoint.lng);
            stringWaypoints.append(",");

        }
        return stringWaypoints.toString();
    }

}
