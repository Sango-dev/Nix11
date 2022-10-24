package ua.com.alevel.hw17;

import ua.com.alevel.hw17.factory.Factory;
import ua.com.alevel.hw17.robots.RobotFive;
import ua.com.alevel.hw17.robots.RobotFour;
import ua.com.alevel.hw17.robots.RobotOne;
import ua.com.alevel.hw17.robots.RobotTwoThree;

public class Main {
    public static void main(String[] args) {
        init();
    }

    public static void init() {
        RobotOne first = new RobotOne();
        RobotTwoThree second = new RobotTwoThree(2);
        RobotTwoThree third = new RobotTwoThree(3);
        RobotFour fourth = new RobotFour();
        RobotFive fifth = new RobotFive();

        first.setDaemon(true);
        second.setDaemon(true);
        third.setDaemon(true);
        fourth.setDaemon(true);
        fifth.setDaemon(true);

        first.start();
        second.start();
        third.start();
        fourth.start();
        fifth.start();

        while(Factory.getInstance().getFinalPointsDetail().get() < 100);
        System.out.println("The process of creating a detail is over!");
    }
}
