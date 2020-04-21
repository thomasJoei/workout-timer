package com.thojoeis.workout.timer;

import com.thojoeis.workout.timer.ui.TimerUI;

import java.util.*;

public class TimerController implements Observer {

    private Timer timer;
    private TimerUI ui;
    private WorkoutSession workoutSession;
    private AtomicTimerCount centralizedTimer;

    TimerController(TimerUI timerUI) {
        this.ui = timerUI;
        this.centralizedTimer = new AtomicTimerCount();
        centralizedTimer.addObserver(this);
    }

    TimerController(TimerUI timerUI, WorkoutSession workoutSession) {
        this.ui = timerUI;
        this.workoutSession = workoutSession;
        this.centralizedTimer = new AtomicTimerCount();
        centralizedTimer.addObserver(this);
    }

    public void start() {
        System.out.println("Starting timer!");
        timer = new Timer();

        TimerTask decrementTask = new TimerDecrementTask(this.centralizedTimer);
        startInterval();
        timer.scheduleAtFixedRate(decrementTask, 0, 1000);
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

    public TimerController timer(Timer timer) {
        this.timer = timer;
        return this;
    }

    public TimerController ui(TimerUI ui) {
        this.ui = ui;
        return this;
    }

    public TimerController intervals(WorkoutSession intervals) {
        this.workoutSession = intervals;
        return this;
    }

    public TimerController centralizedTimer(AtomicTimerCount centralizedTimer) {
        this.centralizedTimer = centralizedTimer;
        return this;
    }
}
