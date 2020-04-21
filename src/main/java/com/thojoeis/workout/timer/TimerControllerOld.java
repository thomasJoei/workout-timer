//package com.thojoeis.workout.timer;
//
//import com.thojoeis.workout.timer.ui.TimerUI;
//
//import java.util.*;
//
//public class TimerControllerOld implements Observer {
//
//    private Timer timer;
//    private TimerUI ui;
//    private List<Interval> intervals;
//    private AtomicTimerCount centralizedTimer;
//
//    TimerControllerOld(TimerUI timerUI) {
//        this.ui = timerUI;
//        this.intervals = Collections.emptyList();
//        this.centralizedTimer = new AtomicTimerCount();
//        centralizedTimer.addObserver(this);
//    }
//
//    TimerControllerOld(TimerUI timerUI, List<Interval> intervals) {
//        this.ui = timerUI;
//        this.intervals = intervals;
//        this.centralizedTimer = new AtomicTimerCount();
//        centralizedTimer.addObserver(this);
//    }
//
//    public void start() {
//        System.out.println("Starting timer!");
//        timer = new Timer();
//
//        TimerTask decrementTask = new TimerDecrementTask(this.centralizedTimer);
//        startInterval();
//        timer.scheduleAtFixedRate(decrementTask, 0, 1000);
//    }
//
//    private void startInterval() {
//        if (intervals.isEmpty()) {
//            end();
//            return;
//        }
//
//        Interval newInterval = intervals.remove(0);
//        ui.updateExercise(newInterval.getExercise());
//        centralizedTimer.setTimer(newInterval.getDuration());
//    }
//
//    private void end() {
//        timer.cancel();
//    }
//
//    @Override
//    public void update(Observable observable, Object timerValue) {
//        int currentTimerValue = (int) timerValue;
//        if (currentTimerValue <= 0) {
//            startInterval();
//        } else {
//            ui.updateTimer(centralizedTimer.getTimer());
//        }
//    }
//
//    public void setIntervals(List<Interval> intervals) {
//        this.intervals = intervals;
//    }
//
//    public TimerControllerOld timer(Timer timer) {
//        this.timer = timer;
//        return this;
//    }
//
//    public TimerControllerOld ui(TimerUI ui) {
//        this.ui = ui;
//        return this;
//    }
//
//    public TimerControllerOld intervals(List<Interval> intervals) {
//        this.intervals = intervals;
//        return this;
//    }
//
//    public TimerControllerOld centralizedTimer(AtomicTimerCount centralizedTimer) {
//        this.centralizedTimer = centralizedTimer;
//        return this;
//    }
//}
