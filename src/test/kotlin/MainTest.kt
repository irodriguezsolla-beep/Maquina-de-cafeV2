package org.example

import org.example.CoffeeMachineState.*
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MainTest {

    @BeforeTest
    fun resetMachineBeforeTest() {
        resetMachine()
    }

    @AfterTest
    fun resetMachineAfterTest() {
        resetMachine()
    }

    // 1. Probar Estado E1
    @Test
    fun testEstadoE1_TransicionAE2() {
        CoffeeMachine.currentState = E1_Idle

        val siguienteEstado = CoffeeMachine.gestorEstados()

        assertEquals(E2_SeleccionBebida, siguienteEstado)
    }

    // 2. Probar Estado E2
    @Test
    fun testEstadoE2_TransicionAE4_ConStock() {
        CoffeeMachine.currentState = E2_SeleccionBebida
        CoffeeMachine.bebida = Bebida.CAFE_SOLO

        val siguienteEstado = CoffeeMachine.gestorEstados()

        assertEquals(E4_SeleccionAzucar, siguienteEstado)
    }

    // 3. Probar Estado E3
    @Test
    fun testEstadoE3_TransicionAE1() {
        CoffeeMachine.currentState = E3_BebidaNoDisponible

        val siguienteEstado = CoffeeMachine.gestorEstados()

        assertEquals(E1_Idle, siguienteEstado)
    }

    // 4. Probar Estado E4
    @Test
    fun testEstadoE4_TransicionAE5_SinAzucar() {
        CoffeeMachine.currentState = E4_SeleccionAzucar
        CoffeeMachine.quiereAzucar = false

        val siguienteEstado = CoffeeMachine.gestorEstados()

        assertEquals(E5_PreparacionSinAzucar, siguienteEstado)
    }

    // 5. Probar Estado E5
    @Test
    fun testEstadoE5_TransicionAE9() {
        CoffeeMachine.bebida = Bebida.CAFE_SOLO
        CoffeeMachine.currentState = E5_PreparacionSinAzucar

        val siguienteEstado = CoffeeMachine.gestorEstados()

        assertEquals(E9_RecogidaCafe(Bebida.CAFE_SOLO), siguienteEstado)
    }

    // 6. Probar Estado E6
    @Test
    fun testEstadoE6_TransicionAE8_ConStockAzucar() {
        CoffeeMachine.currentState = E6_SeleccionCantidadAzucar
        CoffeeMachine.stockAzucar = 5
        CoffeeMachine.cantidadAzucarDeseada = 2

        val siguienteEstado = CoffeeMachine.gestorEstados()

        assertEquals(E8_PreparacionConAzucar, siguienteEstado)
    }

    // 7. Probar Estado E7
    @Test
    fun testEstadoE7_TransicionAE4() {
        CoffeeMachine.currentState = E7_AzucarNoDisponible

        val siguienteEstado = CoffeeMachine.gestorEstados()

        assertEquals(E4_SeleccionAzucar, siguienteEstado)
    }

    // 8. Probar Estado E8
    @Test
    fun testEstadoE8_TransicionAE9() {
        CoffeeMachine.bebida = Bebida.CAFE_SOLO
        CoffeeMachine.currentState = E8_PreparacionConAzucar

        val siguienteEstado = CoffeeMachine.gestorEstados()

        assertEquals(E9_RecogidaCafe(Bebida.CAFE_SOLO), siguienteEstado)
    }

    // 9. Probar Estado E9
    @Test
    fun testEstadoE9_TransicionAE1() {
        CoffeeMachine.currentState = E9_RecogidaCafe(Bebida.CAFE_SOLO)

        val siguienteEstado = CoffeeMachine.gestorEstados()

        assertEquals(E1_Idle, siguienteEstado)
    }

    private fun resetMachine() {
        // Restablecer stock del enum Bebida
        Bebida.CAFE_SOLO.cantidad = 5
        Bebida.CAFE_CON_LECHE.cantidad = 8
        Bebida.NO_CAFE.cantidad = 0

        // Restablecer valores de la máquina
        CoffeeMachine.bebida = null
        CoffeeMachine.quiereAzucar = true
        CoffeeMachine.stockAzucar = 5
        CoffeeMachine.cantidadAzucarDeseada = 2
        CoffeeMachine.currentState = E1_Idle
    }
}