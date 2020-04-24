package com.thojoeis.workout.timer.models;

import java.util.List;
import java.util.Optional;

public class WorkoutSessionRounds implements WorkoutSession {

    private int roundIndex = 0;
    private List<Round> rounds;
    private int roundRestTime;

    public WorkoutSessionRounds(List<Round> rounds, int roundRestTime) {
        this.rounds = rounds;
        this.roundRestTime = roundRestTime;
    }

    public Optional<IntervalInfo> getCurrentInterval() {
        Optional<Interval> interval = rounds.get(roundIndex).getCurrentInterval();

        if (!interval.isPresent()) {
            return Optional.empty();
        }

        Optional<Interval> nextInterval = Optional.empty();

        if (rounds.get(roundIndex).hasNext()) {
            nextInterval = rounds.get(roundIndex).getNextInterval();
        }

        return Optional.of(new IntervalInfo()
                .interval(interval.get())
                .nextInterval(nextInterval)
                .totalRounds(rounds.size())
                .currentRound(roundIndex + 1)
                .exercisesPerRound(rounds.get(roundIndex).getTotalExercises())
                .currentExercise(rounds.get(roundIndex).getTotalExercises() - rounds.get(roundIndex).getTotalRemainingExercises())
        );
    }

    public Optional<IntervalInfo> nextInterval() {
        if (isCompleted()) {
            return Optional.empty();
        }

        Optional<Interval> nextInterval = rounds.get(roundIndex).nextInterval();

        if (nextInterval.isPresent()) {
            return getCurrentInterval();
        }

        if (isLastRound()) {
            // end of workout
            return Optional.empty();
        }
        roundIndex++;

        return Optional.of(getRoundRestInterval());
    }

    public boolean isLastRound() {
        return getTotalRemainingRounds() <= 0;
    }

    public int getTotalRemainingRounds() {
        return rounds.size() - (roundIndex + 1);
    }

    private IntervalInfo getRoundRestInterval() {
        return new IntervalInfo()
                .interval(new Interval(IntervalType.REST, "Round Rest", roundRestTime))
                .nextInterval(rounds.get(roundIndex).getNextInterval()) // there is always a next round if there is a round rest
                .totalRounds(rounds.size())
                .currentRound(roundIndex + 1)
                .exercisesPerRound(rounds.get(roundIndex).getTotalExercises())
                .currentExercise(rounds.get(roundIndex).getTotalExercises() - rounds.get(roundIndex).getTotalRemainingExercises())
                ;
    }

    public int getTotalRounds() {
        return rounds.size();
    }

    public int getRoundIndex() {
        return roundIndex;
    }

    public boolean isCompleted() {
        return isLastRound() && rounds.get(roundIndex).isCompleted();
    }

}
