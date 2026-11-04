public class BubbleSort {

    public static void sort(int[] a) {
        int n = a.length;
        boolean swapped = true;
        for (int i = 0; swapped && i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (a[j] > a[j + 1]) {
                    int temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                    swapped = true;
                }
            }
        }
    }
}
