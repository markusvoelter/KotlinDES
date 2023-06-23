package treatment

import des.Monitor
import des.Simulation
import des.Time

fun main() {
    val sim = Simulation()
    sim.init(PatientTemperature(37), FeverPresent(false))
    sim.registerMonitor(Monitor(
        {prev, now ->
            val tNow = now.get(PatientTemperature::class)
            val tPrev = prev.get(PatientTemperature::class)
            tNow.temp >= 38 && tPrev.temp < 38
        },
        {sim ->
            sim.updateState(FeverPresent(true))
            sim.enqueue(CheckFever(), sim.now.plus(10), sim.now.plus(20), sim.now.plus(30))
        },
    ))
    sim.updateState(PatientTemperature(38), Time(100))
    sim.updateState(PatientTemperature(37), Time(120))
    sim.runTo(Time(1000))
    sim.stateSnapshot(Time(0)).print()
    sim.stateSnapshot(Time(100)).print()
    sim.stateSnapshot(Time(200)).print()
}

