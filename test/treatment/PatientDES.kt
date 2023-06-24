package treatment

import des.IntIncreaseTo
import des.Simulation
import des.Time

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
                sim.updateState(FeverPresent(true))
                sim.enqueueRelative(CheckFever(), 10, 20, 30)
            },
        )
    )

    // initial state of the simulation
    sim.setupState(PatientTemperature(37), FeverPresent(false))

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

