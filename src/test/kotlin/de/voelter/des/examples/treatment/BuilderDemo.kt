package de.voelter.des.examples.treatment

import de.voelter.des.fw.IntIncreaseTo
import de.voelter.des.fw.Simulation
import de.voelter.des.testsupport.test

val sim = Simulation().registerMonitor(
    IntIncreaseTo(
        { it.get(PatientTemperature::class) },
        38,
        { sim ->
            sim.updateState(PatientFever(true))
            sim.enqueueRelative(CheckNoMoreFever(), 10, 20, 30)
        }
    )
)

val t = test(sim) {
    setup(
        PatientTemperature(37),
        PatientFever(false)
    )
    steps {
        100 modify PatientTemperature(39)
        110 modify PatientTemperature(38)
        115 modify PatientTemperature(38)
        120 modify PatientTemperature(37)
    }
}

fun main() {
    val sim = t.buildAndRunSimulation()
    sim.state().printHistory()
}
