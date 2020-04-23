package com.thojoeis.workout.timer.controllers;

import com.thojoeis.workout.timer.AtomicTimerCount;
import com.thojoeis.workout.timer.models.IntervalInfo;
import com.thojoeis.workout.timer.models.WorkoutSession;
import com.thojoeis.workout.timer.ui.TimerUI;

import java.util.*;

public class TimerControllerImpl implements Observer, TimerController {

    private Timer timer;
    private TimerUI ui;
    private WorkoutSession workoutSession;
    private AtomicTimerCount centralizedTimer;

    public TimerControllerImpl(TimerUI timerUI) {
        this.ui = timerUI;
        this.centralizedTimer = new AtomicTimerCount();
        centralizedTimer.addObserver(this);
    }

    public TimerControllerImpl(TimerUI timerUI, WorkoutSession workoutSession) {
        this.ui = timerUI;
        this.workoutSession = workoutSession;
        this.centralizedTimer = new AtomicTimerCount();
        centralizedTimer.addObserver(this);
    }

    public void start() {
        System.out.println("Starting timer!");
        startInterval();
        startTimer();
    }

    private void startTimer() {
        timer = new Timer();

        TimerTask decrementTask = new TimerDecrementTask(this.centralizedTimer);
        timer.scheduleAtFixedRate(decrementTask, 1000, 1000);
    }

    public void pause() {
        cancelTimer();
    }

    public void resume() {
        cancelTimer(); // to cancel any running timer
        startTimer();
    }

    private void startInterval() {
        Optional<IntervalInfo> newInterval = workoutSession.nextInterval();

        if (newInterval.isPresent()) {
            IntervalInfo intervalInfo = newInterval.get();
            ui.updateIntervalInfo(intervalInfo);
            centralizedTimer.setTimer(intervalInfo.getInterval().getDuration());
        } else {
            end();
        }
    }

    private void end() {
        cancelTimer();
        ui.end();
    }

    private void cancelTimer() {
        timer.cancel();
    }

    @Override
    public void update(Observable observable, Object timerValue) {
        int currentTimerValue = (int) timerValue;
        if (currentTimerValue <= 0) {
            startInterval();
        } else {
            ui.updateTimer(centralizedTimer.getTimer());
        }
    }

    public void setWorkoutSession(WorkoutSession workoutSession) {
        this.workoutSession = workoutSession;
    }

}
