package edu.bsu.cs;

public class EvenPhilosopher implements Runnable {

    private final int PHILOSOPHER_ID;
    final Chopstick LEFT_CHOPSTICK;
    final Chopstick RIGHT_CHOPSTICK;
    boolean isInUse = false;

    EvenPhilosopher(int philosopherId, Chopstick leftChopstick, Chopstick rightChopstick){
        this.PHILOSOPHER_ID = philosopherId;
        this.LEFT_CHOPSTICK = leftChopstick;
        this.RIGHT_CHOPSTICK = rightChopstick;
    }

    @Override
    public void run() {
        // conditional variable set to true
        setInUse(true);
        // runs even or odd specified to implement asymmetric chopstick taking
        beginPhilosopher();
        setInUse(false);
    }

    public void beginPhilosopher(){
        while(MiscSubs.TotalEats <= MiscSubs.MAX_EATS){
            think();
            if(MiscSubs.TotalEats >= MiscSubs.MAX_EATS){
                break;
            }
            // takes chopsticks in order for even philosophers to avoid deadlock
            takeSticks();
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

    void think(){
        System.out.println("Philosopher " + PHILOSOPHER_ID + " is thinking");
        MiscSubs.RandomDelay();
    }

    public void takeSticks(){
        try {
            //asymmetric chopstick taking
            RIGHT_CHOPSTICK.takeChopstick();
            LEFT_CHOPSTICK.takeChopstick();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void eat(){
        MiscSubs.StartEating(PHILOSOPHER_ID);
        System.out.println("Philosopher " + PHILOSOPHER_ID + " is eating");
        MiscSubs.RandomDelay();
        MiscSubs.DoneEating(PHILOSOPHER_ID);
    }

    private synchronized void setInUse(boolean inUse) {
        isInUse = inUse;
        notify();
    }

    synchronized boolean isInUse() {
        return isInUse;
    }
}
