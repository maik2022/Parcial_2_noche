package com.example.parcial_2_noche.Model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "libros",
    foreignKeys = [
        ForeignKey(
            entity = autores::class,
            parentColumns = ["autor_id"],
            childColumns = ["autor_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class libros(
    @PrimaryKey(autoGenerate = true)
    val libro_id: Int = 0,
    val titulo: String,
    val genero: String,
    val autor_id: Int
)
