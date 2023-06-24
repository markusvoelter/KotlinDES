package de.voelter.des.util

/**
 * I use this for the queue. Not the most efficient implementation because
 * it resorts the whole list whenever you add something to it. maybe
 * some red/black tree or something would be better.
 */
class SortedArrayList<T>(val comparator: Comparator<T>): ArrayList<T>() {

    override fun add(element: T): Boolean {
        val ret = super.add(element)
        sortWith(comparator)
        return ret
    }
}