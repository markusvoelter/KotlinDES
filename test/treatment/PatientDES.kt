package treatment

import des.SimpleStateUpdateEvent
import des.Simulation
import des.Time

fun main() {
    val sim = Simulation()
    sim.enqueue(Time(0), SimpleStateUpdateEvent(PatientTemperature(37)))
    sim.enqueue(Time(100), SimpleStateUpdateEvent(PatientTemperature(38)))
    sim.runTo(Time(1000))
    sim.stateSnapshot(Time(0)).print()
    sim.stateSnapshot(Time(100)).print()
    sim.stateSnapshot(Time(200)).print()
}

