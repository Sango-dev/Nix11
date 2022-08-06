package ua.com.alevel.hw2;

import ua.com.alevel.hw2.comparator.CustomComparator;
import ua.com.alevel.hw2.controller.Controller;
import ua.com.alevel.hw2.customlist.CustomLinkedList;
import ua.com.alevel.hw2.factory.ProductFactory;
import ua.com.alevel.hw2.model.*;
import ua.com.alevel.hw2.tree.BSTree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Controller.run();
    }
}
