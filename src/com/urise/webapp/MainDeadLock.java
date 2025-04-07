package com.urise.webapp;

import java.util.Random;

public class MainDeadLock {
    static class BankAccount implements Runnable {
        private final Object LOCK = new Object();
        private int balance = Integer.MAX_VALUE;
        private final String name;

        @Override
        public void run() {
            System.out.println("Account " + getName() + "started opened");

        }

        public BankAccount(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void transfer(BankAccount toAccount, int amount) {
            if (this.balance >= amount) {
                System.out.println(Thread.currentThread().getName() +
                        " transferring " + amount + " from " + name + " to " + toAccount.name);
                synchronized (toAccount.LOCK) {
                    toAccount.balance += amount;
                    System.out.println(toAccount.name + " is locked");

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    synchronized (this.LOCK) {
                        System.out.println(this.name + " is locked");
                        this.balance -= amount;
                    }
                }

                System.out.println(name + " new balance: " + this.balance);
                System.out.println(toAccount.name + " new balance: " + toAccount.balance);
            } else {
                System.out.println("Insufficient funds in " + name);
            }
        }

        public void randomTransfer(BankAccount otherAccount) {
            Random random = new Random();
            for (int i = 0; i < 10000; i++) {
                this.transfer(otherAccount, random.nextInt(1000));
            }
        }
    }

    public static void main(String[] args) {

        BankAccount acc1 = new BankAccount("acc1");
        BankAccount acc2 = new BankAccount("acc2");

        new Thread(() -> acc1.randomTransfer(acc2), "Acc1_thread").start();
        new Thread(() -> acc2.randomTransfer(acc1), "Acc2_thread").start();

    }
}
