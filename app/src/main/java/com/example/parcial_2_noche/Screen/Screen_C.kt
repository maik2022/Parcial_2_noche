package com.example.parcial_2_noche.Screen

import android.app.DatePickerDialog
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
import com.example.parcial_2_noche.InnerJoin.libroConAutor
import com.example.parcial_2_noche.Model.miembros
import java.text.DateFormat
import java.util.Calendar
import java.util.Date

@Composable
fun screen_c(navController: NavController, viewModel: mainViewModel){

    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var fecha_inscripcion by remember { mutableStateOf("") }
    var miembroActualizar by remember { mutableStateOf<miembros?>(null) } //Puede ser nulo o autor
    var mostrarVentana by remember { mutableStateOf(false) }
    //Manejo de la fecha

    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val mes = calendar.get(Calendar.MONTH)
    val dia = calendar.get(Calendar.DAY_OF_MONTH)
    //Estado para manejar la fecha seleccionada
    val seleccionFecha = remember { mutableStateOf<Long?>(null)}
    val seleccionFechaAct = remember { mutableStateOf<Long?>(null)}


    LaunchedEffect(Unit) {
        viewModel.listarMiembros()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {

            Text(
                text = "CREACIÓN DE MIEMBROS",
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

            //Mostrar el selector de fecha

            Button(
                onClick = {
                    DatePickerDialog(context, { _, seleccionYear, seleccionMes, seleccionDia ->
                        //Convertir la fecha a long

                        val cal = Calendar.getInstance()
                        cal.set(seleccionYear, seleccionMes, seleccionDia)
                        seleccionFecha.value= cal.timeInMillis

                    }, year, mes, dia).show()
                },
                    modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Seleccionar Fecha")
            }
            
            seleccionFecha.value?.let {
                TextField(
                    value = DateFormat.getDateInstance().format(Date(it)),
                    onValueChange = {},
                    label = {Text("Fecha Inscripción")},
                    enabled = false,
                    modifier = Modifier.fillMaxWidth()
                )

                //Text(text = "Fecha: ${DateFormat.getDateInstance().format(Date(it))}")
            }

            Button(
                onClick = {
                    if(nombre.isBlank() || apellido.isBlank() || seleccionFecha.value == null){
                        Toast.makeText(context, "Completar todos los campos", Toast.LENGTH_SHORT).show()
                    }else{
                        val fecha: Long = seleccionFecha.value!! //para desreferenciar
                        viewModel.agregarMiembro(nombre, apellido, fecha)
                        nombre = ""
                        apellido = ""
                        seleccionFecha.value = null
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
                text = "LISTA DE MIEMBROS",
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            viewModel.listamiembros.forEach {miembro ->

                val fechaLegible = DateFormat.getDateInstance().format(Date(miembro.fecha_inscripcion))

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
                        text = "ID: ${miembro.miembro_id}\nNombre: ${miembro.nombre}\nApellido: ${miembro.apellido}\nFecha Inscripción: ${fechaLegible}",
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
                                viewModel.eliminarMiembro(miembro.miembro_id)
                            }

                        ) {
                            Text(
                                text = "Eliminar"
                            )
                        }

                        Button(
                            onClick = {
                                miembroActualizar = miembro // guardar la info
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

            if (mostrarVentana && miembroActualizar != null){

                var miembrosId by remember { mutableStateOf(miembroActualizar?.miembro_id ?.toString() ?: "") }
                var nombreAct by remember { mutableStateOf(miembroActualizar?.nombre ?: "") }
                var apellidoAct by remember { mutableStateOf(miembroActualizar?.apellido ?: "") }
                var fechaAct = DateFormat.getDateInstance().format(Date(miembroActualizar!!.fecha_inscripcion))

                AlertDialog(
                    onDismissRequest = { mostrarVentana = false },
                    title = {
                        Text(
                            text = "Actualizar Miembro",
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

                            Button(
                                onClick = {
                                    DatePickerDialog(context, { _, seleccionYear, seleccionMes, seleccionDia ->
                                        //Convertir la fecha a long

                                        val cal = Calendar.getInstance()
                                        cal.set(seleccionYear, seleccionMes, seleccionDia)
                                        seleccionFechaAct.value= cal.timeInMillis

                                    }, year, mes, dia).show()
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(text = "Seleccionar Fecha")
                            }

                            if (seleccionFechaAct.value == null){
                                TextField(
                                    value = fechaAct,
                                    onValueChange = {},
                                    label = {Text("Fecha Inscripción")},
                                    enabled = false,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }else{
                                seleccionFechaAct.value?.let {
                                    TextField(
                                        value = DateFormat.getDateInstance().format(Date(it)),
                                        onValueChange = {},
                                        label = {Text("Fecha Inscripción")},
                                        enabled = false,
                                        modifier = Modifier.fillMaxWidth()
                                    )

                                    //Text(text = "Fecha: ${DateFormat.getDateInstance().format(Date(it))}")
                                }
                            }

                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {

                                if(nombreAct.isBlank() || apellidoAct.isBlank()){
                                    Toast.makeText(context, "Completar todos los campos", Toast.LENGTH_SHORT).show()
                                }else{

                                    if(seleccionFechaAct.value == null){

                                        viewModel.actualizarMiembros(miembrosId.toInt(), nombreAct, apellidoAct, miembroActualizar!!.fecha_inscripcion)
                                        mostrarVentana = false

                                    }else{

                                        val fecha: Long = seleccionFechaAct.value!! //para desreferenciar
                                        viewModel.actualizarMiembros(miembrosId.toInt(), nombreAct, apellidoAct, fecha)
                                        mostrarVentana = false
                                        seleccionFechaAct.value = null

                                    }

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
                                seleccionFechaAct.value = null

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
                    navController.navigate("screen_a")
                },
                    modifier = Modifier.width(200.dp)

            ) {
                Text(
                    text = "Autores"
                )
            }



        }


    }

}