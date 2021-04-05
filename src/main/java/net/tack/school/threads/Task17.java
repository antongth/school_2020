package net.thumbtack.school.threads;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

class MultiTask {
    List<Executable> list = new LinkedList<>();

    public List<Executable> getList() {
        return list;
    }

    public void setList(List<Executable> list) {
        this.list = list;
    }
}

class PoisonMultiTask extends MultiTask{

}

class DeveloperU extends Thread{

    private Queue<MultiTask> queue;
    private Queue<Event> events;

    public DeveloperU(Queue<MultiTask> queue, Queue<Event> events) {
        this.queue = queue;
        this.events = events;
        this.start();
    }

    @Override
    public void run() {
        for (int i = 0; i < new Random().nextInt(4)+2; i++) {
            MultiTask multiTask = new MultiTask();
            for (int j = 0; j < new Random().nextInt(3) + 1; j++) {
                multiTask.getList().add(new Task());
            }
            queue.add(multiTask);
            events.add(Event.TASK_START);
            System.out.println("Multi task is added");
        }
    }
}

class ExecutorOne extends Thread{
    private Queue<MultiTask> queue;
    private Queue<Event> events;

    public ExecutorOne(Queue<MultiTask> queue, Queue<Event> events) {
        this.queue = queue;
        this.events = events;
        this.start();
    }

    @Override
    public void run() {
        while (true) {
            MultiTask multiTask = queue.poll();
            if (multiTask == null)
                continue;
            if (multiTask instanceof PoisonMultiTask) {
                queue.add(multiTask);
                break;
            }
            System.out.println("Multi task is polled by exec " + Thread.currentThread().getId());
            int s = multiTask.getList().size();
            if (s != 0) {
                if (multiTask.getList().remove(s-1) != null) {
                    queue.add(multiTask);
                    System.out.println("Multi task is added back by exec " + Thread.currentThread().getId());
                    continue;
                }
                events.add(Event.TASK_END);
            } else
                events.add(Event.TASK_END);

        }
    }
}

public class Task17 {
    public static void main(String[] args) {
        Queue<MultiTask> queue = new LinkedBlockingDeque<>();
        BlockingQueue<Event> events = new LinkedBlockingDeque<>();

        DeveloperU dev1 = new DeveloperU(queue, events);
        DeveloperU dev2 = new DeveloperU(queue, events);
        ExecutorOne devL1 = new ExecutorOne(queue, events);
        ExecutorOne devL2 = new ExecutorOne(queue, events);
        ExecutorOne devL3 = new ExecutorOne(queue, events);

        try {
            dev1.join();
            dev2.join();
            int taskCount = 0;
            while (true) {
                Event event = events.take();
                switch (event) {
                    case TASK_START:
                        taskCount++;
                        break;
                    case TASK_END:
                        taskCount--;
                        break;
                }
                if (taskCount == 0) {
                    queue.add(new PoisonMultiTask());
                    break;
                }
            }
            devL1.join();
            devL2.join();
            devL3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(queue.size());
        System.out.println(queue.peek().getClass());
    }
}
