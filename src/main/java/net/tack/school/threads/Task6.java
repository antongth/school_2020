package net.thumbtack.school.threads;

// Можно ли корректно решить задачу 4 , используя Collections. synchronizedList и не используя synchronized явно ?
// Если да - напишите программу

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Task6 {
    private static final int N = 10000;

    public static void main(String[] args) {
        //В SynchronizedCollection коллекции все методы обрамлены блоком synchronized для получения синхронизированных коллекций
        // Эта коллекция требует синхронизации "составных операций"(не атомарных) - add, iterator
        List<Integer> arrayListSync = Collections.synchronizedList(new ArrayList<>());

        Thread myThreadAdd = new Thread(() -> {
            System.out.println("enteringAdd");
            for (int i = 0; i < N; i++) {
                synchronized (arrayListSync) {
                    arrayListSync.add((int) (Math.random()*100));
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
                if (arrayListSync.size() >= a) {
                    arrayListSync.remove(a);
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

        arrayListSync.stream().forEach(System.out::println);
    }
}
