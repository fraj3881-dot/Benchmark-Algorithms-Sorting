public class MergeSort {

    public static void sort(int[] a) {
        int[] temp = new int[a.length];
        sort(a, 0, a.length - 1, temp);
    }

    private static void sort(int[] a, int low, int high, int[] temp) {
        if (low >= high) {
            return;
        }
        int mid = (low + high) >>> 1;
        sort(a, low, mid, temp);
        sort(a, mid + 1, high, temp);
        merge(a, low, mid, high, temp);
    }

    private static void merge(int[] a, int low, int mid, int high, int[] temp) {
        int i = low;
        int j = mid + 1;
        int k = low;
        while (i <= mid && j <= high) {
            if (a[i] <= a[j]) {
                temp[k++] = a[i++];
            } else {
                temp[k++] = a[j++];
            }
        }
        while (i <= mid) {
            temp[k++] = a[i++];
        }
        while (j <= high) {
            temp[k++] = a[j++];
        }
        System.arraycopy(temp, low, a, low, high - low + 1);
    }
}
