package com.thojoeis.workout.timer.ui;

import com.thojoeis.workout.timer.WorkoutSessionFactory;
import com.thojoeis.workout.timer.controllers.TimerController;
import com.thojoeis.workout.timer.controllers.TimerControllerImpl;
import com.thojoeis.workout.timer.models.Interval;
import com.thojoeis.workout.timer.models.IntervalInfo;
import com.thojoeis.workout.timer.models.IntervalType;
import com.thojoeis.workout.timer.models.WorkoutSession;
import org.teavm.jso.browser.Window;
import org.teavm.jso.dom.html.*;
import org.teavm.jso.dom.xml.NodeList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HtmlUI implements TimerUI {

    private TimerController timerController;
    private WorkoutSession workoutSession;

    private IntervalInfo currentInterval;
    private int currentIntervalTimeLeft;

    private static final int FULL_DASH_ARRAY = 283;

    private static final int ALERT_THRESHOLD = 3;
    private static final int WARNING_THRESHOLD = 8;
    private static final String ALERT_COLOR = "red";
    private static final String WARNING_COLOR = "orange";
    private static final String INFO_COLOR = "green";


    private static final int EXERCISE_MUSIC_VOLUME = 100;
    private static final int REST_MUSIC_VOLUME = 45;


    private HTMLButtonElement startButton;
    private HTMLButtonElement pauseButton;
    private HTMLButtonElement resumeButton;
    private HTMLButtonElement submitButton;

    public HtmlUI() {
//        List<Interval> exercises = new ArrayList(Arrays.asList(
//                new Interval(IntervalType.EXERCISE, "Mountain climbers", 5),
//                new Interval(IntervalType.EXERCISE, "Twisties", 7)
//        ));
//
//        WorkoutSessionFactory.getWorkoutSession(3, exercises, 7, 4);

        this.startButton = Window.current().getDocument().getElementById("start-button").cast();
        this.pauseButton = Window.current().getDocument().getElementById("pause-button").cast();
        this.resumeButton = Window.current().getDocument().getElementById("resume-button").cast();
        this.submitButton = Window.current().getDocument().getElementById("submit-button").cast();
        HTMLButtonElement addExerciseButton = Window.current().getDocument().getElementById("add-exercise-button").cast();

        startButton.listenClick(evt -> {
//            WorkoutSession workoutSession = WorkoutSessionFactory.getWorkoutSession(3, exercises, 7, 4);
            submit();
            this.timerController = new TimerControllerImpl(this, this.workoutSession);
            this.timerController.start();
            startButton.setHidden(true);
            pauseButton.setHidden(false);
            longBeep();
        });

        pauseButton.listenClick(evt -> {
            this.timerController.pause();
            pauseButton.setHidden(true);
            resumeButton.setHidden(false);
        });

        resumeButton.listenClick(evt -> {
            this.timerController.resume();
            resumeButton.setHidden(true);
            pauseButton.setHidden(false);
            longBeep();
        });

        submitButton.listenClick(evt -> {
            submit();
        });

        addExerciseButton.listenClick(evt -> {
            addExerciseInput();
        });


    }

    private HTMLElement getTimer() {
        return HTMLDocument.current().getElementById("base-timer-label");
    }

    private HTMLElement getExercise() {
        return HTMLDocument.current().getElementById("exercise");
    }
    private HTMLElement getNextExercise() {
        return HTMLDocument.current().getElementById("next-exercise");
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

    private HTMLAudioElement getDoubleBeep() {
        return HTMLDocument.current().getElementById("double-beep").cast();
    }

    private HTMLAudioElement getCrowdApplause() {
        return HTMLDocument.current().getElementById("crowd-applause").cast();
    }


    @Override
    public void end() {
        startButton.setHidden(false);
        submitButton.setHidden(false);
        pauseButton.setHidden(true);
        resumeButton.setHidden(true);

        doApplause();
    }

    @Override
    public void updateTimer(int time) {
//        System.out.println("Timer : " + value);
        this.currentIntervalTimeLeft = time;
        int minutes = time / 60;
        int seconds = time % 60;
        String timeString = String.format("%02d:%02d", minutes, seconds);
        getTimer().setInnerHTML(String.valueOf(timeString));

        if (this.currentInterval != null) {
            setCircleDasharray(this.currentIntervalTimeLeft, this.currentInterval.getInterval().getDuration());
            setRemainingPathColor(this.currentIntervalTimeLeft);
        }
        beep(this.currentInterval, time);
    }

    @Override
    public void updateIntervalInfo(IntervalInfo intervalInfo) {
        this.currentInterval = intervalInfo;
        getExercise().setInnerHTML(intervalInfo.getInterval().getLabel());
        getRoundCounter().setInnerHTML(String.format("%s / %s", intervalInfo.getCurrentRound(), intervalInfo.getTotalRounds()));
        getExerciseCounter().setInnerHTML(String.format("%s / %s", intervalInfo.getCurrentExercise(), intervalInfo.getExercisesPerRound()));

        if (intervalInfo.getNextInterval().isPresent() && intervalInfo.getNextInterval().get().isExercise()) {
            getNextExercise().setInnerHTML(String.format("Next : %s",intervalInfo.getNextInterval().get().getLabel()));
        } else {
            getNextExercise().setInnerHTML("");
        }

        manageSpotifyVolume(intervalInfo);
    }

    private void manageSpotifyVolume(IntervalInfo intervalInfo) {
        Optional<String> token = getSpotifyToken();
        if (token.isPresent()) {
            System.out.println("Token retrieved ! :" + token.get());
            if (intervalInfo.getInterval().isExercise()) {
                SpotifyService.setVolume(token.get(), EXERCISE_MUSIC_VOLUME);
            } else {
                SpotifyService.setVolume(token.get(), REST_MUSIC_VOLUME);
            }
        } else {
            System.out.println("No Token retrieved !");
        }
    }

    private Optional<String> getSpotifyToken() {
        String token = ((HTMLInputElement) HTMLDocument.current().getElementById("spotify-token").cast()).getValue();

        if (token == null || "".equals(token)) {
            return Optional.empty();
        }
        return Optional.of(token);
    }

    private void beep(IntervalInfo intervalInfo, int seconds) {
        int halfway = intervalInfo.getInterval().getDuration() / 2;

        if (intervalInfo.getInterval().isExercise() && halfway == seconds) {
            doubleBeep();
        }
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
    }

    private void longBeep() {
        getLongBeep().play();
    }

    private void doubleBeep() {
        getDoubleBeep().play();
    }

    private void submit() {

        int totalRounds = Integer.parseInt(((HTMLInputElement) HTMLDocument.current().getElementById("totalRounds").cast()).getValue());
        int roundRest = Integer.parseInt(((HTMLInputElement) HTMLDocument.current().getElementById("roundRest").cast()).getValue());
        int exerciseRest = Integer.parseInt(((HTMLInputElement) HTMLDocument.current().getElementById("exerciseRest").cast()).getValue());

        List<Interval> exercises = new ArrayList<>();
        for (int i = 0; i < getNbExercises(); i++) {

            int exerciseDuration = Integer.parseInt((
                    (HTMLInputElement) HTMLDocument.current().getElementById(String.format("exercise[%d][duration]", i)).cast())
                    .getValue()
            );

            String exerciseLabel = ((HTMLInputElement) HTMLDocument.current().getElementById(String.format("exercise[%d][label]", i)).cast()).getValue();
            exercises.add(new Interval(IntervalType.EXERCISE, exerciseLabel, exerciseDuration));
        }

        workoutSession = WorkoutSessionFactory.getWorkoutSession(totalRounds, exercises, exerciseRest, roundRest);
    }

    private int getNbExercises() {
        NodeList<HTMLElement> elements = (NodeList<HTMLElement>) HTMLDocument.current().getElementById("exercises").getElementsByTagName("div");
        return elements.getLength();
    }

    private void addExerciseInput() {
        System.out.println("Add exercise clicked !");
        int exerciseIndex = getNbExercises();

        HTMLElement label = HTMLDocument.current().createElement("label").cast();
        label.setAttribute("id", String.format("exercise%dlabel", exerciseIndex));
        label.setInnerHTML(String.format("exercise %d :", exerciseIndex + 1));

        HTMLInputElement labelInput = HTMLDocument.current().createElement("input").cast();
        labelInput.setAttribute("id", String.format("exercise[%d][label]", exerciseIndex));
        labelInput.setClassName("form-control");
        labelInput.setType("text");

        HTMLInputElement durationInput = HTMLDocument.current().createElement("input").cast();
        durationInput.setAttribute("id", String.format("exercise[%d][duration]", exerciseIndex));
        durationInput.setClassName("form-control");
        durationInput.setType("number");

        HTMLElement exerciseDiv = HTMLDocument.current().createElement("div");
        exerciseDiv.setAttribute("id", String.format("exercise[%d]", exerciseIndex));

        exerciseDiv.appendChild(label);
        exerciseDiv.appendChild(labelInput);
        exerciseDiv.appendChild(durationInput);
        HTMLDocument.current().getElementById("exercises").appendChild(exerciseDiv);
    }


    private float calculateTimeFraction(int timeLeft, int totalDuration) {

        return (float) timeLeft / (float) totalDuration;
    }

    private void setCircleDasharray(int timeLeft, int totalDuration) {
        Integer circleDashArray = (int) (calculateTimeFraction(timeLeft, totalDuration) * FULL_DASH_ARRAY);
        Integer circleDashOffset = FULL_DASH_ARRAY - (int) (calculateTimeFraction(timeLeft, totalDuration) * FULL_DASH_ARRAY);

        System.out.println("Set stroke-dasharray : " + circleDashArray.toString());

        HTMLDocument.current()
                .getElementById("base-timer-path-remaining")
//                .setAttribute("stroke-dasharray", circleDashArray.toString())
                .setAttribute("stroke-dashoffset", circleDashOffset.toString())
        ;
    }

    private void setRemainingPathColor(int timeLeft) {
        HTMLElement baseTimePath = HTMLDocument.current().getElementById("base-timer-path-remaining");
        String nativeClasses = baseTimePath.getAttribute("class")
                .replaceAll(ALERT_COLOR, "")
                .replaceAll(WARNING_COLOR, "")
                .replaceAll(INFO_COLOR, "")
                + " ";

        if (timeLeft <= ALERT_THRESHOLD) {
            baseTimePath.setAttribute("class", nativeClasses + ALERT_COLOR);
        } else if (timeLeft <= WARNING_THRESHOLD) {
            baseTimePath.setAttribute("class", nativeClasses + WARNING_COLOR);
        } else {
            baseTimePath.setAttribute("class", nativeClasses + INFO_COLOR);
        }
    }

    private void doApplause() {
        HTMLDocument.current().getElementById("end-overlay").setHidden(false);
        getCrowdApplause().play();
    }

}
