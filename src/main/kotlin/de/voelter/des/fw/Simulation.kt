package de.voelter.des.fw

import de.voelter.des.util.SortedArrayList

/**
 * This is the main class for the DES Simulation. Details
 * are explained for the members.
 */
class Simulation {

    /**
     * The queue for the events. Note how it uses the EventInstanceComparator to enforce sorting.
     */
    private val eventQueue = SortedArrayList(EventInstanceComparator())

    /**
     * The state/history maintained by the system; see State class for details.
     */
    private val state = State()

    /** the list of registered monitors */
    private val monitors = ArrayList<AbstractMonitor>()

    /** Current sim time */
    var now = Time(0)

    /**
     * This can be called at the beginning of the simulation to set up
     * initial values for the state variables
     */
    fun setupState(vararg stateVars: StateVariable) {
        stateVars.forEach { state.update(Time(0), it) }
    }

    /**
     * Grabs the latest value for a particular state. Currently, this
     * builds a whole snapshot for "now" and then grabs the respective
     * state. This could be done much more efficiently by looking
     * at the history backwards for a change of the instanceID
     */
    fun latest(instanceID: String) = stateSnapshot()[instanceID]

    /**
     * Updates a particular state immediately (for "now"). Note that
     * if the supplied value is equal to the current/latest one, no
     * state update will be registered.
     */
    fun updateState(sv: StateVariable) {
        val curr = latest(sv.instanceID())
        if (sv == curr) return
        state.update(now, sv)
    }

    /**
     * Same as above, but the state update is scheduled for
     * a time in the future. To this end, we enqueue an
     * EventInstance for that future time at which the
     * event effects that state change.
     */
    fun updateState(evt: StateVariable, vararg times: Time) {
        times.forEach { eventQueue.add(EventOccurrence(it, SimpleStateUpdateEvent(evt))) }
    }

    /**
     * puts the event into the queue at the given list
     * of future times. Creates a new event occurrence for
     * each of the times.
     */
    fun enqueue(evt: AbstractEvent, vararg times: Time) {
        times.forEach { eventQueue.add(EventOccurrence(it, evt)) }
    }

    /**
     * Another convenience function where you supply the
     * times as offsets relative to now.
     */
    fun enqueueRelative(evt: AbstractEvent, vararg deltas: Int) {
        deltas.forEach { enqueue(evt, now + it) }
    }

    /**
     * register a new monitor with the simulation
     */
    fun registerMonitor(mon: AbstractMonitor): Simulation {
        monitors.add(mon)
        return this
    }

    /**
     * A deriver is a monitor with no condition that always
     * fires and updates the state somehow, typically with
     * "derived", calculated values
     */
    fun registerDeriver(exec: SimulationExecutor): Simulation {
        monitors.add(AlwaysTrueMonitor(exec))
        return this
    }

    /**
     * Runs the simulation until the queue is empty or the supplied
     * time has been reached.
     */
    fun runTo(stopTime: Time) {
        // as I said: run until queue is empty or stopTime has been reached
        while (!eventQueue.isEmpty() && now <= stopTime) {
            // grab next event from the queue; remember it is sorted by (dense) time, increasing
            val nextEventInstance = eventQueue.removeAt(0)
            // advance the simulation time to the time of the event,
            // because this is the "new now"
            now = nextEventInstance.time
            // tun the event
            nextEventInstance.event.run(this)
            // give all monitors a chance to react
            monitors.forEach {
                it.testAndRun(this, stateSnapshot(now), stateSnapshot(now.immediatelyBefore()))
            }
        }
    }

    /**
     * convenience method that runs the queue to completion, no
     * time limit given
     */
    fun run() {
        runTo(Time(Long.MAX_VALUE))
    }

    /**
     * Create a snapshot for "now"
     */
    fun stateSnapshot() = stateSnapshot(now)

    /**
     * Create a snapshot for the given time; implementation
     * delegates to State class
     */
    fun stateSnapshot(time: Time) = state.snapshot(time)

    /**
     * Access to the state of the simulation
     */
    fun state() = state
}
