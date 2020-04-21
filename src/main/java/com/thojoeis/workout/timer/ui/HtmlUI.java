package com.thojoeis.workout.timer.ui;

import com.thojoeis.workout.timer.IntervalInfo;
import org.teavm.jso.dom.html.HTMLAudioElement;
import org.teavm.jso.dom.html.HTMLDocument;
import org.teavm.jso.dom.html.HTMLElement;
import org.teavm.jso.dom.html.HTMLMediaElement;

public class HtmlUI implements TimerUI {

    private HTMLElement timer;
    private HTMLElement exercise;
    private HTMLElement roundCounter;
    private HTMLElement exerciseCounter;

    public HtmlUI() {
//        HTMLDocument document = HTMLDocument.current();
//        HTMLElement timerDiv = document.createElement("div");
//        timer = document.getElementById("timer");
//        exercise = document.getElementById("exercise");
//        timer = document.createTextNode("Timer");
//        exercise = document.createTextNode("Exercise");
//        timerDiv.appendChild(timer);
//        timerDiv.appendChild(exercise);

//        document.getBody().appendChild(timerDiv);

    }

    private HTMLElement getTimer() {
        return HTMLDocument.current().getElementById("base-timer-label");
    }

    private HTMLElement getExercise() {
        return HTMLDocument.current().getElementById("exercise");
    }

    private HTMLElement getRoundCounter() {
        return HTMLDocument.current().getElementById("round-counter");
    }

    private HTMLElement getExerciseCounter() {
        return HTMLDocument.current().getElementById("exercise-counter");
    }

    private HTMLAudioElement getShortBeep() {
        return HTMLDocument.current().getElementById("short-beep").cast();
    }
    private HTMLAudioElement getLongBeep() {
        return HTMLDocument.current().getElementById("long-beep").cast();
    }

    public void updateTimer(int time) {
//        System.out.println("Timer : " + value);
        int minutes = time / 60;
        int seconds = time % 60;
        String timeString = String.format("%02d:%02d", minutes, seconds);
        getTimer().setInnerHTML(String.valueOf(timeString));

        beep(seconds);
    }

    @Override
    public void updateIntervalInfo(IntervalInfo intervalInfo) {
        getExercise().setInnerHTML(intervalInfo.getInterval().getLabel());
        getRoundCounter().setInnerHTML(String.format("%s / %s", intervalInfo.getCurrentRound(), intervalInfo.getTotalRounds()));
        getExerciseCounter().setInnerHTML(String.format("%s / %s", intervalInfo.getCurrentExercise(), intervalInfo.getExercisesPerRound()));
    }

    private void beep(int seconds) {
        if (seconds <= 3) {
            if (seconds <= 0) {
                longBeep();
            } else {
                shortBeep();
            }
        }
    }

    private void shortBeep() {
        getShortBeep().play();
//        HTMLAudioElement sourceMp3 = HTMLDocument.current().createElement("beep").cast();
//        sourceMp3.setSrc("audio/shortbeep.mp3");
//        sourceMp3.setAttribute("type", "audio/mpeg");

    }
    private void longBeep() {
        getLongBeep().play();
    }

}
