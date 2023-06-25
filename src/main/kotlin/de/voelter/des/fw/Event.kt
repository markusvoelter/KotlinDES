package de.voelter.des.fw

/**
 * Events are the contents of the queue. When the event is
 * executed, it gets access to the whole simulation environment.
 */
abstract class AbstractEvent {
    abstract fun run(sim: Simulation)
}

/**
 * An event type whose only effect is to change the value of a
 * state variable. The user supplies the code to produce that
 * State variable. There could be another one that produces a list
 * of state variables instead of just one.
 */
class StateUpdateEvent(val code: (State) -> StateVariable) : AbstractEvent() {
    override fun run(sim: Simulation) {
        val stateVar = code(sim.state())
        sim.state().update(sim.now, stateVar)
    }
}

/**
 * Yet another simplification. Here we don't supply code that produces
 * a state variable for immediate update, but we directly pass the state
 * variable.
 */
class SimpleStateUpdateEvent(val stateVar: StateVariable) : AbstractEvent() {
    override fun run(sim: Simulation) {
        sim.state().update(sim.now, stateVar)
    }
}

/**
 * Helper class for the queue. We don't directly store the events in the
 * queue, but wrapped with an EventOccurrence; this instance also captures
 * the time at which the event is supposed to be executed.
 */
data class EventOccurrence(val time: Time, val event: AbstractEvent)

/**
 * And here is the comparator for the event instances, to make sure
 * that our sorted list sorts them by time
 */
class EventInstanceComparator : Comparator<EventOccurrence> by compareBy({ it.time })
