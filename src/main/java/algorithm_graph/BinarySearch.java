package algorithm_graph;

public class BinarySearch {


    public static void main(String[] args) {
        int length = 128;
        int arr[] = new int[length];
        for (int i = 0; i < length; i++) {
            arr[i] = i;
        }

        int position = binarySearch(arr, 63);
        System.out.println("position = " + position);
    }


    /**
     * 二分法查找
     *
     * @param arr 查找的源数组
     * @param src 查找的目标
     * @return src在arr中的位置，-1表示没找到
     */
    public static int binarySearch(int arr[], int src) {

        /**
         * 记录查找次数
         */
        int len = 0;

        if (arr.length == 0) {
            return -1;
        }

        int position = -1;
        int high = arr.length - 1;
        int low = 0;

        int mid;
        int guest;

        while (low <= high) {

            mid = (low + high) / 2;
            guest = arr[mid];

            len++;

            if (guest == src) {
                System.out.println("len = " + len);
                return mid;
            } else if (guest < src) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return position;
    }
}
