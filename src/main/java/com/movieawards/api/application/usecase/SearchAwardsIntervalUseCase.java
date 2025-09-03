package com.movieawards.api.application.usecase;

import com.movieawards.api.application.repository.MovieAwardRepository;
import com.movieawards.api.application.usecase.base.UseCase;
import com.movieawards.api.domain.entity.AwardsInterval;
import com.movieawards.api.domain.entity.AwardsIntervalDetail;
import com.movieawards.api.domain.entity.ProducerAwarded;
import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

        var groupedWinners = getGroupedWinnersByName(winners);
        var minIntervals = getMinIntervalsFromWinners(groupedWinners);
        var maxIntervals = getMaxIntervalsFromWinners(groupedWinners);

        var max = maxIntervals.stream().mapToInt(AwardsIntervalDetail::interval).max().orElse(0);
        var min = minIntervals.stream().mapToInt(AwardsIntervalDetail::interval).min().orElse(0);

        getLogger().info("m=execute, action=awards interval processed successfully, min={}, max={}", min, max);

        return new AwardsInterval(
                minIntervals.stream().filter(p -> p.interval().equals(min)).toList(),
                maxIntervals.stream().filter(p -> p.interval().equals(max)).toList()
        );
    }

    @Override
    public Class<?> getClazz() {
        return this.getClass();
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

    private static List<Map.Entry<String, List<Integer>>> getGroupedWinnersByName(ArrayList<ProducerAwarded> winners) {
        return winners
                .stream()
                .collect(Collectors.groupingBy(
                        ProducerAwarded::name,
                        Collectors.collectingAndThen(
                                Collectors.mapping(ProducerAwarded::year, Collectors.toList()),
                                years -> years
                        ))).entrySet()
                .stream()
                .filter(f -> f.getValue().size() > 1)
                .toList();
    }

    private static List<AwardsIntervalDetail> getMinIntervalsFromWinners(List<Map.Entry<String, List<Integer>>> groupedWinners) {
        return groupedWinners.stream().collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> {
                            var years = e.getValue().stream().sorted().toList();
                            var minDiffPair = IntStream.range(0, years.size() - 1)
                                    .mapToObj(i -> new int[]{years.get(i), years.get(i + 1)})
                                    .min(Comparator.comparingInt(pair -> pair[1] - pair[0])).orElse(new int[0]);

                            var previousWin = minDiffPair[0];
                            int followingWin = minDiffPair[1];
                            int diff = followingWin - previousWin;
                            return new AwardsIntervalDetail(e.getKey(), diff, previousWin, followingWin);
                        }
                ))
                .values()
                .stream()
                .toList();
    }

    private static List<AwardsIntervalDetail> getMaxIntervalsFromWinners(List<Map.Entry<String, List<Integer>>> groupedWinners) {
        return groupedWinners.stream().collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> {
                            var years = e.getValue().stream().sorted().toList();
                            var maxDiffPair = IntStream.range(0, years.size() - 1)
                                    .mapToObj(i -> new int[]{years.get(i), years.get(i + 1)})
                                    .max(Comparator.comparingInt(pair -> pair[1] - pair[0])).orElse(new int[0]);

                            int previousWin = maxDiffPair[0];
                            int followingWin = maxDiffPair[1];
                            int diff = followingWin - previousWin;
                            return new AwardsIntervalDetail(e.getKey(), diff, previousWin, followingWin);
                        }
                ))
                .values()
                .stream()
                .toList();
    }
}
