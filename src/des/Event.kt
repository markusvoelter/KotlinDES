package des

abstract class AbstractEvent {
    abstract fun run(sim: Simulation, state: State)
}

class StateUpdateEvent(val code : (State) -> StateVariable) : AbstractEvent() {
    override fun run(sim: Simulation, state: State) {
        val stateVar = code(state)
        state.update(sim.now, stateVar)
    }
}

class SimpleStateUpdateEvent(val stateVar : StateVariable) : AbstractEvent() {
    override fun run(sim: Simulation, state: State) {
        state.update(sim.now, stateVar)
    }
}

data class EventInstance(val time: Time, val event: AbstractEvent)

class EventInstanceComparator : Comparator<EventInstance> {
    override fun compare(e1: EventInstance, e2: EventInstance): Int {
        if (e1.time.value < e2.time.value) return -1
        if (e1.time.value > e2.time.value) return 1
        return 0
    }
}