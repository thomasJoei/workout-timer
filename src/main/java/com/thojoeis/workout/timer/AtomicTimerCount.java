package com.thojoeis.workout.timer;

import java.util.Observable;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicTimerCount extends Observable {

    private AtomicInteger timer;

    public AtomicTimerCount() {
        this.timer = new AtomicInteger(0);
    }

    public int getTimer() {
        return this.timer.get();
    }

    public void setTimer(int value) {
        this.timer.getAndSet(value);
        this.setChanged();
        this.notifyObservers(value);
    }

    public void decrementTimer() {
        int value = this.timer.getAndDecrement();
        this.setChanged();
        this.notifyObservers(value);
    }
}
