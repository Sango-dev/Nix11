package ua.com.alevel.hw17.robots;

import ua.com.alevel.hw17.factory.Factory;

import java.util.Random;

public class RobotOne extends Thread{
    private static final Random RANDOM = new Random();

    @Override
    public void run() {
        while (true) {
            int fuelCnt = RANDOM.nextInt(500, 1000);
            System.out.println("Robot(1) gets " + fuelCnt + " gallons, transporting is starting...");
            try {
                Thread.sleep(3000);
                int total = Factory.getInstance().getFuelCnt().get() + fuelCnt;
                System.out.println("Robot(1) has transported " + fuelCnt + " gallons, total = " + total);
                Factory.getInstance().setFuelCnt(total);
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
