package com.mycompany.librarysorting;

import java.io.PrintStream;
import java.util.Arrays;

/**
 * Main.java
 * Library of 5 books. Measures (in nanoseconds) the time of Insertion Sort and
 * Bubble Sort on 4 scenarios: Random, Sorted Reversely, Sorted Partially, Sorted Fully.
 */
public class Main {

    // How many times each measurement is repeated (each run uses a fresh copy of the data).
    // With only 5 books one run takes a few hundred ns, so we report the average of many runs.
    // Set REPEATS = 1 for a single raw measurement.
    static final int REPEATS = 1000;

    // The library: 5 books, listed here from the oldest to the newest (sorted by year)
    static Book[] createLibrary() {
        return new Book[]{
            new Book(1, "Pride and Prejudice",     "Jane Austen",        1813, 432),
            new Book(2, "Les Miserables",          "Victor Hugo",        1862, 1463),
            new Book(3, "The Great Gatsby",        "F. Scott Fitzgerald", 1925, 180),
            new Book(4, "1984",                    "George Orwell",      1949, 328),
            new Book(5, "One Hundred Years of Solitude", "Gabriel Garcia Marquez", 1967, 417)
        };
    }

    // Builds a scenario by choosing the order of the books (indexes into the sorted library)
    static Book[] build(Book[] sorted, int[] order) {
        Book[] a = new Book[order.length];
        for (int i = 0; i < order.length; i++) {
            a[i] = sorted[order[i]];
        }
        return a;
    }

    static final String[] SCENARIOS = {"Random", "Sorted Reversely", "Sorted Partially", "Sorted Fully"};

    static void sort(int algo, Book[] a) {
        if (algo == 0) {
            SortingAlgorithms.insertionSort(a, a.length);
        } else {
            SortingAlgorithms.bubbleSort(a, a.length);
        }
    }

    static boolean isSorted(Book[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i].getYear() > a[i + 1].getYear()) {
                return false;
            }
        }
        return true;
    }

    // returns the average sorting time in nanoseconds
    static long measure(int algo, Book[] original) {
        long total = 0;
        for (int r = 0; r < REPEATS; r++) {
            // 1. independent copy of the original data (NOT timed)
            Book[] testArray = Arrays.copyOf(original, original.length);
            // 2. start time
            long startTime = System.nanoTime();
            // 3. run the algorithm
            sort(algo, testArray);
            // 4. end time
            long endTime = System.nanoTime();
            total += (endTime - startTime);
        }
        return total / REPEATS;
    }


    static void printBooks(String title, Book[] a) {
        System.out.println(title);
        for (Book b : a) {
            System.out.println("   " + b);
        }
    }

    public static void main(String[] args) throws Exception {
        System.setOut(new PrintStream(System.out, true, "UTF-8"));

        Book[] library = createLibrary();            // sorted by year

        // the 4 scenarios (indexes of the books in the sorted library)
        Book[][] cases = {
            build(library, new int[]{2, 4, 0, 3, 1}),   // Random
            build(library, new int[]{4, 3, 2, 1, 0}),   // Sorted Reversely
            build(library, new int[]{0, 1, 4, 2, 3}),   // Sorted Partially (4 of 5 books = 80% in order)
            build(library, new int[]{0, 1, 2, 3, 4})    // Sorted Fully
        };

        System.out.println("=== Library: " + library.length + " books (sorted by publication year) ===");
        for (int c = 0; c < cases.length; c++) {
            printBooks("Scenario - " + SCENARIOS[c] + ":", cases[c]);
        }

        // warm-up (not measured) so the JIT compiler does not distort the first results
        for (int w = 0; w < 20000; w++) {
            for (int c = 0; c < cases.length; c++) {
                sort(0, Arrays.copyOf(cases[c], cases[c].length));
                sort(1, Arrays.copyOf(cases[c], cases[c].length));
            }
        }

        // check that both algorithms really sort every scenario
        for (int c = 0; c < cases.length; c++) {
            for (int a = 0; a < 2; a++) {
                Book[] t = Arrays.copyOf(cases[c], cases[c].length);
                sort(a, t);
                if (!isSorted(t)) {
                    System.out.println("ERROR: not sorted!");
                    return;
                }
            }
        }

        long[][] result = new long[2][4];            // [algorithm][scenario]
        for (int a = 0; a < 2; a++) {
            for (int c = 0; c < cases.length; c++) {
                result[a][c] = measure(a, cases[c]);
            }
        }

        // ---- results table ----
        System.out.println();
        System.out.println("=== Sorting time in nanoseconds (average of " + REPEATS + " runs) ===");
        System.out.printf("%-16s | %-14s | %-18s | %-18s | %-14s%n",
                "Algorithm", "Random", "Sorted Reversely", "Sorted Partially", "Sorted Fully");
        System.out.println("-".repeat(92));
        String[] names = {"Insertion Sort", "Bubble Sort"};
        for (int a = 0; a < 2; a++) {
            System.out.printf("%-16s | %-14s | %-18s | %-18s | %-14s%n", names[a],
                    result[a][0] + " ns", result[a][1] + " ns", result[a][2] + " ns", result[a][3] + " ns");
        }

        // ---- comparison per scenario ----
        System.out.println();
        System.out.println("=== Comparison ===");
        for (int c = 0; c < cases.length; c++) {
            long ins = result[0][c], bub = result[1][c];
            String winner = ins <= bub ? "Insertion Sort" : "Bubble Sort";
            double ratio = ins <= bub ? (double) bub / Math.max(ins, 1) : (double) ins / Math.max(bub, 1);
            System.out.printf("%-17s: Insertion = %d ns, Bubble = %d ns -> faster: %s (x%.2f)%n",
                    SCENARIOS[c], ins, bub, winner, ratio);
        }

        // ---- same table in Markdown (copy it into README.md) ----
        System.out.println();
        System.out.println("| Algorithm | Random | Sorted Reversely | Sorted Partially | Sorted Fully |");
        System.out.println("|---|---|---|---|---|");
        for (int a = 0; a < 2; a++) {
            System.out.printf("| %s | %d ns | %d ns | %d ns | %d ns |%n", names[a],
                    result[a][0], result[a][1], result[a][2], result[a][3]);
        }
    }
}
