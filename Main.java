import java.util.Arrays;
import java.util.Random;
import java.util.function.Consumer;

public class Main {

    public static void main(String[] args) {
        String[] algorithms = {
            "Selection Sort", "Insertion Sort", "Bubble Sort",
            "Merge Sort", "Quick Sort", "Radix Sort"
        };
        String[] cases = {"Random", "Reversed", "Partially Sorted", "Fully Sorted"};

        @SuppressWarnings("unchecked")
        Consumer<int[]>[] sorters = new Consumer[] {
            (Consumer<int[]>) SelectionSort::sort,
            (Consumer<int[]>) InsertionSort::sort,
            (Consumer<int[]>) BubbleSort::sort,
            (Consumer<int[]>) MergeSort::sort,
            (Consumer<int[]>) QuickSort::sort,
            (Consumer<int[]>) RadixSort::sort
        };

        Random random = new Random(2025);
        int[] base = DataGenerator.random(random);
        int[][] datasets = {
            base,
            DataGenerator.reversed(base),
            DataGenerator.partiallySorted(base, random),
            DataGenerator.sorted(base)
        };

        warmUp(sorters, random);

        System.out.printf("Sorting %,d student IDs for each algorithm and case...%n", DataGenerator.SIZE);

        long[][] nanos = new long[algorithms.length][cases.length];

        for (int a = 0; a < algorithms.length; a++) {
            for (int c = 0; c < cases.length; c++) {
                int[] copy = Arrays.copyOf(datasets[c], datasets[c].length);

                long start = System.nanoTime();
                sorters[a].accept(copy);
                long end = System.nanoTime();

                nanos[a][c] = end - start;

                int[] expected = DataGenerator.sorted(datasets[c]);
                if (!Arrays.equals(copy, expected)) {
                    throw new IllegalStateException(algorithms[a] + " produced a wrong result for " + cases[c]);
                }
            }
            System.out.println("Finished: " + algorithms[a]);
        }

        ResultPrinter.print("Execution time in nanoseconds", "ns", algorithms, cases, nanos, 1);
        ResultPrinter.print("Execution time in microseconds", "us", algorithms, cases, nanos, 1000);
    }

    private static void warmUp(Consumer<int[]>[] sorters, Random random) {
        for (int round = 0; round < 5; round++) {
            for (Consumer<int[]> sorter : sorters) {
                int[] small = new int[2000];
                for (int i = 0; i < small.length; i++) {
                    small[i] = DataGenerator.randomId(random);
                }
                sorter.accept(small);
            }
        }
    }
}
