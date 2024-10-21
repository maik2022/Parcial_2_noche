package com.example.parcial_2_noche.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.parcial_2_noche.InnerJoin.libroConAutor
import com.example.parcial_2_noche.Model.libros

@Dao
interface librosDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarLibro(libro: libros)

    @Query("""
        SELECT libros.libro_id, libros.titulo, libros.genero, autores.nombre AS nombreAutor
        FROM libros
        INNER JOIN autores ON libros.autor_id = autores.autor_id
    """)
    suspend fun listarLibros(): List<libroConAutor>

    /*
    @Query("SELECT * FROM libros")
    suspend fun listarLibros(): List<libros>*/

    @Query("DELETE FROM libros WHERE libro_id = :libroId")
    suspend fun eliminarLibro(libroId:Int)

    @Query("UPDATE libros SET titulo = :titulo, genero = :genero, autor_id = :autor WHERE libro_id = :libroId")
    suspend fun actualizarAutor(libroId: Int, titulo: String, genero: String, autor: Int)

}