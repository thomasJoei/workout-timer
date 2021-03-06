package com.thojoeis.workout.timer.controllers;

import com.thojoeis.workout.timer.AtomicTimerCount;

import java.util.TimerTask;

public class TimerDecrementTask extends TimerTask {

    private AtomicTimerCount centralizedTimer;

    public TimerDecrementTask(AtomicTimerCount centralizedTimer) {
        this.centralizedTimer = centralizedTimer;
    }

    @Override
    public void run() {
        centralizedTimer.decrementTimer();
    }
}
