package edu.bsu.cs;

public class Chopstick {

    private boolean isInUse;

    public Chopstick(boolean isInUse){
        this.isInUse = isInUse;
    }

    public synchronized void returnChopstick() {
        //makes chopstick available and notifies threads
        isInUse = false;
        notify();
    }

    public synchronized void takeChopstick() throws InterruptedException {
        //monitor region waits for the chopstick to become available
        while (isInUse) {
            wait();
        }
        isInUse = true;
    }
}
