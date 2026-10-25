// Declaración del paquete: Organiza el código en un espacio de nombres para evitar conflictos.
package org.example

// Importación explícita de la clase/enum 'Bebida'.
import org.example.Bebida
// Importación de todas las constantes o subclases contenidas en 'CoffeeMachineState'.
import org.example.CoffeeMachineState.*

/**
 * Comentario de documentación (KDoc):
 * Define un Singleton que maneja la lógica y las variables globales de la máquina de café.
 */
// 'object': Patron Singleton en Kotlin. Garantiza que existe una única instancia compartida de esta clase.
object CoffeeMachine {

    // Variable mutable anulable ('?'): Guarda la bebida seleccionada. Inicia en 'null' (sin selección).
    var bebida: Bebida? = null

    // Variable mutable booleana: Indica si el cliente quiere azúcar o no. Por defecto 'true'.
    var quiereAzucar: Boolean = true

    // Variable mutable entera: Controla la cantidad disponible de azúcar en el depósito.
    var stockAzucar: Int = 5

    // Variable mutable entera: Guarda las cucharadas de azúcar solicitadas por el cliente.
    var cantidadAzucarDeseada: Int = 2

    // Variable pública y mutable que mantiene el estado actual de la máquina.
    // Inicia en el estado de reposo/espera 'E1_Idle'.
    public var currentState: CoffeeMachineState = E1_Idle

    // Función que evalúa y ejecuta la acción correspondiente al estado actual.
    // Retorna un objeto del tipo 'CoffeeMachineState'.
    fun gestorEstados(): CoffeeMachineState {
        // Imprime una línea separadora en la consola para formatear la salida visual.
        println("-------------------------------")
        // Imprime en consola el estado en el que se encuentra la máquina en este momento.
        println("Estado actual: ${currentState.toString()}")

        // Estructura condicional 'when': Compara el tipo de dato de 'currentState'.
        when (currentState) {
            // Comprueba si el estado es una instancia de la clase/objeto 'E1_Idle'
            is E1_Idle -> {
                // Casteo explícito ('as') al tipo 'E1_Idle' para invocar la función 'onEnter()'
                (currentState as E1_Idle).onEnter()
            }
            // Estado de selección de bebida
            is E2_SeleccionBebida -> {
                (currentState as E2_SeleccionBebida).onEnter()
            }
            // Estado cuando no hay stock de la bebida elegida
            is E3_BebidaNoDisponible -> {
                (currentState as E3_BebidaNoDisponible).onEnter()
            }
            // Estado de pregunta de azúcar
            is E4_SeleccionAzucar -> {
                (currentState as E4_SeleccionAzucar).onEnter()
            }
            // Estado de preparación para bebidas sin azúcar
            is E5_PreparacionSinAzucar -> {
                (currentState as E5_PreparacionSinAzucar).onEnter()
            }
            // Estado para elegir la cantidad de cucharadas de azúcar
            is E6_SeleccionCantidadAzucar -> {
                (currentState as E6_SeleccionCantidadAzucar).onEnter()
            }
            // Estado cuando se pide azúcar pero no hay stock suficiente
            is E7_AzucarNoDisponible -> {
                (currentState as E7_AzucarNoDisponible).onEnter()
            }
            // Estado de preparación para bebidas con azúcar
            is E8_PreparacionConAzucar -> {
                (currentState as E8_PreparacionConAzucar).onEnter()
            }
            // Estado final donde el usuario retira el café servido
            is E9_RecogidaCafe -> {
                (currentState as E9_RecogidaCafe).onEnter()
            }
        }

        // Devuelve la referencia del estado actual tras haber ejecutado 'onEnter()'
        return currentState
    }

}