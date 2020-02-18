package algorithm_graph;

/**
 * java不支持数组删除元素，可以在一个数组中通过下标来执行
 */
public class SelectionSort {

    public static void main(String[] args) {
        int arr[] = {1, 2, 3, 4, 5, 7, 8};


        selectionSort(arr);

        System.out.println("arr=" + arr);
    }


    /**
     * 找出指定数组中的虽小元素，并返回下标
     *
     * @param arr 目标数组
     * @return 最小元素的下标
     */
    private static int findSmallSort(int arr[]) {

        if (arr.length == 0) {
            return -1;
        }

        int index = 0;
        int smallest = arr[0];

        for (int i = 0; i < arr.length; i++) {

            if (arr[i] < smallest) {
                smallest = arr[i];
                index = i;
            }
        }

        return index;
    }

    /**
     * 选择排序
     *
     * @param arr 需要排序的数组
     */
    private static void selectionSort(int arr[]) {
        int dest[] = new int[arr.length];

        for (int i = 0; i < dest.length; i++) {
            /**
             * 找到最小元素的下标
             */
            int smallIndex = findSmallSort(arr);

            /**
             * 将这个元素加到新数组中
             */
            dest[i] = arr[smallIndex];

            /**
             * 从老数组中移除这个元素
             */
            arr[smallIndex] = -1;
        }
    }
}
