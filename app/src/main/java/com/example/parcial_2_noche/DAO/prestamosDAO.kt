package com.example.parcial_2_noche.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.parcial_2_noche.InnerJoin.prestamosDetalles
import com.example.parcial_2_noche.Model.prestamos

@Dao
interface prestamosDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarPrestamo(prestamo: prestamos)

    @Query("""
        SELECT p.prestamo_id, l.titulo AS libro_nombre, m.nombre AS miembro_nombre, p.fecha_prestamo, p.fecha_devolucion
        FROM prestamo AS p
        INNER JOIN libros AS l ON p.libro_id = l.libro_id
        INNER JOIN miembros AS m ON p.miembro_id = m.miembro_id
    """)
    suspend fun listarPrestamos(): List<prestamosDetalles>

    @Query("DELETE FROM prestamo WHERE prestamo_id = :prestamoId")
    suspend fun eliminarPrestamo(prestamoId: Int)

    @Query("UPDATE prestamo SET libro_id = :libroId, miembro_id = :miembroId, fecha_prestamo = :fechaPrestamo, fecha_devolucion = :fechaDevolucion WHERE prestamo_id = :prestamoId")
    suspend fun actualizarPrestamo(prestamoId: Int, libroId: Int, miembroId: Int, fechaPrestamo: Long, fechaDevolucion: Long)

}