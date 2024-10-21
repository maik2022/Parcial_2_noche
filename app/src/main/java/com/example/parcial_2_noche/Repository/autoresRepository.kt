package com.example.parcial_2_noche.Repository

import com.example.parcial_2_noche.DAO.autoresDAO
import com.example.parcial_2_noche.Model.autores

class autoresRepository(private var autoresDao: autoresDAO) {

    suspend fun insertarAutor(autor: autores){
        autoresDao.insertarAutor(autor)
    }

    suspend fun listarAutores(): List<autores>{
        return autoresDao.listarAutores()
    }

    suspend fun eliminarAutor(autorId: Int){
        autoresDao.eliminarAutor(autorId)
    }

    suspend fun actualizarAutor(autorId: Int, nombre: String, apellido: String, nacionalidad:String){
        autoresDao.actualizarAutor(autorId, nombre, apellido, nacionalidad)
    }

    /*
    suspend fun obtenerAutores(): List<autores>{
        return autoresDao.obtenerAutores()
    }*/


}