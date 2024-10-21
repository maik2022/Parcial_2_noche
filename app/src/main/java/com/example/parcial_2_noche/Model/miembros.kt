package com.example.parcial_2_noche.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "miembros")
data class miembros(
    @PrimaryKey(autoGenerate = true)
    val miembro_id: Int = 0,
    val nombre: String,
    val apellido: String,
    val fecha_inscripcion: Long
)
