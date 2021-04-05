package net.thumbtack.school.threads;

public class Task3 {

    public static void main(String args[]) {
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("interrupted.");
            }
            System.out.println("exiting 1 thread");
        });

        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("interrupted.");
            }
            System.out.println("exiting 2 thread");
        });

        Thread t3 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("interrupted.");
            }
            System.out.println("exiting 3 thread");
        });

        t1.start();System.out.println("is 1 alive: " + t1.isAlive());
        t2.start();System.out.println("is 2 alive: " + t2.isAlive());
        t3.start();System.out.println("is 3 alive: " + t3.isAlive());

        //пока 2 поток не завершится - сообщение о том что 3 закончился не обработается, не смотря на то что 3 уже завершон
        try {
            t1.join();System.out.println("is 1 alive: " + t1.isAlive());
            t2.join();System.out.println("is 2 alive: " + t2.isAlive());
            t3.join();System.out.println("is 3 alive: " + t3.isAlive());
        } catch (InterruptedException ex) {
            System.out.println("Main thread Interrupted");
        }




    }
}
