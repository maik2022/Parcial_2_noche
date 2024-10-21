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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import com.example.parcial_2_noche.InnerJoin.libroConAutor
import com.example.parcial_2_noche.Model.autores
import com.example.parcial_2_noche.Model.libros

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun screen_b(navController: NavController, viewModel: mainViewModel){

    var titulo by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }
    var autorId by remember { mutableStateOf("") }
    var libroActualizar by remember { mutableStateOf<libroConAutor?>(null) }
    var mostrarVentana by remember { mutableStateOf(false) }

    var expanded by remember { mutableStateOf(false) }
    var selectorTexto by remember { mutableStateOf("") }
    var selectorId by remember { mutableStateOf("") }

    var expanded2 by remember { mutableStateOf(false) }
    var selectorTexto2 by remember { mutableStateOf("") }
    var selectorId2 by remember { mutableStateOf("") }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.listarLibros()
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
                text = "CREACIÓN DE LIBRO",
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = titulo,
                onValueChange = {titulo = it},
                label = { Text("Titulo") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = genero,
                onValueChange = {genero = it},
                label = { Text("Genero") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {expanded = !expanded},
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                TextField(
                    value = selectorTexto,
                    onValueChange = {},
                    readOnly = true,
                    label = {Text("Seleccione Autor")},
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    viewModel.listaAutores.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option.nombre) },
                            onClick = {
                                selectorTexto = option.nombre
                                selectorId = option.autor_id.toString()
                                expanded = false
                            })
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {

                    if(titulo.isBlank() || genero.isBlank() || selectorId.isEmpty()){
                        Toast.makeText(context, "Completar todos los campos", Toast.LENGTH_SHORT).show()
                    }else{
                        viewModel.agregarLibro(titulo, genero, selectorId.toInt())
                        titulo = ""
                        genero = ""
                        selectorId = ""
                        selectorTexto = ""
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
                text = "LISTA DE LIBROS",
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            viewModel.listaLibros.forEach {libro ->

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
                        text = "ID: ${libro.libro_id}\nTitulo: ${libro.titulo}\nGenero: ${libro.genero}\nAutor: ${libro.nombreAutor}",
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
                                viewModel.eliminarLibro(libro.libro_id)
                            }

                        ) {
                            Text(
                                text = "Eliminar"
                            )
                        }

                        Button(
                            onClick = {
                                libroActualizar = libro
                                mostrarVentana = true
                            }

                        ) {
                            Text(
                                text = "Actualizar"
                            )
                        }
                    }

                }

            }

            if (mostrarVentana && libroActualizar != null){

                var libroId by remember { mutableStateOf(libroActualizar?.libro_id ?.toString() ?: "") }
                var tituloAct by remember { mutableStateOf(libroActualizar?.titulo ?: "") }
                var generoAct by remember { mutableStateOf(libroActualizar?.genero ?: "") }
                var autorIdAct by remember { mutableStateOf(libroActualizar?.nombreAutor.toString() ?: "") }

                AlertDialog(
                    onDismissRequest = { mostrarVentana = false },
                    title = {
                        Text(
                            text = "Actualizar Libro",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )

                    },
                    text = {
                        Column {

                            TextField(
                                value = tituloAct,
                                onValueChange = { tituloAct = it },
                                label = { Text("Titulo") }
                            )

                            TextField(
                                value = generoAct,
                                onValueChange = { generoAct = it },
                                label = { Text("Genero") }
                            )

                            ExposedDropdownMenuBox(
                                expanded = expanded2,
                                onExpandedChange = {expanded2 = !expanded2},
                                modifier = Modifier
                                    .fillMaxWidth()

                            ) {
                                TextField(
                                    value = autorIdAct,
                                    onValueChange = {},
                                    readOnly = true,
                                    label = {Text("Seleccione Autor")},
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded2)
                                    },
                                    modifier = Modifier
                                        .menuAnchor()
                                        .fillMaxWidth()
                                )

                                ExposedDropdownMenu(
                                    expanded = expanded2,
                                    onDismissRequest = { expanded2 = false }
                                ) {
                                    viewModel.listaAutores.forEach { option ->

                                        DropdownMenuItem(
                                            text = { Text(option.nombre) },
                                            onClick = {
                                                autorIdAct = option.nombre
                                                selectorId2 = option.autor_id.toString()
                                                expanded2 = false
                                            })
                                    }
                                }
                            }

                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {

                                if(tituloAct.isBlank() || generoAct.isBlank()){
                                    Toast.makeText(context, "Completar todos los campos", Toast.LENGTH_SHORT).show()
                                }else{
                                    if(selectorId2.isEmpty()){

                                        selectorId2 = viewModel.listaAutores.find { it.nombre == autorIdAct }?.autor_id.toString()

                                    }

                                    viewModel.actualizarLibro(libroId.toInt(), tituloAct, generoAct, selectorId2.toInt())
                                    selectorId2 = ""
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
                                selectorId2 = ""

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
                        navController.navigate("screen_a")
                    },
                        modifier = Modifier.width(150.dp)

                ) {
                    Text(
                        text = "Autores"
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