package des

data class Time(val value: Int) : Comparable<Time>{

    override fun compareTo(other: Time): Int {
        return when {
            this.value > other.value -> 1
            this.value == other.value -> 0
            else -> -1
        }
    }

    fun immediatelyBefore() : Time {
        if (value == 0) return this
        return Time(value - 1)
    }

    fun plus(delta: Int) = Time(this.value + delta)


}

