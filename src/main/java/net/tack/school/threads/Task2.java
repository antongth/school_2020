package net.thumbtack.school.threads;

public class Task2 {

    public static void main(String args[]) {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("interrupted.");
            }
            System.out.println("exiting child thread");
        });
        t.start();
        System.out.println("is alive: " + t.isAlive());
        try {
            t.join();
        } catch (InterruptedException ex) {
            System.out.println("main thread Interrupted");
        }
        System.out.println("is alive: " + t.isAlive());
    }
}
