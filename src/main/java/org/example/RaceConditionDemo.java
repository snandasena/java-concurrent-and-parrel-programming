package org.example; /**
 * Deciding how many bags of chips to buy for the party
 */

import java.util.concurrent.locks.*;

class Shopper1 extends Thread {

    public static int bagsOfChips = 1; // start with one on the list
    private static Lock pencil = new ReentrantLock();

    public Shopper1(String name) {
        this.setName(name);
    }

    public void run() {
        if (this.getName().contains("Olivia")) {
            pencil.lock();
            try {
                bagsOfChips += 3;
                System.out.println(this.getName() + " ADDED three bags of chips.");
            } finally {
                pencil.unlock();
            }
        } else { // "Barron"
            pencil.lock();
            try {
                bagsOfChips *= 2;
                System.out.println(this.getName() + " DOUBLED the bags of chips.");
            } finally {
                pencil.unlock();
            }
        }
    }
}

public class RaceConditionDemo {
    public static void main(String args[]) throws InterruptedException  {
        // create 10 shoppers: Barron-0...4 and Olivia-0...4
        Shopper1[] shoppers = new Shopper1[10];
        for (int i=0; i<shoppers.length/2; i++) {
            shoppers[2*i] = new Shopper1("Barron-"+i);
            shoppers[2*i+1] = new Shopper1("Olivia-"+i);
        }
        for (Shopper1 s : shoppers)
            s.start();
        for (Shopper1 s : shoppers)
            s.join();
        System.out.println("We need to buy " + Shopper1.bagsOfChips + " bags of chips.");
    }
}