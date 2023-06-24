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

    fun latest(instanceID: String) = stateSnapshot().get(instanceID)

    fun updateState(sv: StateVariable) {
        val curr = latest(sv.instanceID())
        if (sv.equals(curr)) return
        state.update(now, sv)
    }

    fun updateState(evt: StateVariable, vararg times: Time) {
        for (t in times) {
            queue.add(EventInstance(t, SimpleStateUpdateEvent(evt)))
        }
    }

    fun enqueue(evt: AbstractEvent, vararg times: Time) {
        for (t in times) {
            queue.add(EventInstance(t, evt))
        }
    }

    fun enqueueRelative(evt: AbstractEvent, vararg deltas: Int) {
        for (d in deltas) {
            enqueue(evt, now.plus(d))
        }
    }


    fun registerMonitor(mon: AbstractMonitor) {
        monitors.add(mon)
    }

    fun runTo(time: Time) {
        while (!queue.isEmpty() && now <= time) {
            val nextEventInstance = queue.removeAt(0)
            now = nextEventInstance.time
            nextEventInstance.event.run(this)
            for (m in monitors) {
                m.testAndRun(this, stateSnapshot(now), stateSnapshot(now.immediatelyBefore()))
            }
        }
    }

    fun stateSnapshot() = state.snapshot(now)

    fun stateSnapshot(time: Time) = state.snapshot(time)

    fun state() = state
}