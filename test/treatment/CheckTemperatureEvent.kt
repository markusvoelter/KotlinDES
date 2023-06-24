package treatment

import des.AbstractEvent
import des.Simulation

class CheckFever : AbstractEvent() {
    override fun run(sim: Simulation) {
        val temp = sim.stateSnapshot().get(PatientTemperature::class)
        if (temp.temp < 38) {
            sim.updateState(FeverPresent(false));
        }
    }
}