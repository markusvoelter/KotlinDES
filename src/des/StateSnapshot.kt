package des

import kotlin.reflect.KClass

class StateSnapshot(val time: Time) {

    private val variables = HashMap<String, StateVariable>()

    internal fun register(state: StateVariable) {
        variables.put(state.instanceID(), state)
    }

    fun get(instanceID: String) = variables.get(instanceID)

    fun <T : Any> get(cls: KClass<T>) = variables.get(cls.qualifiedName) as T

    fun print() {
        System.err.println("Snapshot for " + time.clock)
        for (k in variables.keys) {
            System.err.println("  " + k + " -> " + variables.get(k))
        }
        System.err.println("")
    }

}