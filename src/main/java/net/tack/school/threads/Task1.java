package net.thumbtack.school.threads;

public class Task1 {

    public static void main(String args[]) {
        Thread t = Thread.currentThread();
        System.out.println(t.getId());
        System.out.println(t.getName());
        System.out.println(t.getPriority());
        System.out.println(t.getState());
        System.out.println(t.getThreadGroup());
    }
}
