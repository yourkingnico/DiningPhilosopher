package edu.bsu.cs376;

public class Dining {

    private static Philosopher[] philosophers = new Philosopher[MiscSubs.NUMBER_PHILOSOPHERS];
    private static Chopstick[] chopsticks = new Chopstick[MiscSubs.NUMBER_CHOPSTICKS];

    public static void main(String args[])
    {
        System.out.println("Starting the Dining Philosophers Simulation\n");
        boolean complete = false;
        MiscSubs.InitializeChecking();
        makeChopsticks();
        makePhilosophers();
        while(!complete){
            //checks for all threads to complete
            int count = 0;
            for (Philosopher thinker : philosophers){
                if(!thinker.isInUse){
                    count++;
                }
                if(count >= MiscSubs.NUMBER_PHILOSOPHERS){
                    complete = true;
                }
            }
        }
        MiscSubs.LogResults();
    }

    private static void makeChopsticks(){
        for(int i = 0; i < MiscSubs.NUMBER_CHOPSTICKS; i++){
            chopsticks[i] = new Chopstick(false);
        }
    }

    private static void makePhilosophers(){
        int i = 0;
        philosophers[0] = new Philosopher(0,chopsticks[0], chopsticks[1] );
        Thread thread1 = new Thread(philosophers[0]);
        thread1.start();
        i++;
        // Makes and starts first Philosopher
        while( i < MiscSubs.NUMBER_PHILOSOPHERS){
            //Makes and starts each philosopher when the previous is started
            if(philosophers[i-1].isInUse()){
                philosophers[i] = new Philosopher(i, chopsticks[i], chopsticks[(i + 1) % MiscSubs.NUMBER_PHILOSOPHERS]);
                Thread thread = new Thread(philosophers[i]);
                thread.start();
                System.out.println("started " + i);
                i++;
            }
        }
    }
}
