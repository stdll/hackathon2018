package de.msg.gbg.hackathon18.navigalypse;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

import de.msg.gbg.hackathon18.navigalypse.data.WetterService;
import de.msg.gbg.hackathon18.navigalypse.data.jpa.Vorhersage;
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
    private WetterService wetterService;

    @Test
    public void contextLoads() {

        Vorhersage v =
            wetterService.findeVorhersage(50.88, 6.91, LocalDateTime.now().truncatedTo(ChronoUnit.HOURS));

        LoggerFactory.getLogger(getClass()).info(v.toString());

    }

}
