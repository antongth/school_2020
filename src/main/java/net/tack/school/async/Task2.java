package net.thumbtack.school.async;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Task2 {
    private static final List<Integer> xFromFile = new ArrayList<>(10);
    private static final List<Integer> yFromFile = new ArrayList<>(10);
    private static final List<Integer> xplusyToFile = new ArrayList<>(10);
    private static final List<Integer> xprodyToFile = new ArrayList<>(10);

    public static void main(String[] args) {

        CompletableFuture<Void> xx = CompletableFuture.runAsync(() -> {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader("file1.txt", StandardCharsets.UTF_8))) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    xFromFile.add(Integer.valueOf(line));
                }
                System.out.println("read done 1");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        CompletableFuture<Void> yy = CompletableFuture.runAsync(() -> {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader("file2.txt", StandardCharsets.UTF_8))) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    yFromFile.add(Integer.valueOf(line));
                }
                System.out.println("read done 2");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        CompletableFuture<Void> zz = yy.acceptEither(xx, unused -> {
            for (int i = 0; i < 10; i++) {
                xplusyToFile.add(i, xFromFile.get(i) + yFromFile.get(i));
                xprodyToFile.add(i, xFromFile.get(i) * yFromFile.get(i));
            }
            System.out.println("logic done");
        });

        CompletableFuture<Void> xxx = zz.thenRunAsync(() -> {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("file3.txt", StandardCharsets.UTF_8))) {
                for (Integer i:xplusyToFile) {
                    bufferedWriter.write(String.valueOf(i));
                    bufferedWriter.newLine();
                }
                System.out.println("write done 1");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).handle((unused, throwable) -> {
            if (null != throwable) {
                System.out.println(throwable);
                throwable.printStackTrace();
                return null;
            } else {
                return unused;
            }
        });

        CompletableFuture<Void> yyy = zz.thenRunAsync(() -> {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("file4.txt", StandardCharsets.UTF_8))) {
                for (Integer i:xprodyToFile) {
                    bufferedWriter.write(String.valueOf(i));
                    bufferedWriter.newLine();
                }
                System.out.println("write done 2");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).handle((unused, throwable) -> {
            if (null != throwable) {
                System.out.println(throwable);
                throwable.printStackTrace();
                return null;
            } else {
                return unused;
            }
        });

        System.out.println("end of main");

        yyy.isDone();
        xxx.isDone();
    }

}
