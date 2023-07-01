package de.voelter.des.util

/**
 * I use this for the queue. Not the most efficient implementation because
 * it resorts the whole list whenever you add something to it. maybe
 * some red/black tree or something would be better.
 */
class SortedArrayList<T>(val comparator: Comparator<T>) : ArrayList<T>() {

    override fun add(element: T): Boolean {
        val index = binarySearch(element, comparator).let { if (it < 0) -(it + 1) else it }
        super.add(index, element)
        return true
    }
}
