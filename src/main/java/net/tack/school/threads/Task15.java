package net.thumbtack.school.threads;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

class Data {
    int[] get() {
        return new int[] {new Random().nextInt(50)+1};
    }
}

class PoisonData extends Data{

}

class Writer1 implements Runnable {
    private Queue<Data> queue;
    private int count;

    public Writer1(Queue<Data> queue, int count) {
        this.queue = queue;
        this.count = count;
    }

    public void run() {
        System.out.println("Writer " + Thread.currentThread().getId() + " started work, where is left "
                + Task15.atomicWritersQuantity.get());
        for (int i = 0; i < count; i++) {
            queue.add(new Data());
            System.out.println("Writer " + Thread.currentThread().getId() + " added book. Totally in queue = "
                    + queue.size());
        }
        System.out.println("Writer " + Thread.currentThread().getId() + " finished  work, only left "
                + Task15.atomicWritersQuantity.decrementAndGet());
    }
}

class Reader1 implements Runnable {
    private Queue<Data> queue;

    public Reader1(Queue<Data> queue) {
        this.queue = queue;
    }

    public void run() {
        System.out.println("Reader " + Thread.currentThread().getId() + " started");
        while (true) {
            Data data = queue.poll();
            if (data instanceof PoisonData) {
                System.out.println("Reader " + Thread.currentThread().getId() + " finished, in queue " + queue.size());
                queue.add(data);
                break;
            }
            if (data == null) {
                System.out.println("Reader " + Thread.currentThread().getId() + " is over and over again trying to get the book");
                continue;
            }
            System.out.println("Reader " + Thread.currentThread().getId() + " get the book " + data);
        }
    }
}

public class Task15 {
    public static AtomicInteger atomicWritersQuantity = new AtomicInteger();

    public static void main(String[] args) {
        int writersQuantity = new Random().nextInt(5)+2;
        atomicWritersQuantity.set(writersQuantity);
        BlockingQueue<Data> queue = new LinkedBlockingQueue<>();

        System.out.println("where is " + atomicWritersQuantity.get() + " writers");

        ExecutorService esW = Executors.newFixedThreadPool(2);
        ExecutorService esR = Executors.newFixedThreadPool(2);

        for (int i = 0; i < writersQuantity; i++) {
            esW.execute(new Writer1(queue, 3));
        }

        for (int i = 0; i < new Random().nextInt(10)+1; i++) {
            esR.execute(new Reader1(queue));
        }

        esW.shutdown();
        try {
            if (!esW.awaitTermination(60, TimeUnit.MINUTES))
                esW.shutdownNow();
            queue.add(new PoisonData());
            System.out.println("Writers are finished " + atomicWritersQuantity.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        esR.shutdown();
        try {
            if (!esR.awaitTermination(60, TimeUnit.MINUTES))
                esR.shutdownNow();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
