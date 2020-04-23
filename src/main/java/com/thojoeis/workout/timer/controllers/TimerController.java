package com.thojoeis.workout.timer.controllers;

import com.thojoeis.workout.timer.models.WorkoutSession;

public interface TimerController {


    void start();

    void pause();

    void resume();

    void setWorkoutSession(WorkoutSession workoutSession);

}
