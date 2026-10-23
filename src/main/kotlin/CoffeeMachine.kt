package org.example

import org.example.Bebida
import org.example.CoffeeMachineState.*

/**
 * Singleton que maneja lo que ocurre en cada estado
 */
object CoffeeMachine {

    var bebida: Bebida? = null
    var quiereAzucar: Boolean = true
    var stockAzucar: Int = 5
    var cantidadAzucarDeseada: Int = 2

    public var currentState: CoffeeMachineState = E1_Idle

    fun gestorEstados(): CoffeeMachineState {
        println("-------------------------------")
        println("Estado actual: ${currentState.toString()}")

        when (currentState) {
            is E1_Idle -> {
                (currentState as E1_Idle).onEnter()
            }
            is E2_SeleccionBebida -> {
                (currentState as E2_SeleccionBebida).onEnter()
            }
            is E3_BebidaNoDisponible -> {
                (currentState as E3_BebidaNoDisponible).onEnter()
            }
            is E4_SeleccionAzucar -> {
                (currentState as E4_SeleccionAzucar).onEnter()
            }
            is E5_PreparacionSinAzucar -> {
                (currentState as E5_PreparacionSinAzucar).onEnter()
            }
            is E6_SeleccionCantidadAzucar -> {
                (currentState as E6_SeleccionCantidadAzucar).onEnter()
            }
            is E7_AzucarNoDisponible -> {
                (currentState as E7_AzucarNoDisponible).onEnter()
            }
            is E8_PreparacionConAzucar -> {
                (currentState as E8_PreparacionConAzucar).onEnter()
            }
            is E9_RecogidaCafe -> {
                (currentState as E9_RecogidaCafe).onEnter()
            }
        }

        return currentState
    }

}