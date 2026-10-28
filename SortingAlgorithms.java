package com.mycompany.librarysorting;

/**
 * SortingAlgorithms.java
 * Insertion Sort and Bubble Sort for an array of books.
 * Books are sorted by publication year (ascending).
 */
public class SortingAlgorithms {

    // ---------------- Insertion Sort ----------------
    public static void insertionSort(Book[] arr, int n) {
        if (n < 2) {
            return;
        }
        int j;
        Book temp;
        for (int i = 1; i < n; i++) {
            temp = arr[i];                                   // book to insert
            for (j = i; j > 0 && arr[j - 1].getYear() > temp.getYear(); j--) {
                arr[j] = arr[j - 1];                         // shift newer books to the right
            }
            arr[j] = temp;
        }
    }

    // ---------------- Bubble Sort ----------------
    public static void bubbleSort(Book[] arr, int n) {
        int i, j;
        Book temp;
        for (i = 0; i < n - 1; i++) {
            for (j = 0; j < n - 1; j++) {
                if (arr[j].getYear() > arr[j + 1].getYear()) {   // swap neighbours if out of order
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
}
