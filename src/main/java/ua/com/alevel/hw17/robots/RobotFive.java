package ua.com.alevel.hw17.robots;


import ua.com.alevel.hw17.factory.Factory;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class RobotFive extends Thread {
    private static final Random RANDOM = new Random();

    @Override
    public void run() {
        while (true) {
            AtomicInteger fuel = Factory.getInstance().getFuelCnt();
            AtomicInteger schemaPoints = Factory.getInstance().getShemaProgramm();
            AtomicInteger finalPointsDetail = Factory.getInstance().getFinalPointsDetail();

            while (finalPointsDetail.get() < 100 && schemaPoints.get() >= 100) {
                int needFuel = RANDOM.nextInt(350, 700);
                System.out.println("Robot(5) will use " + needFuel + " gallons, " + "remaining amount of fuel " + fuel.get() + " gallons");
                while (needFuel > fuel.get()) ;
                int restFuel = fuel.get() - needFuel;
                int total = finalPointsDetail.get() + 10;
                System.out.println("Robot(5) made points of final detail: " + total);
                try {
                    Thread.sleep(1000);
                    Factory.getInstance().setFuelCnt(restFuel);
                    Factory.getInstance().setFinalPointsDetail(total);
                }
                catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
