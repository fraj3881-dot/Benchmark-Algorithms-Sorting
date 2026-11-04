public class ResultPrinter {

    public static void print(String title, String unit, String[] algorithms, String[] cases, long[][] nanos, long divisor) {
        System.out.println();
        System.out.println("=== " + title + " ===");
        System.out.printf("%-18s", "Algorithm");
        for (String c : cases) {
            System.out.printf(" | %-20s", c);
        }
        System.out.println();
        System.out.println("-".repeat(18 + cases.length * 23));
        for (int a = 0; a < algorithms.length; a++) {
            System.out.printf("%-18s", algorithms[a]);
            for (int c = 0; c < cases.length; c++) {
                System.out.printf(" | %,16d %-3s", nanos[a][c] / divisor, unit);
            }
            System.out.println();
        }
    }
}
