package net.thumbtack.school.threads;

//“Ping Pong”, задача заключается в том чтобы бесконечно выводить на консоль сообщения “ping” или “pong” из двух
// разных потоков. При этом сообщения обязаны чередоваться, т.е. не может быть ситуации когда ping или pong появляется
// в консоли более одного раза подряд. Первым должно быть сообщение “ping”.

import java.util.concurrent.Semaphore;

class Ball {
    private Semaphore semaphorePing = new Semaphore(1);
    private Semaphore semaphorePong = new Semaphore(0);

    public void ping() {
        try {
            semaphorePing.acquire();
            System.out.println("ping");
            Thread.sleep(10);
        } catch (InterruptedException e) {
            System.out.println(e);
        } finally {
            semaphorePong.release();
        }
    }

    public void pong() {
        try {
            semaphorePong.acquire();
            System.out.println("pong");
            Thread.sleep(10);
        } catch (InterruptedException e) {
            System.out.println(e);
        } finally {
            semaphorePing.release();
        }
    }
}

class Producer extends Thread {
    private Ball ball;

    public Producer(Ball ball) {
        this.ball = ball;
    }

    public void run() {
        while (true) {
            ball.ping();
        }
    }
}

class Consumer extends Thread {
    private Ball ball;

    public Consumer(Ball ball) {
        this.ball = ball;
    }

    public void run() {
        while (true) {
            ball.pong();
        }
    }
}

public class Task7 {

    public static void main(String args[]) {
        Ball ball = new Ball();
        new Producer(ball).start();
        new Consumer(ball).start();
    }
}
