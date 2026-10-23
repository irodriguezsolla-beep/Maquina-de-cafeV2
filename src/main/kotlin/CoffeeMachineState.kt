package org.example

import org.example.CoffeeMachine.currentState
import org.example.CoffeeMachine.bebida
import org.example.CoffeeMachine.quiereAzucar
import org.example.CoffeeMachine.stockAzucar
import org.example.CoffeeMachine.cantidadAzucarDeseada
import org.example.Bebida

/**
 * Clase sellada que representa los diferentes estados de la máquina de café
 */
sealed class CoffeeMachineState {

    // E1: Idle
    object E1_Idle : CoffeeMachineState() {
        fun onEnter() {
            println("Entrando al estado E1_Idle")
            Thread.sleep(2000)
            currentState = E2_SeleccionBebida
        }

        override fun toString(): String {
            return "E1_Idle"
        }
    }

    // E2: Selección de bebida
    object E2_SeleccionBebida : CoffeeMachineState() {
        fun onEnter() {
            println("Entrando al estado E2_SeleccionBebida")
            Thread.sleep(2000)

            val b = bebida
            if (b != null && b.cantidad > 0) {
                b.cantidad--
                println("Bebida seleccionada: ${b.nombre}. Stock restante: ${b.cantidad}")
                currentState = E4_SeleccionAzucar
            } else {
                println("No queda stock de esta bebida.")
                currentState = E3_BebidaNoDisponible
            }
        }

        override fun toString(): String {
            return "E2_SeleccionBebida(bebida=${bebida?.nombre ?: "Ninguna"})"
        }
    }

    // E3: Bebida no disponible
    object E3_BebidaNoDisponible : CoffeeMachineState() {
        fun onEnter() {
            println("Entrando al estado E3_BebidaNoDisponible")
            Thread.sleep(2000)
            println("Error: no hay café.")
            currentState = E1_Idle
        }

        override fun toString(): String {
            return "E3_BebidaNoDisponible"
        }
    }

    // E4: Selección de azúcar
    object E4_SeleccionAzucar : CoffeeMachineState() {
        fun onEnter() {
            println("Entrando al estado E4_SeleccionAzucar")
            Thread.sleep(2000)

            if (!quiereAzucar) {
                println("Opción elegida: Sin azúcar.")
                currentState = E5_PreparacionSinAzucar
            } else {
                if (stockAzucar > 0) {
                    println("Azúcar disponible. Redirigiendo a selección de cantidad...")
                    currentState = E6_SeleccionCantidadAzucar
                } else {
                    currentState = E7_AzucarNoDisponible
                }
            }
        }

        override fun toString(): String {
            return "E4_SeleccionAzucar(quiereAzucar=$quiereAzucar, stockAzucar=$stockAzucar)"
        }
    }

    // E5: Preparación sin azúcar
    object E5_PreparacionSinAzucar : CoffeeMachineState() {
        fun onEnter() {
            println("Entrando al estado E5_PreparacionSinAzucar")
            println("Preparando café....")
            Thread.sleep(2000)
            currentState = E9_RecogidaCafe(bebida = bebida)
        }

        override fun toString(): String {
            return "E5_PreparacionSinAzucar"
        }
    }

    // E6: Selección de cantidad de azúcar
    object E6_SeleccionCantidadAzucar : CoffeeMachineState() {
        fun onEnter() {
            println("Entrando al estado E6_SeleccionCantidadAzucar")
            Thread.sleep(2000)
            println("Cantidad elegida: $cantidadAzucarDeseada cucharadas/sobres.")

            if (stockAzucar >= cantidadAzucarDeseada) {
                stockAzucar -= cantidadAzucarDeseada
                println("Azúcar asignado correctamente. Stock restante de azúcar: $stockAzucar")
                currentState = E8_PreparacionConAzucar
            } else {
                currentState = E7_AzucarNoDisponible
            }
        }

        override fun toString(): String {
            return "E6_SeleccionCantidadAzucar(deseada=$cantidadAzucarDeseada, stock=$stockAzucar)"
        }
    }

    // E7: Azúcar no disponible
    object E7_AzucarNoDisponible : CoffeeMachineState() {
        fun onEnter() {
            println("Entrando al estado E7_AzucarNoDisponible")
            Thread.sleep(2000)
            println("No hay suficiente azúcar para la cantidad elegida (Stock actual: $stockAzucar).")
            currentState = E4_SeleccionAzucar
        }

        override fun toString(): String {
            return "E7_AzucarNoDisponible(stockAzucar=$stockAzucar)"
        }
    }

    // E8: Preparación con azúcar
    object E8_PreparacionConAzucar : CoffeeMachineState() {
        fun onEnter() {
            println("Entrando al estado E8_PreparacionConAzucar")
            println("Preparando café con azúcar....")
            Thread.sleep(2000)
            currentState = E9_RecogidaCafe(bebida = bebida)
        }

        override fun toString(): String {
            return "E8_PreparacionConAzucar"
        }
    }

    // E9: Recogida del café
    data class E9_RecogidaCafe(var bebida: Bebida? = CoffeeMachine.bebida) : CoffeeMachineState() {
        fun onEnter() {
            println("Entrando al estado E9_RecogidaCafe")
            println("Por favor, toma tu café: ${bebida?.nombre ?: "Bebida"}")
            Thread.sleep(2000)
            currentState = E1_Idle
        }

        override fun toString(): String {
            return "E9_RecogidaCafe(bebida=${bebida?.nombre})"
        }
    }
}