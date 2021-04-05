package net.thumbtack.school.async;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
// Коэффициент корреляции Пирсона
// Линейный корреляционный анализ позволяет установить прямые связи между переменными величинами по их абсолютнымзначениям
// в файле file.txt содержатся номера экспериментов и величины для переменных x и y

public class Task1 {
    private static final int NUMDER_OF_CLIENTS = 20;
    // операция чтения элемента массива - атомарная
    private final static List<Integer> x = new ArrayList<>(NUMDER_OF_CLIENTS);
    private final static List<Integer> y = new ArrayList<>(NUMDER_OF_CLIENTS);

    private static void readFile(String filePath) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            String line;
            String[] parseLine;
            while ((line = bufferedReader.readLine()) != null) {
                parseLine = line.split("\t");
                x.add(Integer.valueOf(parseLine[1]));
                y.add(Integer.valueOf(parseLine[2]));
            }
            if (x.size() != y.size() && x.size() != NUMDER_OF_CLIENTS)
                throw new IOException("Incorrect experimental data");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

        double rxyPirson = 0;

        //начинаем pipeline передавая на входе файл
        CompletableFuture<Void> xyz = CompletableFuture
                .runAsync(() -> readFile("file.txt"))
                .handle((unused, throwable) -> {
                    System.out.println(throwable);
                    return unused;
                });

        //запускаем 5 потоков асинхронно
        CompletableFuture<Integer> sumX = xyz.thenApplyAsync(unused -> {
            int sum = 0;
            for (int i = 0; i < NUMDER_OF_CLIENTS; i++) {
                sum += x.get(i);
            }
            return sum;
        });

        CompletableFuture<Integer> sumY = xyz.thenApplyAsync(unused -> {
            int sum = 0;
            for (int i = 0; i < NUMDER_OF_CLIENTS; i++) {
                sum += y.get(i);
            }
            return sum;
        });

        CompletableFuture<Integer> nXY = xyz.thenApplyAsync(unused -> {
            int sum = 0;
            for (int i = 0; i < NUMDER_OF_CLIENTS; i++) {
                sum += x.get(i)*y.get(i);
            }
            return sum;
        }).thenApply(integer -> integer*NUMDER_OF_CLIENTS);

        CompletableFuture<Integer> XX = xyz.thenApplyAsync(unused -> {
            int sum = 0;
            for (int i = 0; i < NUMDER_OF_CLIENTS; i++) {
                sum += x.get(i)*x.get(i);
            }
            return sum;
        });

        CompletableFuture<Integer> YY = xyz.thenApplyAsync(unused -> {
            int sum = 0;
            for (int i = 0; i < NUMDER_OF_CLIENTS; i++) {
                sum += y.get(i)*y.get(i);
            }
            return sum;
        });

        // выводим результаты в основной поток
        // все это можно было написать в одной формуле, но не наглядно у каждой перемнной .get() писать
        CompletableFuture<Integer> v = sumX.thenCombine(sumY, (integer, integer2) -> integer*integer2);

        CompletableFuture<Integer> v0 = nXY.thenCombine(v, (integer, integer2) -> integer-integer2);

        CompletableFuture<Integer> v1 = XX.thenCombine(sumX, (integer, integer2) -> NUMDER_OF_CLIENTS*integer-integer2*integer2);

        CompletableFuture<Integer> v2 = YY.thenCombine(sumY, (integer, integer2) -> NUMDER_OF_CLIENTS*integer-integer2*integer2);

        CompletableFuture<Integer> v3 = v1.thenCombine(v2, (integer, integer2) -> integer*integer2);

        try {
            rxyPirson = (v0.get())/
                    Math.sqrt(v3.get());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(rxyPirson);
    }
}
