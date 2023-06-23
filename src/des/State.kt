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

}

data class StateUpdate(val time: Time, val stateVar: StateVariable)

class StateUpdateComparator : Comparator<StateUpdate> {
    override fun compare(e1: StateUpdate, e2: StateUpdate): Int {
        if (e1.time.value < e2.time.value) return -1
        if (e1.time.value > e2.time.value) return 1
        return 0
    }
}