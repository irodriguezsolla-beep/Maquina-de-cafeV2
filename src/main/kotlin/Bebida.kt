package org.example

enum class Bebida(
    val id: Int,
    val nombre: String,
    val precio: Double,
    var cantidad: Int = 5,
) {
    CAFE_SOLO(1, "Café Solo", 1.20,5),
    CAFE_CON_LECHE(2, "Café con Leche", 1.50,8),
    NO_CAFE(3, "No cafe", 1.80,0);
}