package de.voelter.des.testsupport

import de.voelter.des.fw.Simulation
import de.voelter.des.fw.StateVariable
import de.voelter.des.fw.Time

/**
 * Experimental
 */
class DESTest(val sim: Simulation) {
    val initials = ArrayList<StateVariable>()
    var steps = TestStepContainer()

    fun setup(vararg initialStates: StateVariable) {
        initials += initialStates
    }

    fun steps(init: TestStepContainer.() -> Unit): TestStepContainer {
        steps = TestStepContainer()
        steps.init()
        return steps
    }

    fun buildSimulation(): Simulation {
        for (initial in initials) {
            sim.setupState(initial)
        }
        for (modify in steps.steps.filterIsInstance<ExplicitStateUpdate>()) {
            sim.updateState(modify.state, modify.time)
        }
        return sim
    }

    fun buildAndRunSimulation(): Simulation {
        val sim = buildSimulation()
        sim.run()
        return sim
    }
}

class TestStepContainer {
    val steps = ArrayList<TestStep>()
    infix fun Int.modify(state: StateVariable) {
        steps += ExplicitStateUpdate(Time(this.toLong()), state)
    }
}

abstract class TestStep
class ExplicitStateUpdate(val time: Time, val state: StateVariable) : TestStep()

fun test(sim: Simulation, init: DESTest.() -> Unit): DESTest {
    return DESTest(sim).apply { init() }
}
