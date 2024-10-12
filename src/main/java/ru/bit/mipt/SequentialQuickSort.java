package ru.bit.mipt;

public class SequentialQuickSort {
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = Common.partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }
}
