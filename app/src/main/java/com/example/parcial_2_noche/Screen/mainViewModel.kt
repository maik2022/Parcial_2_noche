package com.example.parcial_2_noche.Screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parcial_2_noche.InnerJoin.libroConAutor
import com.example.parcial_2_noche.InnerJoin.prestamosDetalles
import com.example.parcial_2_noche.Model.autores
import com.example.parcial_2_noche.Model.libros
import com.example.parcial_2_noche.Model.miembros
import com.example.parcial_2_noche.Model.prestamos
import com.example.parcial_2_noche.Repository.autoresRepository
import com.example.parcial_2_noche.Repository.librosRepository
import com.example.parcial_2_noche.Repository.miembrosRepository
import com.example.parcial_2_noche.Repository.prestamosRepository
import kotlinx.coroutines.launch

class mainViewModel(val librosRepository: librosRepository, val autoresRepository: autoresRepository, val prestamosRepository: prestamosRepository, val miembrosRepository: miembrosRepository):ViewModel() {

    var listaAutores by mutableStateOf(emptyList<autores>())
    var listaLibros by mutableStateOf(emptyList<libroConAutor>())
    var listamiembros by mutableStateOf(emptyList<miembros>())
    var listaPrestamos by mutableStateOf(emptyList<prestamosDetalles>())

    //Funciones para la tabla autores

    fun agregarAutor(nombre: String, apellido: String, nacionalidad: String){
        viewModelScope.launch {
            val nuevoAutor = autores(nombre = nombre, apellido = apellido, nacionalidad = nacionalidad)
            autoresRepository.insertarAutor(nuevoAutor)
            listarAutores()
        }
    }

    fun listarAutores(){
        viewModelScope.launch {
            listaAutores = autoresRepository.listarAutores()
        }
    }

    fun eliminarAutor(id:Int){
        viewModelScope.launch {
            autoresRepository.eliminarAutor(id)
            listarAutores()
        }
    }

    fun actualizarAutor(autorId: Int, nombre: String, apellido: String, nacionalidad:String){
        viewModelScope.launch {
            autoresRepository.actualizarAutor(autorId, nombre, apellido, nacionalidad)
            listarAutores()
        }
    }


    //Funciones para la tabla libros

    fun agregarLibro(titulo: String, genero: String, autorId: Int){
        viewModelScope.launch {
            val nuevoLibro = libros(titulo = titulo, genero = genero, autor_id = autorId)
            librosRepository.insertarLibro(nuevoLibro)
            listarLibros()
        }
    }

    fun listarLibros(){
        viewModelScope.launch {
            listaLibros = librosRepository.listarLibros()
        }
    }

    fun eliminarLibro(id:Int){
        viewModelScope.launch {
            librosRepository.eliminarLibro(id)
            listarLibros()
        }
    }

    fun actualizarLibro(libroId: Int, titulo: String, genero: String, autor: Int){
        viewModelScope.launch {
            librosRepository.actualizarLibro(libroId, titulo, genero, autor)
            listarLibros()
        }
    }

    //Funciones para la tabla miembros

    fun agregarMiembro(nombre: String, apellido: String, fecha_inscripcion: Long){
        viewModelScope.launch {
            val nuevoMiembro = miembros(nombre = nombre, apellido = apellido, fecha_inscripcion = fecha_inscripcion)
            miembrosRepository.insertarMiembro(nuevoMiembro)
            listarMiembros()
        }
    }

    fun listarMiembros(){
        viewModelScope.launch {
            listamiembros = miembrosRepository.listarMiembros()
        }
    }

    fun eliminarMiembro(id:Int){
        viewModelScope.launch {
            miembrosRepository.eliminarMiembro(id)
            listarMiembros()
        }
    }

    fun actualizarMiembros(miembroId: Int, nombre: String, apellido: String, fecha_inscripcion: Long){
        viewModelScope.launch {
            miembrosRepository.actualizarMiembro(miembroId, nombre, apellido, fecha_inscripcion)
            listarMiembros()
        }
    }

    //Funciones para la tabla prestamos

    fun insertarPrestamo(libroId: Int, miembroId: Int, fechaPrestamo: Long, fechaDevolucion: Long){
        viewModelScope.launch {
            val nuevoPrestamo = prestamos(libro_id = libroId, miembro_id = miembroId, fecha_prestamo = fechaPrestamo, fecha_devolucion = fechaDevolucion)
            prestamosRepository.insertarPrestamo(nuevoPrestamo)
            listarPrestamos()
        }
    }

    fun listarPrestamos(){
        viewModelScope.launch {
            listaPrestamos = prestamosRepository.listarPrestamos()
        }
    }

    fun eliminarPrestamo(id:Int){
        viewModelScope.launch {
            prestamosRepository.eliminarPrestamo(id)
            listarPrestamos()
        }
    }

    fun actualizarPrestamo(prestamoId: Int, libroId: Int, miembroId: Int, fechaPrestamo: Long, fechaDevolucion: Long){
        viewModelScope.launch {
            prestamosRepository.actualizarPrestamo(prestamoId, libroId, miembroId, fechaPrestamo, fechaDevolucion)
            listarPrestamos()
        }
    }


}