package de.voelter.des.examples.treatment

import de.voelter.des.fw.AbstractEvent
import de.voelter.des.fw.IntIncreaseTo
import de.voelter.des.fw.Simulation
import de.voelter.des.fw.SingleInstanceBoolState
import de.voelter.des.fw.SingleInstanceIntState
import de.voelter.des.fw.Time
import de.voelter.des.fw.UserSimulation

/**
 * A state to track the patient's temperature. It's both a single instance
 * state variable (i.e., identified by class name) and an IntState; we have
 * to implement the value
 */
data class PatientTemperature(val temp: Int) : SingleInstanceIntState(temp)

/**
 * Demonstrate derived values
 */
data class PatientTemperatureTimesTwo(val temp: Int) : SingleInstanceIntState(temp)

/**
 * Another one that tracks whether a fever has been detected
 */
data class PatientFever(val detected: Boolean) : SingleInstanceBoolState(detected)

/**
 * An event whose implementation checks whether the fever
 * has subsided and then updates the Fever state
 */
class CheckNoMoreFever : AbstractEvent() {
    override fun run(sim: Simulation) {
        val temp = sim.stateSnapshot().getInt(PatientTemperature::class)
        if (temp < 38) {
            sim.updateState(PatientFever(false))
        }
    }
}

class PatientFeverSimulation : UserSimulation() {

    fun main(args: Array<String>) {
        run()
    }

    override fun run(): Simulation {
        // create the simulation
        val simulation = Simulation()

        // register a monitor that updates the FeverPresent
        // state if the temperature reaches 38 degrees
        simulation.registerMonitor(
            IntIncreaseTo(
                { it.get(PatientTemperature::class) },
                38,
                { sim ->
                    sim.updateState(PatientFever(true))
                    sim.enqueueRelative(CheckNoMoreFever(), 10, 20, 30)
                }
            )
        )

        /**
         * register a kind of monitor that always fires and computes
         * derived values; here, PatientTemperatureTimesTwo
         */
        simulation.registerDeriver { sim ->
            val temp = sim.stateSnapshot().getInt(PatientTemperature::class)
            sim.updateState(PatientTemperatureTimesTwo(2 * temp))
        }

        // initial state of the simulation
        simulation.setupState(PatientTemperature(37), PatientFever(false))

        // "scripted" behavior
        simulation.updateState(PatientTemperature(38), Time(100))
        simulation.updateState(PatientTemperature(37), Time(120))

        // run the thing
        simulation.run()

        return simulation
    }
}
