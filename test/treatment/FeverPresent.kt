package treatment

import des.BooleanState
import des.SingleInstanceStateVariable

data class FeverPresent(val present: Boolean) : SingleInstanceStateVariable(), BooleanState {
    override fun value() = present
}
