package com.thojoeis.workout.timer;

import com.thojoeis.workout.timer.models.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class WorkoutSessionFactory {

    private static final String REST_LABEL = "Rest";

    public static WorkoutSession getWorkoutSession(int totalRounds, List<Interval> exercises, int restTime, int roundRestTime) {

        List<Round> rounds = IntStream
                .range(0, totalRounds)
                .mapToObj(roundIndex -> {
                    List<Interval> roundIntervals = exercises.stream()
                            .flatMap(exercise -> Stream.of(exercise, createRestInterval(restTime, REST_LABEL)))
                            .limit((exercises.size() * 2) - 1) // skip last rest
                            .collect(Collectors.toList());
                    return new Round(roundIntervals);
                })
                .collect(Collectors.toList());

        return new WorkoutSessionRounds(rounds, roundRestTime);
    }

    private static Interval createRestInterval(int restTime, String label) {
        return new Interval(IntervalType.REST, label, restTime);
    }

}
