package de.msg.gbg.hackathon18.navigalypse;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.msg.gbg.hackathon18.navigalypse.data.WetterService;
import de.msg.gbg.hackathon18.navigalypse.data.jpa.Vorhersage;
import de.msg.gbg.hackathon18.navigalypse.service.NavigationService;
import de.msg.gbg.hackathon18.navigalypse.service.Wegpunkt;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NavigalypseApplicationTests {

    private static final Logger LOG = LoggerFactory.getLogger(NavigalypseApplicationTests.class);

    @Autowired
    private NavigationService navigation;

    @Autowired
    private WetterService wetterService;

    @Test
    public void contextLoads() {
        OffsetDateTime zeitpunkt =
            OffsetDateTime.now().plus(1, ChronoUnit.HOURS).truncatedTo(ChronoUnit.HOURS);

        LOG.info(zeitpunkt.format(DateTimeFormatter.ISO_DATE_TIME));
        LOG.info(zeitpunkt.toInstant().toString());

        List<Wegpunkt> wegpunkte = navigation.getWegpunkte("Max-Planck-Straße 40, 50354 Hürth",
            "Wittestraße 30, 13509 Berlin", zeitpunkt.toInstant());

        for (Wegpunkt wegpunkt : wegpunkte) {
            zeitpunkt = zeitpunkt.plus(wegpunkt.getSekunden(), ChronoUnit.SECONDS);

            Vorhersage v = wetterService.findeVorhersage(wegpunkt.getKoordinaten().lat,
                wegpunkt.getKoordinaten().lng, zeitpunkt.truncatedTo(ChronoUnit.HOURS).toLocalDateTime());

            LOG.info(v.toString());
        }
    }

}
