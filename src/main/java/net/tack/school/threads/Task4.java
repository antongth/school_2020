package net.thumbtack.school.threads;

import java.util.ArrayList;
import java.util.List;
// В основном потоке создать ArrayList<Integer>. Запустить 2 потока на базе разных классов, один поток 10000 раз
// добавляет в список случайное целое число, другой 10000 раз удаляет элемент по случайному индексу (если при удалении
// выясняется, что список пуст, ничего не делать). Использовать внешний synchronized блок.
// Потоки должны работать конкурентно, то есть одновременно должно идти и добавление, и удаление.

public class Task4 {
    private static final int N = 10000;

    public static void main(String[] args) {
        List<Integer> arrayList = new ArrayList<>();

        Thread myThreadAdd = new Thread(() -> {
            System.out.println("enteringAdd");
            for (int i = 0; i < N; i++) {
                synchronized (arrayList) {
                    arrayList.add((int) (Math.random()*100));
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
                    synchronized (arrayList) {
                        arrayList.remove(a);
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
