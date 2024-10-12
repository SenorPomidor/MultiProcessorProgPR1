package ru.bit.mipt;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class QuickSortTest {
    private static final int SIZE = 100_000_000;
    private static final int THRESHOLD = 1_000;
    private static final int NUM_RUNS = 5;

    public static void main(String[] args) {
        long seqTotalTime = 0;
        long parTotalTime = 0;

        for (int run = 1; run <= NUM_RUNS; run++) {
            int[] array = generateRandomArray();
            int[] arrayForPar = array.clone();

            seqTotalTime = getSeqTotalTime(array, seqTotalTime, run);
            parTotalTime = getParTotalTime(arrayForPar, parTotalTime, run);
        }

        System.out.println("\nСреднее время последовательной сортировки: " + (seqTotalTime / NUM_RUNS) + " мс");
        System.out.println("Среднее время параллельной сортировки: " + (parTotalTime / NUM_RUNS) + " мс");
    }

    private static long getParTotalTime(int[] arrayForPar, long parTotalTime, int run) {
        ForkJoinPool pool = new ForkJoinPool(4);

        long startTime = System.currentTimeMillis();
        pool.invoke(new ParallelQuickSort(arrayForPar, 0, arrayForPar.length - 1, THRESHOLD));
        long endTime = System.currentTimeMillis();

        long parTime = endTime - startTime;
        parTotalTime += parTime;
        System.out.printf("Запуск %s: Время параллельной сортировки: %s мс%n", run, parTotalTime);

        return parTotalTime;
    }

    private static long getSeqTotalTime(int[] array, long seqTotalTime, int run) {
        long startTime = System.currentTimeMillis();
        SequentialQuickSort.quickSort(array, 0, array.length - 1);
        long endTime = System.currentTimeMillis();

        long seqTime = endTime - startTime;
        seqTotalTime += seqTime;
        System.out.printf("Запуск %s: Время последовательной сортировки: %s мс%n", run, seqTime);

        return seqTotalTime;
    }

    private static int[] generateRandomArray() {
        int[] arr = new int[SIZE];
        Random rand = new Random();

        for (int i = 0; i < SIZE; i++) {
            arr[i] = rand.nextInt();
        }

        return arr;
    }
}
