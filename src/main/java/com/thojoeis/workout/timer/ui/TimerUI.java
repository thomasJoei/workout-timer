package com.thojoeis.workout.timer.ui;

import com.thojoeis.workout.timer.models.IntervalInfo;

public interface TimerUI {
    void end();

    void updateTimer(int value);

    void updateIntervalInfo(IntervalInfo intervalInfo);
}
