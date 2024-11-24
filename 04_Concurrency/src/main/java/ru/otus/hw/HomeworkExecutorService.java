package ru.otus.hw;

import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

public class HomeworkExecutorService implements Executor {

    private final AtomicBoolean isShutdown = new AtomicBoolean(false);
    private final LinkedList<Runnable> tasks = new LinkedList<>();
    private final Thread[] threads;

    private class Worker extends Thread {
        @Override
        public void run() {
            while (!tasks.isEmpty() || !isShutdown.get()) {
                getNextTask().ifPresent(Runnable::run);
            }
        }
    }

    public HomeworkExecutorService(int threadPoolSize) {
        threads = new Thread[threadPoolSize];
        for (int i = 0; i < threadPoolSize; i++) {
                threads[i] = new Worker();
                threads[i].start();
        }
    }

    @Override
    public synchronized void execute(Runnable command) {
        if (isShutdown.get()) {
            throw new IllegalStateException();
        }
        tasks.add(command);
        notifyAll();
    }

    public void shutdown() {
        isShutdown.compareAndSet(false, true);
    }

    private synchronized Optional<Runnable> getNextTask() {
        if (!isShutdown.get()) {
            return Optional.empty();
        }
        return Optional.of(tasks.removeFirst());
    }
}
