package de.msg.gbg.hackathon18.navigalypse;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;

import com.google.maps.model.LatLng;
import de.msg.gbg.hackathon18.navigalypse.data.WetterService;
import de.msg.gbg.hackathon18.navigalypse.data.jpa.Vorhersage;
import de.msg.gbg.hackathon18.navigalypse.service.NavigationServices;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NavigalypseApplicationTests {

    @Autowired
    private NavigationServices navigation;

    @Autowired
    private WetterService wetterService;

    @Test
    public void contextLoads() {
        ArrayList<LatLng> waypoints = navigation
            .getWayPoints("Max-Planck-Straße 40, 50354 Hürth", "Wittestraße 30, 13509 Berlin",
                DateTime.now().plusHours(1).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0));


        waypoints.forEach(latLng -> {
            Vorhersage v = wetterService
                .findeVorhersage(latLng.lat, latLng.lng, LocalDateTime.now().truncatedTo(ChronoUnit.HOURS));

            LoggerFactory.getLogger(getClass()).info(v.toString());
        });

    }

}
