package de.voelter.des.examples.treatment

import de.voelter.des.fw.*

/**
 * A state to track the patient's temperature. It's both a single instance
 * state variable (ie., identified by class name) and an IntState; we have
 * to implement the value
 */
data class PatientTemperature(val temp: Int) : SingleInstanceIntState(temp)

/**
 * Another one that tracks whether a fever has been detected
 */
data class PatientFever(val detected: Boolean) : SingleInstanceBoolState(detected)

/**
 * An event whose implementation checks whether the fever
 * has subsided and then udpates the Fever state
 */
class CheckNoMoreFever : AbstractEvent() {
    override fun run(sim: Simulation) {
        val temp = sim.stateSnapshot().getInt(PatientTemperature::class)
        if (temp < 38) {
            sim.updateState(PatientFever(false))
        }
    }
}

fun main() {
    // create the simulation
    val sim = Simulation()

    // register a monitor that updates the FeverPresent
    // state if the temperature reaches 38 degrees
    sim.registerMonitor(
        IntIncreaseTo(
            {it.get(PatientTemperature::class)},
            38,
            {sim ->
                sim.updateState(PatientFever(true))
                sim.enqueueRelative(CheckNoMoreFever(), 10, 20, 30)
            },
        )
    )

    // initial state of the simulation
    sim.setupState(PatientTemperature(37), PatientFever(false))

    // "scripted" behavior
    sim.updateState(PatientTemperature(38), Time(100))
    sim.updateState(PatientTemperature(37), Time(120))

    // run the thing
    sim.run()

    // outputs for debugging
    sim.stateSnapshot(Time(0)).print()
    sim.stateSnapshot(Time(100)).print()
    sim.stateSnapshot(Time(200)).print()
    sim.state().printHistory()
}

