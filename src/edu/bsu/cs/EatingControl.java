package edu.bsu.cs;

import java.util.Random;
import java.lang.Thread;

class EatingControl {
    static final int NUMBER_PHILOSOPHERS = 5;
    static final int NUMBER_CHOPSTICKS   = 5;
    static final int MAX_EATS = 500;
    static int TotalEats = 0;
    private static int EatCount[] = new int[NUMBER_PHILOSOPHERS];
    private static boolean EatingLog[] = new boolean[NUMBER_PHILOSOPHERS];
    private static Random r = new Random();

    static void InitializeChecking() {
        int i;
        for (i = 0; i < NUMBER_PHILOSOPHERS; i++) {
            EatingLog[i] = false;
        }
    }

    static synchronized void StartEating(int MyIndex) {
        TotalEats++;
        EatCount[MyIndex]++;
        EatingLog[MyIndex] = true;
        int LeftNeighbor = (MyIndex == 0)? NUMBER_PHILOSOPHERS-1:MyIndex-1;
        int RightNeighbor = (MyIndex + 1) % NUMBER_PHILOSOPHERS;
        if (EatingLog[LeftNeighbor] || EatingLog[RightNeighbor]) {
            System.out.println("ERROR! Philosopher " + MyIndex + " eating incorrectly");
        }
    }

    static synchronized void DoneEating(int MyIndex) {
        EatingLog[MyIndex] = false;
    }

    static void LogResults() {
        for (int i = 0; i < NUMBER_PHILOSOPHERS; i++) {
            System.out.println("EatCount " + i + " - " + EatCount[i]);
        }
    }

    static void RandomDelay() {
        int yCount = r.nextInt(900) + 100;
        for (int i = 0; i < yCount; i++) {
            Thread.currentThread();
            Thread.yield();
        }
    }
}
