package structure;

public class TestAHa {

    public static void main(String args[]) {

        //Scanner scanner = new Scanner(System.in);

        int height = 113;
        int allHeights[] = {
                100, 104, 114, 115, 108, 131, 120, 119, 107, 118
        };
        System.out.println(pickApple(height, allHeights));
    }

    /**
     * 从applesHeight里面找出所有不大于height的数的个数
     *
     * @param height
     * @param applesHeight
     * @return
     */
    private static int pickApple(int height, int[] applesHeight) {
        int sum = 0;
        for (int h : applesHeight) {
            if (height >= h) {
                sum++;
            }
        }

        return sum;
    }
}
