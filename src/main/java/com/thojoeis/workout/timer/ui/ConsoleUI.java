package com.thojoeis.workout.timer.ui;

import com.thojoeis.workout.timer.models.IntervalInfo;

public class ConsoleUI implements TimerUI {

    public ConsoleUI() {
        System.out.println("UI ready");
    }

    @Override
    public void end() {
        System.out.println("Workout completed !");
    }

    public void updateTimer(int value) {
        System.out.println("Timer : " + value);
    }

    @Override
    public void updateIntervalInfo(IntervalInfo intervalInfo) {
        System.out.println("Current exercise : " + intervalInfo.getInterval().getLabel());
        System.out.println(String.format("Round : %s / %s", intervalInfo.getCurrentRound(), intervalInfo.getTotalRounds()));
        System.out.println(String.format("Exercises counter :%s / %s", intervalInfo.getCurrentExercise(), intervalInfo.getExercisesPerRound()));

    }

    public void updateExercise(String exerciseName) {
        System.out.println("Exercise : " + exerciseName);

    }
}
