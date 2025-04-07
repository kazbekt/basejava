package com.urise.webapp;
import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {
    private static int counter;
    private static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
            }
        };
        thread0.start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ", " +
                        Thread.currentThread().getState());
            }
        }).start();
        thread0.join();
        System.out.println(thread0.getState());
        MainConcurrency mc = new MainConcurrency();
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 10000; i++) {
            Thread thread = new Thread(() -> {
                System.out.println("Starts thread: " + Thread.currentThread().getName());
                for (int j = 0; j < 100; j++) {
                    System.out.print("was: " + counter + ", become: ");
                    mc.extracted();
                    System.out.print(counter);
                    System.out.println();
                }
            });
            thread.start();
            threads.add(thread);
        }
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println(counter);
    }

    private synchronized void extracted() {
        counter++;
    }
}