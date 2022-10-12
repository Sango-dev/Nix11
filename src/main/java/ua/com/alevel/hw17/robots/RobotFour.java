package ua.com.alevel.hw17.robots;

import ua.com.alevel.hw17.factory.Factory;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class RobotFour extends Thread {
    private static final Random RANDOM = new Random();

    @Override
    public void run() {
        while (true) {
            AtomicInteger totalPointShemaProgram = Factory.getInstance().getShemaProgramm();
            while (totalPointShemaProgram.get() < 100 && Factory.getInstance().getPoints().get() >= 100) {
                double badLuck = Math.random();
                int shemaPoints = RANDOM.nextInt(25, 35);
                System.out.println("Robot(4) programmed " + shemaPoints + " points");

                try {
                    Thread.sleep(1000);
                    if (badLuck > 0.3) {
                        Thread.sleep(1000);
                        int wholePoints = totalPointShemaProgram.get() + shemaPoints;
                        System.out.println("Robot(4) made " + shemaPoints + " points, total = " + wholePoints);
                        Factory.getInstance().setShemaProgramm(wholePoints);
                    } else {
                        Thread.sleep(1000);
                        System.out.println("Restart Robot(4)!!!");
                        Factory.getInstance().setShemaProgramm(0);
                    }

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
