package ru.bit.mipt;

import java.util.concurrent.RecursiveAction;

public class ParallelQuickSort extends RecursiveAction {
    private final int[] arr;
    private final int low;
    private final int high;
    private final int threshold;

    public ParallelQuickSort(int[] arr, int low, int high, int threshold) {
        this.arr = arr;
        this.low = low;
        this.high = high;
        this.threshold = threshold;
    }

    @Override
    protected void compute() {
        if (high - low < threshold) {
            SequentialQuickSort.quickSort(arr, low, high);
        } else {
            int pi = Common.partition(arr, low, high);
            ParallelQuickSort leftTask = new ParallelQuickSort(arr, low, pi - 1, threshold);
            ParallelQuickSort rightTask = new ParallelQuickSort(arr, pi + 1, high, threshold);
            invokeAll(leftTask, rightTask);
        }
    }
}
