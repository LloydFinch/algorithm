public class QuickSort {

    public static void main(String[] args) {

        int[] datas = new int[]{4, 9, 8, 6, 2, 7, 0, 3, 1, 5};
        quickSort(datas, 0, datas.length - 1);

        printArray(datas);
    }

    /**
     * 快速排序，有bug
     * 需要优化一波
     * @param data
     * @param left
     * @param right
     */
    public static void quickSort(int[] data, int left, int right) {

        if (left >= right) {
            return;
        }

        int pivot = data[left]; //设置枢纽元
        int leftGuard = left + 1; //设置左哨兵
        int rightGuard = right; //设置右哨兵

        System.out.println("=================新一轮检测: 左右哨兵为:" + leftGuard + ", " + rightGuard);
        //只要哨兵没有相遇，就一直检测
        while (leftGuard < rightGuard) {
            //从右到左，找第一个小于pivot的元素
            for (; ; ) {
                if (rightGuard > left) {
                    if (data[rightGuard] > pivot) {

                    } else {
                        //说明找到了
                        break;
                    }
                } else {
                    //说明找到头了
                    break;
                }
                rightGuard--; //依次向前寻找
            }

            //从左到右，找第一个大于pivot的元素
            for (; ; ) {
                if (leftGuard < right) {
                    if (data[leftGuard] < pivot) {

                    } else {
                        //说明找到了
                        break;
                    }
                } else {
                    //说明找到尾了
                    break;
                }
                leftGuard++;
            }

            if (leftGuard < rightGuard) {
                //交换两个哨兵对应的元素
                swap(data, leftGuard, rightGuard);
            } else {
                //哨兵相遇了
                swap(data, left, rightGuard);
                System.out.println("==================一轮检测结束==============");
                System.out.println("==================开始排左边" + left + ", " + right + ", " + leftGuard + ", " + rightGuard);
                printArray(data);
                quickSort(data, left, rightGuard);

                System.out.println("==================开始排右边" + left + ", " + right + ", " + leftGuard + ", " + rightGuard);
                printArray(data);
                quickSort(data, rightGuard + 1, right);
            }
        }
    }


    private static void swap(int[] data, int i, int j) {
        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    private static void printArray(int[] datas) {
        for (int i = 0; i < datas.length; i++) {
            System.out.print(datas[i] + " ");
        }
        System.out.println();
    }

}
