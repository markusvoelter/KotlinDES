package des

import util.SortedArrayList

class Simulation {

    private val queue = SortedArrayList<EventInstance>(EventInstanceComparator())
    private val state = State()
    private val monitors = ArrayList<AbstractMonitor>()

    var now = Time(0)

    fun init(vararg stateVars : StateVariable) {
        for (sv in stateVars) {
            state.update(Time(0), sv)
        }
    }

    fun updateState(sv: StateVariable) {
        state.update(now, sv)
    }

    fun enqueue(time: Time, evt: AbstractEvent) {
        queue.add(EventInstance(time, evt))
    }

    fun registerMonitor(mon: AbstractMonitor) {
        monitors.add(mon)
    }

    fun runTo(time: Time) {
        while (!queue.isEmpty() && now <= time) {
            val nextEventInstance = queue.removeAt(0)
            now = nextEventInstance.time
            nextEventInstance.event.run(this, this.state)
            for (m in monitors) {
                m.testAndRun(this, stateSnapshot(now), stateSnapshot(now.immediatelyBefore()))
            }
        }
    }

    fun stateSnapshot() = state.snapshot(now)

    fun stateSnapshot(time: Time) = state.snapshot(time)

    fun state() = state
}