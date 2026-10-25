// Declaración del paquete: Organiza las clases dentro del espacio de nombres especificado.
package org.example

// Importación de las propiedades globales mutables declaradas en el objeto 'CoffeeMachine'.
import org.example.CoffeeMachine.currentState
import org.example.CoffeeMachine.bebida
import org.example.CoffeeMachine.quiereAzucar
import org.example.CoffeeMachine.stockAzucar
import org.example.CoffeeMachine.cantidadAzucarDeseada
// Importación de la clase/enum Bebida.
import org.example.Bebida

/**
 * Comentario de documentación (KDoc):
 * Jerarquía de clases para modelar los estados finitos de la máquina de café.
 */
// 'sealed class': Clase sellada que restringe la jerarquía de herencia. 
// Todos los subtipos posibles deben ser declarados dentro del mismo archivo.
sealed class CoffeeMachineState {

    // 'object': Estado E1 representado como un Singleton sin estado propio. Hereda de 'CoffeeMachineState'.
    object E1_Idle : CoffeeMachineState() {
        // Función que ejecuta la lógica al entrar en el estado de reposo.
        fun onEnter() {
            println("Entrando al estado E1_Idle")
            // Pausa la ejecución del hilo actual durante 2000 milisegundos (2 segundos).
            Thread.sleep(2000)
            // Transición de estado: Cambia la variable global 'currentState' al estado E2_SeleccionBebida.
            currentState = E2_SeleccionBebida
        }

        // Sobrescribe 'toString()' para devolver una representación simplificada en formato String.
        override fun toString(): String {
            return "E1_Idle"
        }
    }

    // Estado E2: Representa el proceso de verificación y selección de la bebida.
    object E2_SeleccionBebida : CoffeeMachineState() {
        fun onEnter() {
            println("Entrando al estado E2_SeleccionBebida")
            Thread.sleep(2000)

            // Asigna la referencia global 'bebida' a una variable local 'b'.
            val b = bebida
            // Comprueba que la bebida elegida no sea nula y que disponga de stock (cantidad > 0).
            if (b != null && b.cantidad > 0) {
                // Decrementa en 1 el stock disponible de la bebida.
                b.cantidad--
                println("Bebida seleccionada: ${b.nombre}. Stock restante: ${b.cantidad}")
                // Transición al estado de selección de azúcar.
                currentState = E4_SeleccionAzucar
            } else {
                println("No queda stock de esta bebida.")
                // Transición al estado de error de stock de bebida.
                currentState = E3_BebidaNoDisponible
            }
        }

        override fun toString(): String {
            // Utiliza la llamada segura (?.) y el operador Elvis (?:) para evitar fallos por nulo si no hay bebida.
            return "E2_SeleccionBebida(bebida=${bebida?.nombre ?: "Ninguna"})"
        }
    }

    // Estado E3: Maneja el error de falta de stock para la bebida solicitada.
    object E3_BebidaNoDisponible : CoffeeMachineState() {
        fun onEnter() {
            println("Entrando al estado E3_BebidaNoDisponible")
            Thread.sleep(2000)
            println("Error: no hay café.")
            // Reinicia la máquina redirigiéndola al estado inicial de reposo.
            currentState = E1_Idle
        }

        override fun toString(): String {
            return "E3_BebidaNoDisponible"
        }
    }

    // Estado E4: Evalúa la preferencia de azúcar del usuario y valida el stock general disponible.
    object E4_SeleccionAzucar : CoffeeMachineState() {
        fun onEnter() {
            println("Entrando al estado E4_SeleccionAzucar")
            Thread.sleep(2000)

            // Verifica si el usuario indicó que no quiere azúcar.
            if (!quiereAzucar) {
                println("Opción elegida: Sin azúcar.")
                // Transición directa al estado de preparación sin azúcar.
                currentState = E5_PreparacionSinAzucar
            } else {
                // Si el usuario quiere azúcar, comprueba si queda stock en la máquina.
                if (stockAzucar > 0) {
                    println("Azúcar disponible. Redirigiendo a selección de cantidad...")
                    // Transición a la selección de cantidad de azúcar.
                    currentState = E6_SeleccionCantidadAzucar
                } else {
                    // Transición al estado de azúcar agotado.
                    currentState = E7_AzucarNoDisponible
                }
            }
        }

        override fun toString(): String {
            return "E4_SeleccionAzucar(quiereAzucar=$quiereAzucar, stockAzucar=$stockAzucar)"
        }
    }

    // Estado E5: Simula el proceso de preparación del café sin azúcar.
    object E5_PreparacionSinAzucar : CoffeeMachineState() {
        fun onEnter() {
            println("Entrando al estado E5_PreparacionSinAzucar")
            println("Preparando café....")
            Thread.sleep(2000)
            // Transición al estado de recogida instanciando 'E9_RecogidaCafe' pasando la bebida seleccionada.
            currentState = E9_RecogidaCafe(bebida = bebida)
        }

        override fun toString(): String {
            return "E5_PreparacionSinAzucar"
        }
    }

    // Estado E6: Valida y descuenta la cantidad exacta de cucharadas solicitadas.
    object E6_SeleccionCantidadAzucar : CoffeeMachineState() {
        fun onEnter() {
            println("Entrando al estado E6_SeleccionCantidadAzucar")
            Thread.sleep(2000)
            println("Cantidad elegida: $cantidadAzucarDeseada cucharadas/sobres.")

            // Comprueba si el stock restante es suficiente para la cantidad deseada.
            if (stockAzucar >= cantidadAzucarDeseada) {
                // Descuenta la cantidad del stock global.
                stockAzucar -= cantidadAzucarDeseada
                println("Azúcar asignado correctamente. Stock restante de azúcar: $stockAzucar")
                // Transición al estado de preparación con azúcar.
                currentState = E8_PreparacionConAzucar
            } else {
                // Transición a azúcar no disponible si el stock es insuficiente.
                currentState = E7_AzucarNoDisponible
            }
        }

        override fun toString(): String {
            return "E6_SeleccionCantidadAzucar(deseada=$cantidadAzucarDeseada, stock=$stockAzucar)"
        }
    }

    // Estado E7: Informa de la falta de azúcar suficiente y solicita de nuevo la decisión.
    object E7_AzucarNoDisponible : CoffeeMachineState() {
        fun onEnter() {
            println("Entrando al estado E7_AzucarNoDisponible")
            Thread.sleep(2000)
            println("No hay suficiente azúcar para la cantidad elegida (Stock actual: $stockAzucar).")
            // Devuelve el flujo al estado de selección de azúcar (E4).
            currentState = E4_SeleccionAzucar
        }

        override fun toString(): String {
            return "E7_AzucarNoDisponible(stockAzucar=$stockAzucar)"
        }
    }

    // Estado E8: Simula el proceso de preparación del café con azúcar.
    object E8_PreparacionConAzucar : CoffeeMachineState() {
        fun onEnter() {
            println("Entrando al estado E8_PreparacionConAzucar")
            println("Preparando café con azúcar....")
            Thread.sleep(2000)
            // Transición al estado final de recogida pasando la referencia de la bebida.
            currentState = E9_RecogidaCafe(bebida = bebida)
        }

        override fun toString(): String {
            return "E8_PreparacionConAzucar"
        }
    }

    // 'data class': A diferencia de 'object', se usa una clase de datos porque almacena información variable por instancia.
    // Recibe como parámetro por defecto la bebida actual definida en 'CoffeeMachine'.
    data class E9_RecogidaCafe(var bebida: Bebida? = CoffeeMachine.bebida) : CoffeeMachineState() {
        fun onEnter() {
            println("Entrando al estado E9_RecogidaCafe")
            // Imprime el mensaje de recogida utilizando el operador Elvis (?:) en caso de que 'bebida' sea nula.
            println("Por favor, toma tu café: ${bebida?.nombre ?: "Bebida"}")
            Thread.sleep(2000)
            // Finaliza el ciclo y reinicia la máquina devolviéndola al estado inicial (E1_Idle).
            currentState = E1_Idle
        }

        override fun toString(): String {
            return "E9_RecogidaCafe(bebida=${bebida?.nombre})"
        }
    }
}