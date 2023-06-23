package des

abstract class AbstractMonitor() {
    abstract fun testAndRun(sim: Simulation, nowState: StateSnapshot, prevState: StateSnapshot)
}

class Monitor(val cond : (StateSnapshot, StateSnapshot) -> Boolean, val exec: (Simulation) -> Unit): AbstractMonitor() {
    override fun testAndRun(sim: Simulation, nowState: StateSnapshot, prevState: StateSnapshot) {
        if (cond(prevState, nowState)) {
            exec(sim)
        }
    }
}

