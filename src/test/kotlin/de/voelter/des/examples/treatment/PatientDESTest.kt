package de.voelter.des.examples.treatment

import de.voelter.des.fw.Time
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class PatientDESTest {

    @org.junit.jupiter.api.Test
    fun justPrintDebugStuff() {
        val sim = PatientFeverSimulation().run()
        sim.stateSnapshot(Time(0)).print()
        sim.stateSnapshot(Time(100)).print()
        sim.stateSnapshot(Time(200)).print()
        sim.state().printHistory()
    }

    @org.junit.jupiter.api.Test
    fun checkEndState() {
        val sim = PatientFeverSimulation().run()
        val snapshot = sim.stateSnapshot()
        assertFalse(snapshot.getBool(PatientFever::class))
        assertEquals(37, snapshot.getInt(PatientTemperature::class))
    }
}