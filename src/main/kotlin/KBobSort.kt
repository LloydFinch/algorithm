fun main(args: Array<String>) {

    val items: Array<Int> = Array(5, { index -> index + 1 })
    bobSort(items)
    KTools.print(items)
}

fun <AnyType : Comparable<AnyType>> bobSort(items: Array<AnyType>) {
    for (len in 0 until (items.size - 1)) {
        items.indices.forEach {
            if (it in 0 until items.size - 1) {
                if (items[it] > items[it + 1]) {
                    KTools.swap(items, it, it + 1)
                }
            }
        }
    }
}