package net.thumbtack.school.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class ListClass {
    private static final int N = 10000;
    private List<Integer> arrayList;
    private String str;

    public ListClass(List<Integer> arrayList) {
        this.arrayList = arrayList;
    }

    public synchronized void add() {
        arrayList.add(new Random().nextInt(100));
        System.out.println("added");
    }

    public synchronized void dell() {
        int a = (int)(Math.random()*N);
        if (arrayList.size() >= a) {
            arrayList.remove(a);
            System.out.println("removed");
        } else
            System.out.println("not remove");
    }

    public int getN() {
        return N;
    }
}

class MyThread extends Thread {
    private ListClass sc;
    private Task5.ToBeOrNot flag;

    public MyThread(ListClass sc, Task5.ToBeOrNot flag) {
        this.sc = sc;
        this.flag = flag;
    }

    public void run() {
        if (flag == Task5.ToBeOrNot.ADD) {
            for (int i = 0; i < sc.getN(); i++) {
                sc.add();
            }
        } else if (flag == Task5.ToBeOrNot.DELL){
            for (int i = 0; i < sc.getN(); i++) {
                sc.dell();
            }
        }
    }
}

public class Task5 {

    enum ToBeOrNot {
        ADD, DELL
    }

    public static void main(String[] args) {
        List<Integer> arrayList = new ArrayList<>();
        ListClass sc = new ListClass(arrayList);
        MyThread add = new MyThread(sc, ToBeOrNot.ADD);
        MyThread dell = new MyThread(sc, ToBeOrNot.DELL);

        add.start();
        dell.start();

        try {
            add.join();
            dell.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(arrayList.size());
    }

}
