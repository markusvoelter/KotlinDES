package de.voelter.des.fw

import de.voelter.des.util.SortedArrayList

/**
 * This class captures the state of the simulation by recording
 * the state updates over time. StateSnapshots are taken to provide
 * the actual state at any particular time
 */
class State {

    /**
     * The actual list of StateUpdate objects that constitute the
     * history of the system.
     */
    private val history = SortedArrayList<StateUpdate>(StateUpdateComparator())

    /**
     * register a state update for a particular time
     */
    fun update(time: Time, stateVar: StateVariable) {
        history.add(StateUpdate(time, stateVar))
    }

    /**
     * produce a snapshot of the state; we run through history
     * until the time for which we want the snapshot. We
     * submit each update consecutively, newer updates later
     * so they can overwrite previous ones. The StateSnapshot
     * class is basically a map from instanceID to value.
     */
    fun snapshot(time: Time): StateSnapshot {
        val snapshot = StateSnapshot(time)
        for (stateUpdate in history) {
            if (stateUpdate.time > time) break
            snapshot.register(stateUpdate.stateVar)
        }
        history.iterator()
        return snapshot
    }

    /**
     * Debug support
     */
    fun printHistory() {
        System.err.println("History")
        for (su in history) {
            System.err.println("  " + su.time.clock+" : " + su.stateVar)
        }
    }

}

/**
 * This class contains the state variable value with a
 * time so it can be time-ordered in the history
 */
data class StateUpdate(val time: Time, val stateVar: StateVariable)

/**
 * Comparator to sort StateUpdates by time
 */
class StateUpdateComparator : Comparator<StateUpdate> {
    override fun compare(e1: StateUpdate, e2: StateUpdate): Int {
        if (e1.time < e2.time) return -1
        if (e1.time > e2.time) return 1
        return 0
    }
}