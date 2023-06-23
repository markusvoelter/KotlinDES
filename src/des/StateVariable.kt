package des

abstract class StateVariable {
    abstract fun instanceID() : String
}

abstract class SingleInstanceStateVariable : StateVariable() {
    override fun instanceID() = this::class.qualifiedName!!
}

interface IntState {
    fun value(): Int
}