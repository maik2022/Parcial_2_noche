package com.example.parcial_2_noche.Navegacion

import androidx.compose.runtime.Composable
import com.example.parcial_2_noche.Screen.mainViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.parcial_2_noche.Repository.autoresRepository
import com.example.parcial_2_noche.Repository.librosRepository
import com.example.parcial_2_noche.Repository.miembrosRepository
import com.example.parcial_2_noche.Repository.prestamosRepository
import com.example.parcial_2_noche.Screen.screen_A
import com.example.parcial_2_noche.Screen.screen_b
import com.example.parcial_2_noche.Screen.screen_c
import com.example.parcial_2_noche.Screen.screen_d

@Composable
fun navegacion(librosRepository: librosRepository, autoresRepository: autoresRepository, prestamosRepository: prestamosRepository, miembrosRepository: miembrosRepository){
    val navController = rememberNavController()
    val mainModel = mainViewModel(librosRepository, autoresRepository, prestamosRepository, miembrosRepository)

    NavHost(navController = navController, startDestination = "screen_a")
    {
        composable("screen_a"){
            screen_A(navController, mainModel)
        }

        composable("screen_b"){
            screen_b(navController, mainModel)
        }

        composable("screen_c"){
            screen_c(navController, mainModel)
        }

        composable("screen_d"){
            screen_d(navController, mainModel)
        }

    }
}