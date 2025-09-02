package com.movieawards.api.infra.batch;

import com.movieawards.api.domain.entity.MovieAward;
import com.movieawards.api.infra.repository.MovieAwardRepositoryImpl;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Component
public class MovieAwardFileService {

    private final Logger logger = LoggerFactory.getLogger(MovieAwardFileService.class);

    private final MovieAwardRepositoryImpl repository;

    public MovieAwardFileService(MovieAwardRepositoryImpl repository) {
        this.repository = repository;
    }

    public void createMovieAwards(String file) {
        logger.info("m=createMovieAwards, action=processing csv file");

        try (InputStream is = getClass().getClassLoader().getResourceAsStream(file)) {
            if (is == null) {
                throw new FileNotFoundException();
            }

            try (Reader reader = new InputStreamReader(is)) {
                var settings = new CsvParserSettings();
                settings.setHeaderExtractionEnabled(true);
                settings.setDelimiterDetectionEnabled(true, ';');
                var parser = new CsvParser(settings);

                var rows = parser.iterate(reader).iterator();

                List<MovieAward> batch = new ArrayList<>();

                String[] row;
                while (rows.hasNext()) {
                    row = rows.next();
                    var movieAward = MovieAward.builder()
                            .year(Integer.parseInt(row[0]))
                            .title(row[1])
                            .studios(row[2])
                            .producers(row[3])
                            .winner(getWinner(row[4]))
                            .build();
                    batch.add(movieAward);
                }
                repository.create(batch);
            }
        } catch (IOException e) {
            logger.error("m=createMovieAwards, message=error reading csv file, exception={}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
        logger.info("m=createMovieAwards, message=data created successfully");
    }

    private static boolean getWinner(String value) {
        if (Strings.isBlank(value))
            return false;
        return value.equalsIgnoreCase("yes");
    }
}
