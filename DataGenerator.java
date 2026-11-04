import java.util.Arrays;
import java.util.Random;

public class DataGenerator {

    public static final int SIZE = 100_000;
    private static final int MIN_ID = 10_000_000;
    private static final int ID_RANGE = 90_000_000;

    public static int randomId(Random random) {
        return MIN_ID + random.nextInt(ID_RANGE);
    }

    public static int[] random(Random random) {
        int[] data = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            data[i] = randomId(random);
        }
        return data;
    }

    public static int[] sorted(int[] base) {
        int[] data = Arrays.copyOf(base, base.length);
        Arrays.sort(data);
        return data;
    }

    public static int[] reversed(int[] base) {
        int[] data = sorted(base);
        for (int i = 0, j = data.length - 1; i < j; i++, j--) {
            int temp = data[i];
            data[i] = data[j];
            data[j] = temp;
        }
        return data;
    }

    public static int[] partiallySorted(int[] base, Random random) {
        int[] data = sorted(base);
        int scattered = data.length / 5;
        for (int k = 0; k < scattered; k++) {
            data[random.nextInt(data.length)] = randomId(random);
        }
        return data;
    }
}
