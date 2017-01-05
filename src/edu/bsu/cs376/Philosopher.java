package edu.bsu.cs376;


public class Philosopher implements Runnable {

    private final int PHILOSOPHER_ID;
    private final Chopstick LEFT_CHOPSTICK;
    private final Chopstick RIGHT_CHOPSTICK;
    boolean isInUse = false;

    public Philosopher(int philosopherId, Chopstick leftChopstick, Chopstick rightChopstick){
        this.PHILOSOPHER_ID = philosopherId;
        this.LEFT_CHOPSTICK = leftChopstick;
        this.RIGHT_CHOPSTICK = rightChopstick;
    }

    @Override
    public void run() {
        // conditional variable set to true
        setInUse(true);
        // runs even or odd specified to implement asymmetric chopstick taking
        if(PHILOSOPHER_ID % 2 == 0){
            runEven();
        }
        else {
            runOdd();
        }
        setInUse(false);
    }

    private void runEven(){
        while(MiscSubs.TotalEats <= MiscSubs.MAX_EATS){
            think();
            if(MiscSubs.TotalEats >= MiscSubs.MAX_EATS){
                break;
            }
            // takes chopsticks in order for even philosophers to avoid deadlock
            takeEven();
            if(MiscSubs.TotalEats >= MiscSubs.MAX_EATS){
                //returns chopsticks to avoid deadlock at the very end
                RIGHT_CHOPSTICK.returnChopstick();
                LEFT_CHOPSTICK.returnChopstick();
                break;
            }
            eat();
            RIGHT_CHOPSTICK.returnChopstick();
            LEFT_CHOPSTICK.returnChopstick();
        }
    }

    private void runOdd(){
        while(MiscSubs.TotalEats <= MiscSubs.MAX_EATS){
            think();
            if(MiscSubs.TotalEats >= MiscSubs.MAX_EATS){
                break;
            }
            // takes chopsticks in order for odd philosophers to avoid deadlock
            takeOdd();
            if(MiscSubs.TotalEats >= MiscSubs.MAX_EATS){
                //returns chopsticks to avoid deadlock at the very end
                RIGHT_CHOPSTICK.returnChopstick();
                LEFT_CHOPSTICK.returnChopstick();
                break;
            }
            eat();
            RIGHT_CHOPSTICK.returnChopstick();
            LEFT_CHOPSTICK.returnChopstick();
        }
    }

    private void think(){
        System.out.println("Philosopher " + PHILOSOPHER_ID + " is thinking");
        MiscSubs.RandomDelay();
    }

    private void takeOdd(){
        try {
            //asymmetric chopstick taking
            LEFT_CHOPSTICK.takeChopstick();
            RIGHT_CHOPSTICK.takeChopstick();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void takeEven(){
        try {
            //asymmetric chopstick taking
            RIGHT_CHOPSTICK.takeChopstick();
            LEFT_CHOPSTICK.takeChopstick();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void eat(){
        MiscSubs.StartEating(PHILOSOPHER_ID);
        System.out.println("Philosopher " + PHILOSOPHER_ID + " is eating");
        MiscSubs.RandomDelay();
        MiscSubs.DoneEating(PHILOSOPHER_ID);
    }

    public synchronized void setInUse(boolean inUse) {
        isInUse = inUse;
        notify();
    }

    public synchronized boolean isInUse() {
        return isInUse;
    }
}
