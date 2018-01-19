package de.msg.gbg.hackathon18.navigalypse.data;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;

import de.msg.gbg.hackathon18.navigalypse.data.jpa.Klassifikation;
import de.msg.gbg.hackathon18.navigalypse.data.jpa.Ort;
import de.msg.gbg.hackathon18.navigalypse.data.jpa.OrtRepository;
import de.msg.gbg.hackathon18.navigalypse.data.jpa.Vorhersage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ForecastImporter {

    private static final Logger LOG = LoggerFactory.getLogger(ForecastImporter.class);

    private final OrtRepository repository;

    public ForecastImporter(OrtRepository repository) {
        this.repository = repository;
    }

    //@PostConstruct
    public void importForecasts() throws Exception {
        Path path = Paths.get(getClass().getClassLoader().getResource("dwd/o_gmosw_078_1_latest").toURI());
        LOG.info(path.toString());

        // Skip header
        Files.lines(path, StandardCharsets.ISO_8859_1).skip(2).forEach(this::readLine);
    }

    public void readLine(String line) {
        if (line.length() < 23) {
            LOG.error("Fehler beim Lesen der Zeile {}. Zu kurz: {}", line, line.length());
            return;
        }
        String[] identifikation = line.substring(0, 23).trim().split("\\s+", 3);
        String[] fields = line.substring(23).trim().split("\\s+");

        if (identifikation[1].equals("*")) {
            // Zeile ist Ort
            try {
                Double longitude = Double.valueOf(fields[0]) / 100.0d;
                Double latitude = Double.valueOf(fields[1]) / 100.0d;
                Integer hoehe = Integer.valueOf(fields[2]);
                Klassifikation klassifikation = Klassifikation.lese(fields[4]);

                Ort aktuellerOrt =
                    new Ort(identifikation[0], identifikation[2], longitude, latitude, hoehe, klassifikation,
                        fields[5]);
                repository.save(aktuellerOrt);
            } catch (Exception e) {
                LOG.error("Fehler beim Lesen der Zeile {}. {}: {}", line, e.getClass().getSimpleName(),
                    e.getMessage());
            }
        } else {
            // Zeile ist Vorhersage
            try {
                fields = line.trim().split("\\s+");

                Ort ort = repository.findByStationskennung(fields[0]);
                if (ort != null) {
                    LocalDateTime zeitpunkt = LocalDate.now().atStartOfDay();
                    zeitpunkt = zeitpunkt.plusHours(Long.valueOf(fields[1]));
                    Integer temperatur = Integer.valueOf(fields[3]);
                    Integer windrichtung = Integer.valueOf(fields[7]) * 10;
                    Integer windgeschwindigkeit = (int) (Integer.valueOf(fields[8]) * 1.852);
                    Double regen = fields[10].equals("---") ? 0 : Double.valueOf(fields[10]) / 10;
                    Double schnee = fields[11].equals("---") ? 0 : regen - Double.valueOf(fields[11]) / 10;

                    Vorhersage vorhersage =
                        new Vorhersage(fields[0], zeitpunkt, temperatur, windrichtung, windgeschwindigkeit,
                            regen, schnee);

                    ort.getVorhersagen().add(vorhersage);
                    repository.save(ort);
                }
            } catch (Exception e) {
                LOG.error("Fehler beim Lesen der Zeile {}. {}: {}", line, e.getClass().getSimpleName(),
                    e.getMessage());
            }
        }
    }
}
