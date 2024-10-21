package com.example.parcial_2_noche.InnerJoin

data class prestamosDetalles(
    val prestamo_id: Int,
    val libro_nombre: String,
    val miembro_nombre: String,
    val fecha_prestamo: Long,
    val fecha_devolucion: Long
)