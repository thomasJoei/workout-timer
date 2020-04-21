package com.thojoeis.workout.timer;

import java.util.List;
import java.util.Optional;

public interface WorkoutSession {

    Optional<IntervalInfo> getCurrentInterval();

    Optional<IntervalInfo> nextInterval();

//    public boolean isLastRound() {
//        return getTotalRemainingRounds() <= 0;
//    }
//
//    public int getTotalRemainingRounds() {
//        return  rounds.size() - (roundIndex + 1);
//    }




    int getTotalRounds();


    boolean isCompleted();



}
