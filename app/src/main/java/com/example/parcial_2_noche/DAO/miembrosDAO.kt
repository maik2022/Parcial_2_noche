package com.example.parcial_2_noche.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.parcial_2_noche.Model.miembros

@Dao
interface miembrosDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarMiembro(miembro: miembros)

    @Query("SELECT * FROM miembros")
    suspend fun listarMiembros(): List<miembros>

    @Query("DELETE FROM miembros WHERE miembro_id = :miembroId")
    suspend fun eliminarMiembro(miembroId: Int)

    @Query("UPDATE miembros SET nombre = :nombre, apellido = :apellido, fecha_inscripcion = :fecha_inscripcion WHERE miembro_id = :miembroId")
    suspend fun actualizarMiembro(miembroId: Int, nombre: String, apellido: String, fecha_inscripcion: Long)

}