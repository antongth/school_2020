package net.thumbtack.school.threads;

// ConcurrentHashMap defines atomic operations which remove or replace a key-value pair only if the key is present,
// or add a key-value pair only if the key is absent

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class MyHashMap<K, V> extends HashMap<K, V> {
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public MyHashMap() {
        super();
    }

    public V putMy(K key, V value) {
        lock.writeLock().lock();
        try {
            return this.put(key, value);
        } finally {
            lock.writeLock().unlock();
        }

    }

    public V getMy(K key) {
        lock.readLock().lock();
        try {
            return this.get(key);
        } finally {
            lock.readLock().unlock();
        }
    }
}

public class Task12 {
    public static void main(String[] args) {
        MyHashMap<Integer, Integer> myHashMap = new MyHashMap<Integer, Integer>();

        for (int i = 0; i < 5; i++) {
            myHashMap.putMy(new Random().nextInt(10),10);
        }
        myHashMap.entrySet().stream().forEach(System.out::print);
        System.out.println();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                myHashMap.putMy(new Random().nextInt(15),10);
                myHashMap.entrySet().stream().forEach(System.out::print);
                System.out.println();
            }).start();
        }

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                myHashMap.getMy(new Random().nextInt(10));
                myHashMap.entrySet().stream().forEach(System.out::print);
                System.out.println();
            }).start();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        myHashMap.entrySet().stream().forEach(System.out::println);
    }
}
