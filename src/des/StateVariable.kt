package des

/**
 * The state managed by the simulation is a list of state variables.
 * Each must be identified with a unique ID. At this point there is
 * no support for structured states, aka trees or something. Structure
 * must be emulated via the instanceID.
 */
abstract class StateVariable {
    abstract fun instanceID() : String
}

/**
 * In many cases, a particular Kotlin class that represents a state
 * will only exist in one instance. In this case we use the qualified
 * name of the class as the instance ID. There are other places
 * where we use this for convenience, I will point it out in the
 * comments.
 */
abstract class SingleInstanceStateVariable : StateVariable() {
    override fun instanceID() = this::class.qualifiedName!!
}

/**
 * A bunch of interfaces for state values of particular types
 * which have convenience support in the framework. Again, I
 * will point out the convenience aspects in the respective
 * places.
 */
interface IntState {
    fun value(): Int
}

interface BooleanState {
    fun value(): Boolean
}