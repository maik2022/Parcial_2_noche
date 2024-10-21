package com.example.parcial_2_noche.Repository

import com.example.parcial_2_noche.DAO.prestamosDAO
import com.example.parcial_2_noche.InnerJoin.prestamosDetalles
import com.example.parcial_2_noche.Model.prestamos

class prestamosRepository(private var prestamosDao: prestamosDAO) {

    suspend fun insertarPrestamo(prestamo: prestamos){
        prestamosDao.insertarPrestamo(prestamo)
        listarPrestamos()
    }

    suspend fun listarPrestamos(): List<prestamosDetalles>{
        return prestamosDao.listarPrestamos()
    }

    suspend fun eliminarPrestamo(prestamoId: Int){
        prestamosDao.eliminarPrestamo(prestamoId)
    }

    suspend fun actualizarPrestamo(prestamoId: Int, libroId: Int, miembroId: Int, fechaPrestamo: Long, fechaDevolucion: Long){
        prestamosDao.actualizarPrestamo(prestamoId, libroId, miembroId, fechaPrestamo, fechaDevolucion)
    }

}