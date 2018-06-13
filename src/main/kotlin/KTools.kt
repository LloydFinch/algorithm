/**
 * kotlin 工具类
 */
class KTools {

    companion object {

        /**
         * 交换元素
         */
        fun <AnyType : Comparable<AnyType>> swap(items: Array<AnyType>, index1: Int, index2: Int) {
            if (items.size >= 2 && index1 >= 0 && index2 >= 0) {
                val temp = items[index1]
                items[index1] = items[index2]
                items[index2] = temp
            }
        }

        /**
         * 打印数组
         */
        fun <AnyType : Comparable<AnyType>> print(items: Array<AnyType>) {
            items.forEach {
                print("$it, ")
            }
        }
    }
}