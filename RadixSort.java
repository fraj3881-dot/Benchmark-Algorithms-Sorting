public class RadixSort {

    public static void sort(int[] a) {
        int max = a[0];
        for (int value : a) {
            if (value > max) {
                max = value;
            }
        }
        int[] output = new int[a.length];
        for (long exp = 1; max / exp > 0; exp *= 10) {
            countingSortByDigit(a, output, exp);
        }
    }

    private static void countingSortByDigit(int[] a, int[] output, long exp) {
        int[] count = new int[10];
        for (int value : a) {
            count[(int) ((value / exp) % 10)]++;
        }
        for (int d = 1; d < 10; d++) {
            count[d] += count[d - 1];
        }
        for (int i = a.length - 1; i >= 0; i--) {
            int digit = (int) ((a[i] / exp) % 10);
            output[--count[digit]] = a[i];
        }
        System.arraycopy(output, 0, a, 0, a.length);
    }
}
