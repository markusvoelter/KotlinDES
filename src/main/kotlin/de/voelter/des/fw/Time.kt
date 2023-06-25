package de.voelter.des.fw

/**
 * Represents the time in the system. Time has two parts. The
 * wall-clock time represented as an integer (the one passed
 * in the constructor) plus a dense time to enforce ordering.
 * The dense time is globally incremented, not "per" clock
 * time. This is easier to implement and also does the job.
 */
data class Time(val clock: Long) : Comparable<Time> {

    /**
     * Maintains a "static" global counter. Whenever we create
     * a new Time object, the globalCounter is incremented
     * The denseTime (non-static) member contains this incrementing
     * ID.
     */
    companion object {
        var globalCounter = 0L
        fun newID() = globalCounter++
    }

    val dense : Long = newID()

    /**
     * This allows comparison with the native <, >, <= etc. operators.
     * Notice the two-step comparison. We first check the actual clock
     * value. Only if these are the same, then we resort to comparing
     * the dense time. This way, since newer events always have bigger
     * dense values, we ensure "insertion ordering" per clock time.
     */
    override fun compareTo(other: Time): Int {
        return when {
            this.clock > other.clock -> 1
            this.clock < other.clock -> -1
            else -> when {
                this.dense > other.dense -> 1
                this.dense < other.dense -> -1
                else -> 0
            }
        }
    }

    /**
     * Since we often want to compare something to the
     * time "just before", this one creates a new Time
     * one tick back.
     */
    fun immediatelyBefore() : Time {
        if (clock == 0L) return this
        return Time(clock - 1)
    }

    /**
     * Utility function to create a time offset
     * by the delta
     */
    operator fun plus(delta: Int) = Time(this.clock + delta)


}

