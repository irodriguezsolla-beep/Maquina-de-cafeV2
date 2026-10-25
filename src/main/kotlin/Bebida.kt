// Declaración del paquete: Organiza el código en un espacio de nombres para evitar conflictos de clases.
package org.example

// 'enum class': Tipo especial de clase en Kotlin para definir un conjunto fijo y cerrado de constantes.
// Constructor primario: Define las propiedades que posee cada objeto constante del enum.
enum class Bebida(
    val id: Int,             // 'val' (inmutable / solo lectura): Identificador único numérico de la bebida.
    val nombre: String,       // 'val' (inmutable): Nombre descriptivo de la bebida.
    val precio: Double,       // 'val' (inmutable): Precio de la bebida (número de coma flotante).
    var cantidad: Int = 5     // 'var' (mutable): Cantidad en stock. Al ser 'var' se puede modificar en tiempo de ejecución.
    // '= 5': Valor por defecto si no se especifica el parámetro al crear la constante.
) {
    // Instancia constante 1: id=1, nombre="Café Solo", precio=1.20, cantidad=5
    CAFE_SOLO(1, "Café Solo", 1.20, 5),

    // Instancia constante 2: id=2, nombre="Café con Leche", precio=1.50, cantidad=8
    CAFE_CON_LECHE(2, "Café con Leche", 1.50, 8),

    // Instancia constante 3: id=3, nombre="No cafe", precio=1.80, cantidad=0
    NO_CAFE(3, "No cafe", 1.80, 0);
    // Nota importante: El punto y coma (;) al final de la última constante es obligatorio
    // en Kotlin cuando el enum define un constructor primario o miembros adicionales.
}