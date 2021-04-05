package net.thumbtack.school.threads;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

// не потокобезопасный класс из-за SimpleDateFormat.
// Переносим его в ThreadLocal и теперь в каждом потоке будет своя копия экземпляра SimpleDateFormat
class Formatter {
    private final String PATTERN;
    private ThreadLocal<SimpleDateFormat> threadLocal = new InheritableThreadLocal<>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(PATTERN);
        }
    };

    public Formatter(String PATTERN) {
        this.PATTERN = PATTERN;
    }

    public void format(Date date) {
        System.out.println(threadLocal.get().format(date));
    }
}

public class Task13 {

    public static void main(String[] args) {
        Formatter formatter = new Formatter("yyyy.MM.dd G 'at' HH:mm:ss z");
        Date date = new Date();

        for (int i = 0; i < 3; i++)
            new Thread(() -> formatter.format(date)).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        formatter.format(date);
        System.out.println("end of main thread");
    }
}
