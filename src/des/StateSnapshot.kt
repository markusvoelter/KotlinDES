package des

class StateSnapshot(val time: Time) {

    private val variables = HashMap<String, StateVariable>()

    internal fun register(state: StateVariable) {
        variables.put(state.instanceID(), state)
    }

    fun get(instanceID: String) = variables.get(instanceID)

    fun print() {
        System.err.println("Snapshot for " + time.value)
        for (k in variables.keys) {
            System.err.println("  " + k + " -> " + variables.get(k))
        }
        System.err.println("")
    }

}