package net.thumbtack.school.threads;

import java.util.Random;
import java.util.concurrent.Semaphore;

class Shelf {
    private String book;
    private Semaphore semaphoreAuth;
    private Semaphore semaphoreReader;

    public Shelf() {
        semaphoreAuth = new Semaphore(1);
        semaphoreReader = new Semaphore(0);
    }

    public void put() {
        book = "A book of Legend" + new Random().nextInt(80000000);
        try {
            semaphoreAuth.acquire();
            System.out.println("put a book! " + book);
            Thread.sleep(10);
        } catch (InterruptedException e) {
            System.out.println(e);
        } finally {
            semaphoreReader.release();
        }
    }

    public void get() {
        try {
            semaphoreReader.acquire();
            System.out.println("get a book");
            Thread.sleep(10);
        } catch (InterruptedException e) {
            System.out.println(e);
        } finally {
            semaphoreAuth.release();
        }
    }
}

class Writer extends Thread {
    private Shelf shelf;

    public Writer(Shelf shelf) {
        this.shelf = shelf;
    }

    public void run() {
        while (true) {
            shelf.put();
        }
    }
}

class Reader extends Thread {
    private Shelf shelf;

    public Reader(Shelf shelf) {
        this.shelf = shelf;
    }

    public void run() {
        while (true) {
            shelf.get();
        }
    }
}

public class Task8 {
    public static void main(String args[]) {
        Shelf shelf = new Shelf();
        new Writer(shelf).start();
        new Reader(shelf).start();
    }
}
