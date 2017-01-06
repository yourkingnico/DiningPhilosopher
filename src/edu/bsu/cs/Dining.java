package edu.bsu.cs;

public class Dining {

    private static EvenPhilosopher[] philosophers = new EvenPhilosopher[EatingControl.NUMBER_PHILOSOPHERS];
    private static Chopstick[] chopsticks = new Chopstick[EatingControl.NUMBER_CHOPSTICKS];

    public static void main(String args[])
    {
        System.out.println("Starting the Dining Philosophers Simulation\n");
        boolean complete = false;
        EatingControl.InitializeChecking();
        makeChopsticks();
        makePhilosophers();
        while(!complete){
            //checks for all threads to complete
            int count = 0;
            for (EvenPhilosopher thinker : philosophers){
                if(!thinker.isInUse){
                    count++;
                }
                if(count >= EatingControl.NUMBER_PHILOSOPHERS){
                    complete = true;
                }
            }
        }
        EatingControl.LogResults();
    }

    private static void makeChopsticks(){
        for(int i = 0; i < EatingControl.NUMBER_CHOPSTICKS; i++){
            chopsticks[i] = new Chopstick(false);
        }
    }

    private static void makePhilosophers(){
        int i = 0;
        philosophers[0] = new EvenPhilosopher(0,chopsticks[0], chopsticks[1] );
        Thread thread1 = new Thread(philosophers[0]);
        thread1.start();
        i++;
        // Makes and starts first EvenPhilosopher
        while( i < EatingControl.NUMBER_PHILOSOPHERS){
            //Makes and starts each philosopher when the previous is started
            if(philosophers[i-1].isInUse()){
                if(i % 2 == 0){
                    philosophers[i] = new EvenPhilosopher(i, chopsticks[i], chopsticks[(i + 1) % EatingControl.NUMBER_PHILOSOPHERS]);
                }
                else{
                    philosophers[i] = new OddPhilosopher(i, chopsticks[i], chopsticks[(i + 1) % EatingControl.NUMBER_PHILOSOPHERS]);
                }
                Thread thread = new Thread(philosophers[i]);
                thread.start();
                System.out.println("started " + i);
                i++;
            }
        }
    }
}
