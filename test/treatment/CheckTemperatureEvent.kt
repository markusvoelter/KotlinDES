package treatment

import des.AbstractEvent
import des.Simulation
import des.State

class CheckFever : AbstractEvent() {
    override fun run(sim: Simulation, state: State) {
        val temp = sim.stateSnapshot().get(PatientTemperature::class)
        if (temp.temp < 38) {
            sim.updateState(FeverPresent(false));
        }
    }
}