package felix;

public class Sum {
    public static void main(String[] args) {
        int r = 0;
        int s = 0;
        int w = 11;
        int sum = 0;
        int counter = 0;

        for (r = 1; r <= w; r++) {
            for (s = r; s<w ;s++){
                sum = sum + s;
                System.out.println("Number:" + (++counter) + " Sum: " + sum + "___ Depth: " + r);
            }
        }
        System.out.println(r);
        System.out.println(sum);
    }
}
