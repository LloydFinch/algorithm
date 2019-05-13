//import java.util.*
//import kotlin.math.sign
//
//fun main(args: Array<String>) {
//
//    val items: Array<Int> = Array(10, { index -> Random().nextInt(100) })
//    println("before insertSort...")
//    KTools.print(items)
//    kInsertSort(items)
//    println("after insertSort...")
//    KTools.print(items)
//}
//
///**
// * 插入排序
// * 从前往后找第一个比pivot大的/
// * 从后往前找第一个比pivot小的
// */
//fun <AnyType : Comparable<AnyType>> kInsertSort(items: Array<AnyType>) {
//    var pivot: AnyType //枢纽元，每轮要比较的元素
//    var pIndex: Int = 0
//    for (index in 1 until items.size) {
//        pivot = items[index]
//        for (sIndex in index downTo 0) {
//            pIndex = sIndex
//            //有多余操作
//            if (items[sIndex] > pivot) {
//                //大于枢纽元，就向后移动
//                items[sIndex + 1] = items[sIndex]
//            } else {
//                break
//            }
//        }
//        //一轮插入完成，枢纽元插入对应位置
//        items[pIndex] = pivot
//    }
//
//}