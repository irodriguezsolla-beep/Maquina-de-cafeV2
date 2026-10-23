package org.example

import org.example.CoffeeMachineState.*
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

// Suite de pruebas unitarias para validar las transiciones de la máquina de estados `CoffeeMachine`.

class MainTest {

    //Se ejecuta ANTES de cada test para garantizar un punto de partida limpio.

    @BeforeTest
    fun resetMachineBeforeTest() {
        resetMachine()
    }

    //Se ejecuta DESPUÉS de cada test para asegurar la independencia entre pruebas.

    @AfterTest
    fun resetMachineAfterTest() {
        resetMachine()
    }

    // =========================================================================
    // PRUEBAS DE TRANSICIÓN DE ESTADOS
    // =========================================================================

    // 1. Probar Estado E1: Reposo (Idle)
    @Test
    fun testEstadoE1_TransicionAE2() {
        // Dado: La máquina está en reposo (E1)
        CoffeeMachine.currentState = E1_Idle

        // Cuando: Se ejecuta el gestor de estados
        val siguienteEstado = CoffeeMachine.gestorEstados()

        // Entonces: Debe transicionar automáticamente a Selección de Bebida (E2)
        assertEquals(E2_SeleccionBebida, siguienteEstado)
    }

    // 2. Probar Estado E2: Selección de Bebida (Con Stock)
    @Test
    fun testEstadoE2_TransicionAE4_ConStock() {
        // Dado: La máquina está en E2 y se selecciona una bebida con stock disponible (> 0)
        CoffeeMachine.currentState = E2_SeleccionBebida
        CoffeeMachine.bebida = Bebida.CAFE_SOLO

        // Cuando: Se evalúa la transición
        val siguienteEstado = CoffeeMachine.gestorEstados()

        // Entonces: Avanza a Selección de Azúcar (E4)
        assertEquals(E4_SeleccionAzucar, siguienteEstado)
    }

    // 3. Probar Estado E3: Bebida No Disponible
    @Test
    fun testEstadoE3_TransicionAE1() {
        // Dado: Se cayó en el estado de fallo por falta de bebida (E3)
        CoffeeMachine.currentState = E3_BebidaNoDisponible

        // Cuando: Se evalúa la transición
        val siguienteEstado = CoffeeMachine.gestorEstados()

        // Entonces: Se cancela la operación y retorna al estado inicial de reposo (E1)
        assertEquals(E1_Idle, siguienteEstado)
    }

    // 4. Probar Estado E4: Selección de Azúcar (Sin Azúcar)
    @Test
    fun testEstadoE4_TransicionAE5_SinAzucar() {
        // Dado: La máquina está en selección de azúcar (E4) y el usuario decide NO añadir azúcar
        CoffeeMachine.currentState = E4_SeleccionAzucar
        CoffeeMachine.quiereAzucar = false

        // Cuando: Se evalúa la transición
        val siguienteEstado = CoffeeMachine.gestorEstados()

        // Entonces: Salta directo a Preparación Sin Azúcar (E5)
        assertEquals(E5_PreparacionSinAzucar, siguienteEstado)
    }

    // 5. Probar Estado E5: Preparación Sin Azúcar
    @Test
    fun testEstadoE5_TransicionAE9() {
        // Dado: Se prepara Café Solo sin azúcar (E5)
        CoffeeMachine.bebida = Bebida.CAFE_SOLO
        CoffeeMachine.currentState = E5_PreparacionSinAzucar

        // Cuando: Finaliza el proceso de preparación sin azúcar
        val siguienteEstado = CoffeeMachine.gestorEstados()

        // Entonces: Pasa a Recogida de Café (E9), asociando la bebida servida
        assertEquals(E9_RecogidaCafe(Bebida.CAFE_SOLO), siguienteEstado)
    }

    // 6. Probar Estado E6: Selección de Cantidad de Azúcar (Con Stock de Azúcar)
    @Test
    fun testEstadoE6_TransicionAE8_ConStockAzucar() {
        // Dado: En E6 el usuario pide 2 unidades de azúcar y hay stock suficiente (5 disponibles)
        CoffeeMachine.currentState = E6_SeleccionCantidadAzucar
        CoffeeMachine.stockAzucar = 5
        CoffeeMachine.cantidadAzucarDeseada = 2

        // Cuando: Se evalúa la transición
        val siguienteEstado = CoffeeMachine.gestorEstados()

        // Entonces: Avanza a Preparación Con Azúcar (E8)
        assertEquals(E8_PreparacionConAzucar, siguienteEstado)
    }

    // 7. Probar Estado E7: Azúcar No Disponible
    @Test
    fun testEstadoE7_TransicionAE4() {
        // Dado: La máquina está en el estado de falta de azúcar (E7)
        CoffeeMachine.currentState = E7_AzucarNoDisponible

        // Cuando: Se evalúa la transición
        val siguienteEstado = CoffeeMachine.gestorEstados()

        // Entonces: Retrocede a Selección de Azúcar (E4) para reevaluar la decisión del cliente
        assertEquals(E4_SeleccionAzucar, siguienteEstado)
    }

    // 8. Probar Estado E8: Preparación Con Azúcar
    @Test
    fun testEstadoE8_TransicionAE9() {
        // Dado: Se prepara Café Solo con azúcar (E8)
        CoffeeMachine.bebida = Bebida.CAFE_SOLO
        CoffeeMachine.currentState = E8_PreparacionConAzucar

        // Cuando: Finaliza la preparación con azúcar
        val siguienteEstado = CoffeeMachine.gestorEstados()

        // Entonces: Pasa al estado de Recogida de Café (E9)
        assertEquals(E9_RecogidaCafe(Bebida.CAFE_SOLO), siguienteEstado)
    }

    // 9. Probar Estado E9: Recogida de Café
    @Test
    fun testEstadoE9_TransicionAE1() {
        // Dado: El cliente retira su café preparado (E9)
        CoffeeMachine.currentState = E9_RecogidaCafe(Bebida.CAFE_SOLO)

        // Cuando: Se concluye la recogida
        val siguienteEstado = CoffeeMachine.gestorEstados()

        // Entonces: La máquina queda en reposo (E1), lista para el siguiente cliente
        assertEquals(E1_Idle, siguienteEstado)
    }


    /**
     * Reinicia las propiedades compartidas del singleton `CoffeeMachine` y los stocks del enum `Bebida`
     * para evitar que los tests se contaminen entre sí (aislamiento de pruebas).
     */
    private fun resetMachine() {
        // 1. Restablecer el stock inicial de cada tipo de bebida
        Bebida.CAFE_SOLO.cantidad = 5
        Bebida.CAFE_CON_LECHE.cantidad = 8
        Bebida.NO_CAFE.cantidad = 0

        // 2. Restablecer el estado interno y la configuración de la máquina de café
        CoffeeMachine.bebida = null
        CoffeeMachine.quiereAzucar = true
        CoffeeMachine.stockAzucar = 5
        CoffeeMachine.cantidadAzucarDeseada = 2
        CoffeeMachine.currentState = E1_Idle
    }
}