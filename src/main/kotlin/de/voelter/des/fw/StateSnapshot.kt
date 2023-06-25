package de.voelter.des.fw

import kotlin.reflect.KClass
import kotlin.reflect.cast

/**
 * A snapshot of the state history for a given point in time.
 * It's basically a Map from instanceID -> StateVariable
 */
class StateSnapshot(val time: Time) {

    /**
     * The Map to store the data
     */
    private val variables = HashMap<String, StateVariable>()

    /**
     * registers a StateVariable by ID. This function is called
     * by State in order of occurrence fo the StateUpdates, then
     * this ends up with the latest update (per instanceID) last.
     */
    internal fun register(state: StateVariable) {
        variables[state.instanceID()] = state
    }

    /**
     * returns the state value for a given instanceID
     */
    operator fun get(instanceID: String) = variables[instanceID]

    /**
     * Convenience method to grab integer state's values directly
     */
    fun getInt(instanceID: String): Int {
        val s = variables[instanceID]
        if (s is IntState) {
            return s.value()
        }
        throw RuntimeException("$instanceID is not an IntState")
    }

    /**
     * directly returns the integer value
     */
    fun <T> getInt(cls: KClass<T>): Int where T : IntState, T : SingleInstanceStateVariable {
        val s = variables[cls.qualifiedName]
        return (s as IntState).value()
    }

    /**
     * Convenience method to grab boolean state's values directly
     */
    fun getBool(instanceID: String): Boolean {
        val s = variables[instanceID]
        if (s is BooleanState) {
            return s.value()
        }
        throw RuntimeException("$instanceID is not an BooleanState")
    }

    /**
     * directly returns the boolean value
     */
    fun <T> getBool(cls: KClass<T>): Boolean where T : BooleanState, T : SingleInstanceStateVariable {
        val s = variables[cls.qualifiedName]
        return (s as BooleanState).value()
    }

    /**
     * For single instance state variables where the class is the instanceID,
     * we can pass in the class for the lookup (instead of its ID aka qualified name)
     * This way we can use the class to cast the result, making value access simpler
     * for the client
     */
    fun <T : SingleInstanceStateVariable> get(cls: KClass<T>) = cls.cast(variables[cls.qualifiedName])

    /**
     * Debug support
     */
    fun print() {
        System.err.println("Snapshot for ${time.clock}")
        for (k in variables.keys) {
            System.err.println("  $k -> ${variables[k]}")
        }
        System.err.println("")
    }
}
