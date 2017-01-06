package edu.bsu.cs;

public class OddPhilosopher extends EvenPhilosopher implements Runnable {

    OddPhilosopher(int philosopherId, Chopstick leftChopstick, Chopstick rightChopstick) {
        super(philosopherId, leftChopstick, rightChopstick);
    }

    public void beginPhilosopher(){
        while(MiscSubs.TotalEats <= MiscSubs.MAX_EATS){
            think();
            if(MiscSubs.TotalEats >= MiscSubs.MAX_EATS){
                break;
            }
            // takes chopsticks in order for odd philosophers to avoid deadlock
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

    public void takeSticks(){
        try {
            //asymmetric chopstick taking
            LEFT_CHOPSTICK.takeChopstick();
            RIGHT_CHOPSTICK.takeChopstick();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
