package treatment

import des.IntState
import des.SingleInstanceStateVariable

data class PatientTemperature(val temp: Int) : SingleInstanceStateVariable(), IntState {
    override fun value() = temp
}
