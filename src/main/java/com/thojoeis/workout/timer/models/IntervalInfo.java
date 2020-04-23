package com.thojoeis.workout.timer.models;

public class IntervalInfo {
    private Interval interval;
    private int currentRound;
    private int totalRounds;
    private int exercisesPerRound;
    private int currentExercise;


    public Interval getInterval() {
        return interval;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public int getTotalRounds() {
        return totalRounds;
    }

    public int getExercisesPerRound() {
        return exercisesPerRound;
    }

    public int getCurrentExercise() {
        return currentExercise;
    }

    public IntervalInfo interval(Interval interval) {
        this.interval = interval;
        return this;
    }

    public IntervalInfo currentRound(int currentRound) {
        this.currentRound = currentRound;
        return this;
    }

    public IntervalInfo totalRounds(int totalRounds) {
        this.totalRounds = totalRounds;
        return this;
    }

    public IntervalInfo exercisesPerRound(int exercisesPerRound) {
        this.exercisesPerRound = exercisesPerRound;
        return this;
    }

    public IntervalInfo currentExercise(int currentExercise) {
        this.currentExercise = currentExercise;
        return this;
    }
}
