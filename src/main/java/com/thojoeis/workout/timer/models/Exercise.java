package com.thojoeis.workout.timer.models;

public class Exercise {

    private final String name;
    private final int duration;

    public Exercise(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }
}
