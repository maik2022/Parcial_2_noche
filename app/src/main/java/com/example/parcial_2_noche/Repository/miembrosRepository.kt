package com.example.parcial_2_noche.Repository

import com.example.parcial_2_noche.DAO.miembrosDAO
import com.example.parcial_2_noche.Model.miembros

class miembrosRepository(private var miembrosDao: miembrosDAO) {

    suspend fun insertarMiembro(miembro: miembros){
        miembrosDao.insertarMiembro(miembro)
    }

    suspend fun listarMiembros(): List<miembros>{
        return miembrosDao.listarMiembros()
    }

    suspend fun eliminarMiembro(miembroId: Int){
        miembrosDao.eliminarMiembro(miembroId)
    }

    suspend fun actualizarMiembro(miembroId: Int, nombre: String, apellido: String, fecha_inscripcion: Long){
        miembrosDao.actualizarMiembro(miembroId, nombre, apellido, fecha_inscripcion)
    }

}