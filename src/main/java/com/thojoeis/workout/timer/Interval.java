package com.thojoeis.workout.timer;

public class Interval {
    private final IntervalType type;
    private final String label;
    private final int duration;

    public Interval(IntervalType type, String label, int duration) {
        this.type = type;
        this.label = label;
        this.duration = duration;
    }

    public IntervalType getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }

    public int getDuration() {
        return duration;
    }

    public boolean isExercise() {
        return IntervalType.EXERCISE.equals(this.type);
    }
}
