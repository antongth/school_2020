package net.thumbtack.school.threads;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

public class Task18 {

    public static void main(String[] args) {
        BlockingQueue<MultiTask> queue = new LinkedBlockingDeque<>();
        BlockingQueue<Event> events = new LinkedBlockingDeque<>();

        ExecutorService es = Executors.newFixedThreadPool(4);

        for (int i = 0; i < 5; i++) {
            es.submit(new DeveloperX(queue, events));
            new ExecutorOne(queue, events);
        }

        try {
            int taskCoun = 0;
            int devCoun = 0;
            while (true) {
                switch (events.take()) {
                    case DEV_START:
                        devCoun++;
                        break;
                    case TASK_START:
                        taskCoun++;
                        break;
                    case DEV_END:
                        devCoun--;
                        break;
                    case TASK_END:
                        taskCoun--;
                        break;
                }
                if (devCoun == 0)
                    if (taskCoun == 0) {
                        queue.add(new PoisonMultiTask());
                        break;
                    }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        es.shutdown();
        System.out.println(queue.size());
    }
}

class DeveloperX extends Thread {

    private Queue<MultiTask> queue;
    private Queue<Event> events;

    public DeveloperX(Queue<MultiTask> queue, Queue<Event> events) {
        this.queue = queue;
        this.events = events;
    }

    @Override
    public void run() {
        events.add(Event.DEV_START);
        for (int i = 0; i < new Random().nextInt(4)+2; i++) {
            MultiTask multiTask = new MultiTask();
            for (int j = 0; j < new Random().nextInt(3) + 1; j++) {
                multiTask.getList().add(new Task());
            }
            queue.add(multiTask);
            events.add(Event.TASK_START);
            System.out.println("Multi task is added");
        }
        events.add(Event.DEV_END);
    }
}

