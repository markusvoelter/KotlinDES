package des

/**
 * Monitors can be registered with the Simulation engine and
 * are called for every state change. They can then test if
 * they want to be triggered, and if so, they are run; they
 * can then modify state and/or enqueue events. Since many
 * monitors will want to be triggered if a value changes in
 * a particular way, they get access to the current state
 * and the state right before now via two snapshots.
 */
abstract class AbstractMonitor() {
    abstract fun test(nowState: StateSnapshot, prevState: StateSnapshot) : Boolean
    abstract fun run(sim: Simulation) : Unit
    fun testAndRun(sim: Simulation, nowState: StateSnapshot, prevState: StateSnapshot) {
        if (test(nowState, prevState)){
            run(sim)
        }
    }
}

/**
 * This one is a convenience version where the test and the
 * to-be-executed code are supplied via functions.
 */
class Monitor(val cond : (StateSnapshot, StateSnapshot) -> Boolean, val exec: (Simulation) -> Unit): AbstractMonitor() {
    override fun test(nowState: StateSnapshot, prevState: StateSnapshot) = cond(prevState, nowState)
    override fun run(sim: Simulation) { exec(sim) }
}

/**
 * This one is a convenience for BooleanStates. The trigger fires if in the current
 * state the Boolean is true and was false in the previous state. So we have to look
 * at the same state variable in both states, which is why the user supplies one
 * pick function that grabs the BooleanState from a snapshot. The implementation
 * then applies that pick function to the now and current states.
 *
 * A BoolBecomesFalse monitor shoould be developed at some point as well.
 */
class BoolBecomesTrue(val pick : (StateSnapshot) -> BooleanState, val exec: (Simulation) -> Unit): AbstractMonitor() {
    override fun test(nowState: StateSnapshot, prevState: StateSnapshot): Boolean {
        val curr = pick(nowState).value()
        val prev = pick(prevState).value()
        return curr  && !prev
    }
    override fun run(sim: Simulation) { exec(sim) }
}


/**
 * This one is another convenience implementation for integers rising
 * to a particular threshold value (on an increasing path).
 */
class IntIncreaseTo(val pick : (StateSnapshot) -> IntState, val threshold: Int, val exec: (Simulation) -> Unit): AbstractMonitor() {
    override fun test(nowState: StateSnapshot, prevState: StateSnapshot): Boolean {
        val curr = pick(nowState).value()
        val prev = pick(prevState).value()
        return curr >= threshold && prev < threshold
    }
    override fun run(sim: Simulation) { exec(sim) }
}


