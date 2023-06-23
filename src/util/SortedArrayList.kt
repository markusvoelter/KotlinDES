package util

class SortedArrayList<T>(val comparator: Comparator<T>): ArrayList<T>() {

    override fun add(element: T): Boolean {
        val ret = super.add(element)
        sortWith(comparator)
        return ret
    }
}