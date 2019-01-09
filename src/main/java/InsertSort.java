public class InsertSort {

    public static void main(String[] args) {
        Integer[] items = {3, 6, 8, 9, 2, 4, 5, 7, 1, 2};
        insertSort(items);
        Tools.print(items);
    }


    /**
     * 插入排序
     *
     * @param items
     * @param <AnyType>
     */
    public static <AnyType extends Comparable<AnyType>> void insertSort(AnyType[] items) {
        AnyType pivot = null;
        int pIndex = 0;
        for (int index = 0; index < items.length; index++) {
            pivot = items[index];
            for (pIndex = index; pIndex > 0; pIndex--) {
                if (pivot.compareTo(items[pIndex]) < 0) {
                    items[pIndex + 1] = items[pIndex];
                } else {
                    items[pIndex] = pivot;
                    break;
                }
            }
        }
    }
}
