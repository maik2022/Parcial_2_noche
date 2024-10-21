package com.example.parcial_2_noche

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.parcial_2_noche.DAO.autoresDAO
import com.example.parcial_2_noche.DAO.librosDAO
import com.example.parcial_2_noche.DAO.miembrosDAO
import com.example.parcial_2_noche.DAO.prestamosDAO
import com.example.parcial_2_noche.Database.AppDatabase
import com.example.parcial_2_noche.Navegacion.navegacion
import com.example.parcial_2_noche.Repository.autoresRepository
import com.example.parcial_2_noche.Repository.librosRepository
import com.example.parcial_2_noche.Repository.miembrosRepository
import com.example.parcial_2_noche.Repository.prestamosRepository
import com.example.parcial_2_noche.ui.theme.Parcial_2_NocheTheme

class MainActivity : ComponentActivity() {

    private lateinit var librosDao: librosDAO
    private lateinit var autoresDao: autoresDAO
    private lateinit var prestamosDao: prestamosDAO
    private lateinit var miembrosDao: miembrosDAO

    private lateinit var LibrosRepository: librosRepository
    private lateinit var AutoresRepository: autoresRepository
    private lateinit var PrestamosRepository: prestamosRepository
    private lateinit var MiembrosRepository: miembrosRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.getDatabase(applicationContext)

        librosDao = db.librosDao()
        autoresDao = db.autoresDao()
        prestamosDao = db.prestamosDao()
        miembrosDao = db.miembrosDao()

        LibrosRepository = librosRepository(librosDao)
        AutoresRepository = autoresRepository(autoresDao)
        PrestamosRepository = prestamosRepository(prestamosDao)
        MiembrosRepository = miembrosRepository(miembrosDao)

        setContent {
           navegacion(LibrosRepository, AutoresRepository, PrestamosRepository, MiembrosRepository)
        }
    }
}
