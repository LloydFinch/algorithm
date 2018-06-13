/**
 * java 工具类
 */
public class Tools {

    /**
     * 交换元素
     */
    public static <AnyType> void swap(AnyType[] items, int index1, int index2) {
        if (items != null && items.length >= 2 && index1 >= 0 && index2 >= 0) {
            AnyType temp = items[index1];
            items[index1] = items[index2];
            items[index2] = temp;
        }
    }

    /**
     * 打印数组
     */
    public static <AnyType> void print(AnyType[] items) {
        for (AnyType e : items) {
            System.out.print(e + ", ");
        }
    }
}
