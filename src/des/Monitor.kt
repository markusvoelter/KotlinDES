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

class IntUpThreshMonitor(val pick : (StateSnapshot) -> IntState, val thresh: Int, val exec: (Simulation) -> Unit): AbstractMonitor() {
    override fun testAndRun(sim: Simulation, nowState: StateSnapshot, prevState: StateSnapshot) {
        val curr = pick(nowState).value()
        val prev = pick(prevState).value()
        if (curr >= thresh && prev < thresh) {
            exec(sim)
        }
    }
}

