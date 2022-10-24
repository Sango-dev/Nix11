package ua.com.alevel.hw17.robots;

import ua.com.alevel.hw17.factory.Factory;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class RobotTwoThree extends Thread {
    private final int number;
    private static final Random RANDOM = new Random();

    public RobotTwoThree(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        while (true) {
            AtomicInteger totalPoints = Factory.getInstance().getPoints();
            while (totalPoints.get() < 100) {
                int point = RANDOM.nextInt(10, 20);
                System.out.println("Robot(" + number + ") made detail for " + point + " points");
                try {
                    Thread.sleep(2000);
                    int wholePoints = totalPoints.get() + point;
                    System.out.println("Robot("+ number +") has a whole points: " + wholePoints);
                    Factory.getInstance().setPoints(wholePoints);
                }
                catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
