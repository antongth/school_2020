package net.thumbtack.school.threads;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

interface Executable {
    void execute();
}

class Task implements Executable {
    @Override
    public void execute() {
        System.out.println("JuniorDeveloper exec");
    }
}

class PoisonTask extends Task {
    @Override
    public void execute() {
        System.out.println("JuniorDeveloper exec dead");
    }
}

class Developer implements Runnable{
    private Queue<Task> tasks;
    private int n;

    public Developer(Queue<Task> tasks) {
        this.tasks = tasks;
        n = new Random().nextInt(10)+2;
    }

    public void put() {
        for (int i = 0; i < n; i++) {
            tasks.add(new Task());
            System.out.println("JavaDeveloper add " + Task16.taskQuantity.incrementAndGet());
        }
    }

    @Override
    public void run() {
        put();
    }
}

class JuniorDeveloper implements Runnable{
    private Queue<Task> tasks;

    public JuniorDeveloper(Queue<Task> tasks) {
        this.tasks = tasks;
    }

    public void get() {
        while (true) {
            Task task = tasks.poll();
            if (task != null && task.getClass() == Task.class) {
                task.execute();
                Task16.taskQuantity.decrementAndGet();
            }
            if (task instanceof PoisonTask) {
                tasks.add(task);
                break;
            }
        }
    }

    @Override
    public void run() {
        get();
    }
}

public class Task16 {
    static AtomicInteger taskQuantity = new AtomicInteger();

    public static void main(String[] args) {
        BlockingQueue<Task> queue = new LinkedBlockingDeque<>();

        Thread producer1 = new Thread(new Developer(queue));
        Thread producer2 = new Thread(new Developer(queue));
        Thread producer3 = new Thread(new Developer(queue));
        Thread consumer1 = new Thread(new JuniorDeveloper(queue));
        Thread consumer2 = new Thread(new JuniorDeveloper(queue));

        consumer1.start();
        consumer2.start();
        producer1.start();
        producer2.start();
        producer3.start();

        try {
            producer1.join();
            producer2.join();
            producer3.join();
            queue.add(new PoisonTask());
            consumer1.join();
            consumer2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("not solved problems " + queue.size() + " " +queue.peek().getClass());
    }
}
