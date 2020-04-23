package com.thojoeis.workout.timer.models;

import java.util.List;
import java.util.Optional;

public class WorkoutSessionLinear implements WorkoutSession {

    private int totalRounds;
    private int currentIntervalIndex;
    private List<Interval> intervals;

    public WorkoutSessionLinear(int totalRounds, int roundRestTime, List<Interval> intervals) {
        this.totalRounds = totalRounds;
        this.currentIntervalIndex = 0;

        this.intervals = intervals;
    }

    public Optional<IntervalInfo> getCurrentInterval() {
        if (isCompleted()) {
            return Optional.empty();
        }
        return Optional.of(new IntervalInfo()
                .interval(intervals.get(currentIntervalIndex))
                .totalRounds(totalRounds)
                .currentRound(getCurrentRound())
                .exercisesPerRound(getTotalExercisesPerRound())
                .currentExercise(getTotalExercises() - getRemainingExercises())
        );
    }

    public Optional<IntervalInfo> nextInterval() {
        if (!hasNext()) {
            return Optional.empty();
        }
        currentIntervalIndex++;

        return getCurrentInterval();
    }

    @Override
    public boolean isCompleted() {
        return currentIntervalIndex >= intervals.size() - 1;
    }

    public boolean hasNext() {
        return currentIntervalIndex < intervals.size() - 1;
    }


    public int getTotalRounds() {
        return totalRounds;
    }

    public int getTotalExercises() {
        return (int) intervals.stream()
                .filter(Interval::isExercise)
                .count();
    }

    public int getTotalExercisesPerRound() {
        return getTotalExercises() / totalRounds;
    }

    public int getCurrentRound() {
        return (int) intervals.stream()
                .limit(currentIntervalIndex)
                .filter(Interval::isExercise)
                .count() / getTotalExercisesPerRound();
    }

    public int getRemainingExercises() {
        return (int) intervals.stream()
                .skip(currentIntervalIndex)
                .filter(Interval::isExercise)
                .count();
    }


}
