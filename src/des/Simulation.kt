package des

import util.SortedArrayList

class Simulation {

    private val queue = SortedArrayList<EventInstance>(EventInstanceComparator())
    private val state = State()

    var now = Time(0)

    fun enqueue(time: Time, evt: AbstractEvent) {
        queue.add(EventInstance(time, evt))
    }

    fun runTo(time: Time) {
        while (!queue.isEmpty() && now <= time) {
            val nextEventInstance = queue.removeAt(0)
            now = nextEventInstance.time
            nextEventInstance.event.run(this, this.state)
        }
    }

    fun stateSnapshot() = state.snapshot(now)

    fun stateSnapshot(time: Time) = state.snapshot(time)

}