package net.thumbtack.school.threads;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// здесь работа на повторном заперании lock
class Ball1 {
    private Lock lock;
    private Condition conditionPing;
    private int i = 0;

    public Ball1(Lock lock) {
        this.lock = lock;
        conditionPing = lock.newCondition();
    }

    public void ping() {
        lock.lock();
        try {
            while (i == 1) {
                conditionPing.await();
            }
            System.out.println("ping");
            i++;
            conditionPing.signal();
            //Thread.sleep(10);
        } catch (InterruptedException e) {
            System.out.println(e);
        } finally {
            lock.unlock();
        }
    }

    public void pong() {
        lock.lock();
        try {
            while (i == 0) {
                conditionPing.await();
            }
            System.out.println("pong");
            i--;
            conditionPing.signal();
            //Thread.sleep(10);
        } catch (InterruptedException e) {
            System.out.println(e);
        } finally {
            lock.unlock();
        }
    }
}

class Producer1 extends Thread {
    private Ball1 ball;

    public Producer1(Ball1 ball) {
        this.ball = ball;
    }

    public void run() {
        while (true) {
            ball.ping();
        }
    }
}

class Consumer1 extends Thread {
    private Ball1 ball;

    public Consumer1(Ball1 ball) {
        this.ball = ball;
    }

    public void run() {
        while (true) {
            ball.pong();
        }
    }
}

public class Task11 {
    public static void main(String args[]) {
        Lock lock = new ReentrantLock();
        Ball1 ball = new Ball1(lock);
        new Consumer1(ball).start();
        new Producer1(ball).start();
    }
}
