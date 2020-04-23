package com.thojoeis.workout.timer.models;

import java.util.Optional;

public interface WorkoutSession {

    Optional<IntervalInfo> getCurrentInterval();

    Optional<IntervalInfo> nextInterval();

    boolean isCompleted();

}
