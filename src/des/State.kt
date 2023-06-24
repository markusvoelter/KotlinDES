package des

import util.SortedArrayList

class State {

    private val history = SortedArrayList<StateUpdate>(StateUpdateComparator())

    fun update(time: Time, stateVar: StateVariable) {
        history.add(StateUpdate(time, stateVar))
    }

    fun snapshot(time: Time): StateSnapshot {
        val snapshot = StateSnapshot(time)
        for (stateUpdate in history) {
            if (stateUpdate.time > time) break
            snapshot.register(stateUpdate.stateVar)
        }
        history.iterator()
        return snapshot
    }

    fun printHistory() {
        System.err.println("History")
        for (su in history) {
            System.err.println("  " + su.time.clock+" : " + su.stateVar)
        }
    }

}

data class StateUpdate(val time: Time, val stateVar: StateVariable)

class StateUpdateComparator : Comparator<StateUpdate> {
    override fun compare(e1: StateUpdate, e2: StateUpdate): Int {
        if (e1.time.clock < e2.time.clock) return -1
        if (e1.time.clock > e2.time.clock) return 1
        return 0
    }
}