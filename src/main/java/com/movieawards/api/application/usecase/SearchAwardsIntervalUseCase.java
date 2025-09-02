package com.movieawards.api.application.usecase;

import com.movieawards.api.application.repository.MovieAwardRepository;
import com.movieawards.api.application.usecase.base.UseCase;
import com.movieawards.api.domain.entity.AwardsInterval;
import com.movieawards.api.domain.entity.AwardsIntervalDetail;
import com.movieawards.api.domain.entity.ProducerAwarded;
import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class SearchAwardsIntervalUseCase extends UseCase<Void, AwardsInterval> {

    private final MovieAwardRepository repository;

    public SearchAwardsIntervalUseCase(MovieAwardRepository repository) {
        this.repository = repository;
    }

    @Override
    public AwardsInterval execute(Void value) {
        getLogger().info("m=execute, action=getting awards interval");
        var winners = getWinners();

        if (winners.isEmpty()) {
            return null;
        }

        var groupedWinners = winners
                .stream()
                .collect(Collectors.groupingBy(
                        ProducerAwarded::name,
                        Collectors.collectingAndThen(
                                Collectors.mapping(ProducerAwarded::year, Collectors.toList()),
                                years -> years
                        ))).entrySet()
                .stream()
                .filter(f -> f.getValue().size() > 1)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> {
                            int min = Collections.min(e.getValue());
                            int max = Collections.max(e.getValue());
                            int diff = max - min;
                            return new AwardsIntervalDetail(e.getKey(), diff, min, max);
                        }
                ))
                .values()
                .stream()
                .toList();

        var max = groupedWinners.stream().mapToInt(AwardsIntervalDetail::interval).max().orElse(0);
        var min = groupedWinners.stream().mapToInt(AwardsIntervalDetail::interval).min().orElse(0);

        getLogger().info("m=execute, action=awards interval processed successfully, min={}, max={}", min, max);

        return new AwardsInterval(
                groupedWinners.stream().filter(p -> p.interval().equals(min)).toList(),
                groupedWinners.stream().filter(p -> p.interval().equals(max)).toList()
        );
    }

    private ArrayList<ProducerAwarded> getWinners() {
        var winners = new ArrayList<ProducerAwarded>();
        repository.listWinners()
                .forEach(f -> {
                    Arrays.stream(f.producers().split(",| and "))
                            .forEach(name -> {
                                if (Strings.isNotBlank(name) && !name.trim().isBlank()) {
                                    winners.add(new ProducerAwarded(f.year(), name.trim()));
                                }
                            });
                });
        return winners;
    }

    @Override
    public Class<?> getClazz() {
        return this.getClass();
    }
}
