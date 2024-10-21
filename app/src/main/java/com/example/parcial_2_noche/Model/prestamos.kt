package com.example.parcial_2_noche.Model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "prestamo",
    foreignKeys = [
        ForeignKey(
            entity = libros::class,
            parentColumns = ["libro_id"],
            childColumns = ["libro_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = miembros::class,
            parentColumns = ["miembro_id"],
            childColumns = ["miembro_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class prestamos(
    @PrimaryKey(autoGenerate = true)
    val prestamo_id: Int = 0,
    val libro_id: Int,
    val miembro_id: Int,
    val fecha_prestamo: Long,
    val fecha_devolucion: Long
)
