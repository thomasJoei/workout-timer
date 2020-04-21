package com.thojoeis.workout.timer;

import com.thojoeis.workout.timer.ui.ConsoleUI;
import com.thojoeis.workout.timer.ui.HtmlUI;
import com.thojoeis.workout.timer.ui.TimerUI;
import org.teavm.jso.browser.Window;
import org.teavm.jso.dom.html.HTMLButtonElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Client {
    public static void main(String[] args) throws InterruptedException {
//        TimerUI ui = new HtmlUI();
//        TimerUI ui = new ConsoleUI();
//        TimerController controller = new TimerController(ui);
//        controller.start();
//        Thread.sleep(1000L * 2);

//        HTMLButtonElement startButton = Window.current().getDocument().getElementById("start-button").cast();

        TimerUI ui = new HtmlUI();


//        List<Interval> intervals = new ArrayList(Arrays.asList(
//                new Interval(IntervalType.EXERCISE, "Mountain climbers", 5),
//                new Interval(IntervalType.REST, "Rest", 6),
//                new Interval(IntervalType.EXERCISE, "Twisties", 7)
//        ));
//        Round round1 = new Round(intervals);
//        Round round2 = new Round(intervals);
//        Round round3 = new Round(intervals);


        List<Interval> exercises = new ArrayList(Arrays.asList(
                new Interval(IntervalType.EXERCISE, "Mountain climbers", 5),
                new Interval(IntervalType.EXERCISE, "Twisties", 7)
        ));

        WorkoutSessionFactory.getWorkoutSession(3, exercises, 7, 4);


//        WorkoutSession workoutSession = new WorkoutSession(new ArrayList<Round>((Arrays.asList(round1, round2, round3))), 5 );
//        WorkoutSession workoutSession = WorkoutSessionFactory.getWorkoutSession(3, exercises, 7, 4);

//        TimerController controller = new TimerController(ui, workoutSession);
//        controller.start();

        HTMLButtonElement startButton = Window.current().getDocument().getElementById("start-button").cast();
//        startButton.listenClick(evt -> controller.start());
        startButton.listenClick(evt -> {
            WorkoutSession workoutSession = WorkoutSessionFactory.getWorkoutSession(3, exercises, 7, 4);

            TimerController controller = new TimerController(ui, workoutSession);
            controller.start();
        });
        Thread.sleep(1000L * 2);
    }

    public static void main2(String[] args) throws InterruptedException {

        TimerUI ui = new ConsoleUI();

        List<Interval> exercises = new ArrayList(Arrays.asList(
                new Interval(IntervalType.EXERCISE, "Mountain climbers", 5),
                new Interval(IntervalType.EXERCISE, "Twisties", 7)
        ));

        WorkoutSession workoutSession = WorkoutSessionFactory.getWorkoutSession(3, exercises, 7, 4);

        TimerController controller = new TimerController(ui, workoutSession);
        controller.start();

        Thread.sleep(1000L * 2);
    }


//    public static void main2(String[] args) throws InterruptedException {
//        HTMLDocument document = HTMLDocument.current();
//        HTMLElement div = document.createElement("div");
//        div.appendChild(document.createTextNode("TeaVM generated element"));
//        document.getBody().appendChild(div);
//
//        HTMLElement timerDiv = document.createElement("div");
//        final Text timerText = document.createTextNode("Timer");
//        timerDiv.appendChild(timerText);
//
//        document.getBody().appendChild(timerDiv);
//
////        Thread.sleep(1000L * 2);
////        timerText.setNodeValue("Test test");
//
////        Timer timer = new Timer(true);
//        Timer timer = new Timer();
//
//        int initialTimerValue = 30;
//        final java.util.concurrent.atomic.AtomicInteger timerUI = new AtomicInteger(initialTimerValue);
//
//        TimerTask task = new TimerTask() {
//            public void run() {
//                if (timerUI.get() <= 0) {
//                    this.cancel();
//                }
//
//                String currentTimer = "Time :" + timerUI.getAndDecrement();
//                System.out.println(currentTimer);
//                timerText.setNodeValue(currentTimer);
//                // TODO: stop the thread here
//            }
//        };
//        timer.scheduleAtFixedRate(task, 0, 1000);
//
//        Thread.sleep(1000L * 2);
//    }
}
