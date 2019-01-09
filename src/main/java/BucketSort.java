public class BucketSort {

    public static void main(String[] args) {
        int range = 60;
        int[] data = {0, 33, 3, 3, 33, 55, 6, 2, 7, 8, 9};
        bucketSort(range, data);
    }


    /**
     * 桶排序
     *
     * @param range
     * @param data
     */
    public static void bucketSort(int range, int[] data) {

        if (data.length == 0) {
            return;
        }

        //这里做个排查处理，如果输入的range不正确，
        //就取data的最大值作为range //效率已经低了，应为找最大值做了遍历操作
        int partRange = findMaxValue(data);
        if (partRange < range) {
            partRange = range;
        }

        //根据范围初始化所有bucket
        int[] buckets = new int[partRange];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = 0;
        }

        //进行统计添加
        for (int i = 0; i < data.length; i++) {
            int index = data[i];
            buckets[index]++;
        }

        //打印结果
        for (int i = 0; i < buckets.length; i++) {
            //出现几次就打印几次
            for (int j = 0; j < buckets[i]; j++) {
                System.out.println(i);
            }
        }
    }

    //<editor-fold desc="找数组的最大值">
    public static int findMaxValue(int[] datas) {
        if (datas.length == 0) {
            return Integer.MIN_VALUE;
        }
        int max = datas[0];
        for (int i = 0; i < datas.length; i++) {
            max = Math.max(max, datas[i]);
        }

        return max;
    }
    //<editor-fold>
}
