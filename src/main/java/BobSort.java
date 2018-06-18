public class BobSort {

    public static void main(String[] args) {

        Integer[] items = {5, 4, 3, 1, 2, 9, 8, 6, 7};
        bobSort(items);
        Tools.print(items);
    }

    /**
     * 冒泡排序，比较对象为任意可比较类型
     *
     * @param <AnyType> AnyType that implements Comparable
     */
    public static <AnyType extends Comparable<AnyType>> void bobSort(AnyType[] items) {
        for (int i = 1; i < items.length; i++) {
            for (int j = 0; j < items.length - i; j++) {
                if (items[j].compareTo(items[j + 1]) > 0) {
                    Tools.swap(items, j, j + 1);
                }
            }
        }
    }
}
