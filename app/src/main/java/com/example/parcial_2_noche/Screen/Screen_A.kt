package com.example.parcial_2_noche.Screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.parcial_2_noche.Model.autores

@Composable
fun screen_A(navController: NavController, viewModel: mainViewModel) {

    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var nacionalidad by remember { mutableStateOf("") }
    var autorActualizar by remember { mutableStateOf<autores?>(null)} //Puede ser nulo o autor
    var mostrarVentana by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.listarAutores()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { 
            Text(
                text = "CREACIÓN DE AUTORES DE LIBROS",
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = nombre,
                onValueChange = {nombre = it},
                label = {Text("Nombre")},
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = apellido,
                onValueChange = {apellido = it},
                label = {Text("Apellido")},
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = nacionalidad,
                onValueChange = {nacionalidad = it},
                label = {Text("Nacionalidad")},
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {

                    if(nombre.isBlank() || apellido.isBlank() || nacionalidad.isBlank()){
                        Toast.makeText(context, "Completar todos los campos", Toast.LENGTH_SHORT).show()
                    }else{
                        viewModel.agregarAutor(nombre, apellido, nacionalidad)
                        nombre = ""
                        apellido = ""
                        nacionalidad = ""
                    }


                },
                    modifier = Modifier.fillMaxWidth()

            ) {
                Text(
                    text = "Registrar"
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "LISTA DE AUTORES",
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            viewModel.listaAutores.forEach {autor ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    elevation = CardDefaults.cardElevation(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.LightGray
                    )
                ) {
                    Text(
                        text = "ID: ${autor.autor_id}\nNombre: ${autor.nombre}\nApellido: ${autor.apellido}\nNacionalidad: ${autor.nacionalidad}",
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize(),
                        textAlign = TextAlign.Center
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = {
                                viewModel.eliminarAutor(autor.autor_id)
                            }

                        ) {
                            Text(
                                text = "Eliminar"
                            )
                        }

                        Button(
                            onClick = {
                                autorActualizar = autor // guardar la info
                                mostrarVentana = true // el estado de la ventana,
                            }

                        ) {
                            Text(
                                text = "Actualizar"
                            )
                        }
                    }

                }

            }

            if (mostrarVentana && autorActualizar != null){

                var autorId by remember { mutableStateOf(autorActualizar?.autor_id ?.toString() ?: "") }
                var nombreAct by remember { mutableStateOf(autorActualizar?.nombre ?: "") }
                var apellidoAct by remember { mutableStateOf(autorActualizar?.apellido ?: "") }
                var nacionalidadAct by remember { mutableStateOf(autorActualizar?.nacionalidad ?: "") }

                AlertDialog(
                    onDismissRequest = { mostrarVentana = false },
                    title = {
                        Text(
                            text = "Actualizar Autor",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )

                    },
                    text = {
                        Column {

                            TextField(
                                value = nombreAct,
                                onValueChange = { nombreAct = it },
                                label = { Text("Nombre") }
                            )

                            TextField(
                                value = apellidoAct,
                                onValueChange = { apellidoAct = it },
                                label = { Text("Apellido") }
                            )

                            TextField(
                                value = nacionalidadAct,
                                onValueChange = { nacionalidadAct = it },
                                label = { Text("Nacionalidad") }
                            )

                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {

                                if(nombreAct.isBlank() || apellidoAct.isBlank() || nacionalidadAct.isBlank()){

                                    Toast.makeText(context, "Completar todos los campos", Toast.LENGTH_SHORT).show()

                                }else{

                                    viewModel.actualizarAutor(autorId.toInt(), nombreAct, apellidoAct, nacionalidadAct)
                                    mostrarVentana = false

                                }

                            }
                        ) {
                            Text("Actualizar")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {

                                mostrarVentana = false

                            }
                        ) {
                            Text("Cancelar")
                        }
                    }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        navController.navigate("screen_b")
                    },
                        modifier = Modifier.width(150.dp)

                ) {
                    Text(
                        text = "Libros"
                    )
                }

                Button(
                    onClick = {
                        navController.navigate("screen_d")
                    },
                        modifier = Modifier.width(150.dp)

                ) {
                    Text(
                        text = "Prestamos"
                    )
                }
            }

            Button(
                onClick = {
                    navController.navigate("screen_c")
                },
                    modifier = Modifier.width(200.dp)

            ) {
                Text(
                    text = "Miembros"
                )
            }





        }
    }

}