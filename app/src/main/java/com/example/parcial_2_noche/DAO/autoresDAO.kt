package com.example.parcial_2_noche.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.parcial_2_noche.Model.autores

@Dao
interface autoresDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarAutor(autor: autores)

    @Query("SELECT * FROM autores")
    suspend fun listarAutores(): List<autores>

    @Query("DELETE FROM autores WHERE autor_id = :autorId")
    suspend fun eliminarAutor(autorId:Int)

    @Query("UPDATE autores SET nombre = :nombre, apellido = :apellido, nacionalidad = :nacionalidad WHERE autor_id = :autorId")
    suspend fun actualizarAutor(autorId: Int, nombre: String, apellido: String, nacionalidad:String)

    /*
    @Query("SELECT autor_id, nombre FROM autores")
    suspend fun obtenerAutores():List<autores>*/
}