package ua.com.alevel.hw17.factory;

import java.util.concurrent.atomic.AtomicInteger;

public final class Factory {
    private AtomicInteger fuelCnt;
    private AtomicInteger points;
    private AtomicInteger finalPointsDetail;
    private AtomicInteger shemaProgramm;
    private static Factory instance;

    private Factory() {
        fuelCnt = new AtomicInteger(0);
        points = new AtomicInteger(0);
        shemaProgramm = new AtomicInteger(0);
        finalPointsDetail = new AtomicInteger(0);
    }

    public static Factory getInstance() {
        if (instance == null) {
            synchronized (Factory.class) {
                if (instance == null) {
                    instance = new Factory();
                }
            }
        }

        return instance;
    }

    public synchronized AtomicInteger getFuelCnt() {
        return fuelCnt;
    }

    public synchronized void setFuelCnt(int count) {
        fuelCnt.set(count);
    }

    public synchronized AtomicInteger getPoints() {
        return points;
    }

    public synchronized void setPoints(int detail) {
        points.set(detail);
    }

    public synchronized AtomicInteger getShemaProgramm() {
        return shemaProgramm;
    }

    public synchronized void setShemaProgramm(int schema) {
        shemaProgramm.set(schema);
    }

    public synchronized AtomicInteger getFinalPointsDetail() {
        return finalPointsDetail;
    }

    public synchronized void setFinalPointsDetail(int finalDetail) {
        finalPointsDetail.set(finalDetail);
    }
}