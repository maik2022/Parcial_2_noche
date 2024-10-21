package com.example.parcial_2_noche.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.parcial_2_noche.DAO.autoresDAO
import com.example.parcial_2_noche.DAO.librosDAO
import com.example.parcial_2_noche.DAO.miembrosDAO
import com.example.parcial_2_noche.DAO.prestamosDAO
import com.example.parcial_2_noche.Model.autores
import com.example.parcial_2_noche.Model.libros
import com.example.parcial_2_noche.Model.miembros
import com.example.parcial_2_noche.Model.prestamos

@Database(entities = [autores::class, libros::class, miembros::class, prestamos::class], version = 1, exportSchema = false)
abstract class AppDatabase:RoomDatabase() {

    abstract fun autoresDao(): autoresDAO
    abstract fun librosDao(): librosDAO
    abstract fun miembrosDao(): miembrosDAO
    abstract fun prestamosDao(): prestamosDAO

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}