package net.thumbtack.school.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Task10 {
    private static final int N = 10000;

    public static void main(String[] args) {
        List<Integer> arrayList = new ArrayList<>();
        Lock lock = new ReentrantLock();

        Thread myThreadAdd = new Thread(() -> {
            System.out.println("enteringAdd");
            for (int i = 0; i < N; i++) {
                try {
                    lock.lock();
                    arrayList.add((int) (Math.random()*100));
                } finally {
                    lock.unlock();
                }
                System.out.println("added");
            }
            System.out.println("exitingAdd");
        });

        Thread myThreadDell = new Thread(() -> {
            System.out.println("enteringDell");
            String str;
            for (int i = 0; i < N; i++) {
                str = "removed";
                int a = (int)(Math.random()*N);
                if (arrayList.size() >= a) {
                    try {
                        lock.lock();
                        arrayList.remove(a);
                    } finally {
                        lock.unlock();
                    }
                } else str = "not remove";
                System.out.println(str);
            }
            System.out.println("exitingDell");
        });

        myThreadAdd.start();
        myThreadDell.start();

        try {
            myThreadAdd.join();
            myThreadDell.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        arrayList.stream().forEach(System.out::println);
    }
}
