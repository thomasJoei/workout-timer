package com.thojoeis.workout.timer;

import java.util.List;
import java.util.Optional;

public class Round {

    private List<Interval> intervals;
    private int intervalIndex = -1;
    private int totalExercises;
    private int totalRemainingExercises;

    public Round(List<Interval> intervals) {
        this.intervals = intervals;
        initCounters();
    }

    public  Optional<Interval> getCurrentInterval() {
        if (!hasStarted() || isCompleted()) {
            return Optional.empty();
        }
        return Optional.of(intervals.get(intervalIndex));
    }

    public Optional<Interval> nextInterval() {
        if (!hasNext()) {
            intervalIndex++;
            return Optional.empty();
        }

        intervalIndex++;

        Interval newInterval = intervals.get(intervalIndex);

        if (newInterval.isExercise()) {
            totalRemainingExercises--;
        }

        return Optional.of(newInterval);
    }

    public int getTotalExercises() {
        return totalExercises;
    }

    public int getTotalRemainingExercises() {
        return totalRemainingExercises;
    }

    private void initCounters() {
        this.totalExercises = (int) intervals.stream()
                .filter(interval -> IntervalType.EXERCISE.equals(interval.getType()))
                .count();
        this.totalRemainingExercises = this.totalExercises;
    }

    public boolean isCompleted() {
        return intervalIndex > intervals.size() - 1;
    }

    public boolean hasNext() {
        return intervalIndex < intervals.size() - 1 ;
    }

    public boolean hasStarted() {
        return intervalIndex >= 0;
    }

}