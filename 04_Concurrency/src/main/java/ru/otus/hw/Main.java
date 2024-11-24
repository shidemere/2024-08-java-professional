package ru.otus.hw;

import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        AtomicInteger one = new AtomicInteger(0);
        AtomicInteger two = new AtomicInteger(250);
        AtomicInteger three = new AtomicInteger(500);
        AtomicInteger four = new AtomicInteger(750);
        HomeworkExecutorService executorService = new HomeworkExecutorService(4);
        Runnable from0to250 = () -> {
            while (one.get() < 250) {System.out.println(Thread.currentThread().getName() + ": " + one.getAndIncrement());}
        };
        Runnable from250to500 = () -> {
            while (two.get() < 500) {System.out.println(Thread.currentThread().getName() + ": " + two.getAndIncrement());}
        };
        Runnable from500to750 = () -> {
            while (three.get() < 750) {System.out.println(Thread.currentThread().getName() + ": " + three.getAndIncrement());}
        };
        Runnable from750to1000 = () -> {
            while (four.get() < 1000) {System.out.println(Thread.currentThread().getName() + ": " + four.getAndIncrement());}
        };

        executorService.execute(from0to250);
        executorService.execute(from250to500);
        executorService.execute(from500to750);
        executorService.execute(from750to1000);
        executorService.shutdown();
    }
}