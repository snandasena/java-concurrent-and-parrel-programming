package org.example; /**
 * Deciding how many bags of chips to buy for the party
 */

import java.util.concurrent.locks.*;
import java.util.concurrent.*;

class Shopper extends Thread {

    public static int bagsOfChips = 1; // start with one on the list
    private static Lock pencil = new ReentrantLock();
    private static CyclicBarrier fistBump = new CyclicBarrier(10);

    public Shopper(String name) {
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
            try {
                fistBump.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        } else { // "Barron"
            try {
                fistBump.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
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

public class BarrierDemo {
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