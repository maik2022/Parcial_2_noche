package com.example.parcial_2_noche.Repository

import com.example.parcial_2_noche.DAO.librosDAO
import com.example.parcial_2_noche.InnerJoin.libroConAutor
import com.example.parcial_2_noche.Model.libros

class librosRepository(private var librosDao: librosDAO) {

    suspend fun insertarLibro(libro: libros){
        librosDao.insertarLibro(libro)
    }

    suspend fun listarLibros(): List<libroConAutor>{
        return librosDao.listarLibros()
    }

    suspend fun eliminarLibro(libroId: Int){
        librosDao.eliminarLibro(libroId)
    }

    suspend fun actualizarLibro(libroId: Int, titulo: String, genero: String, autor: Int){
        librosDao.actualizarAutor(libroId, titulo, genero, autor)
    }

}