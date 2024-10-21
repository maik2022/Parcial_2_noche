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
import com.example.parcial_2_noche.InnerJoin.prestamosDetalles
import java.text.DateFormat
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun screen_d(navController: NavController, viewModel: mainViewModel){

    var expanded by remember { mutableStateOf(false) }
    var selectorTexto by remember { mutableStateOf("") }
    var selectorId by remember { mutableStateOf("") }

    var expanded2 by remember { mutableStateOf(false) }
    var selectorTexto2 by remember { mutableStateOf("") }
    var selectorId2 by remember { mutableStateOf("") }

    var expanded3 by remember { mutableStateOf(false) }
    var selectorTexto3 by remember { mutableStateOf("") }
    var selectorId3 by remember { mutableStateOf("") }

    var expanded4 by remember { mutableStateOf(false) }
    var selectorTexto4 by remember { mutableStateOf("") }
    var selectorId4 by remember { mutableStateOf("") }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val mes = calendar.get(Calendar.MONTH)
    val dia = calendar.get(Calendar.DAY_OF_MONTH)
    //Estado para manejar la fecha seleccionada
    val seleccionPrestamo = remember { mutableStateOf<Long?>(null)}
    val seleccionDevolucion = remember { mutableStateOf<Long?>(null)}
    val seleccionDevAct = remember { mutableStateOf<Long?>(null)}

    var prestamoActualizar by remember { mutableStateOf<prestamosDetalles?>(null) } //Puede ser nulo o autor
    var mostrarVentana by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.listarLibros()
        viewModel.listarMiembros()
        viewModel.listarPrestamos()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = "CREACIÓN DE PRESTAMOS",
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
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
                    label = {Text("Seleccione Libro")},
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
                    viewModel.listaLibros.forEach { libro ->
                        DropdownMenuItem(
                            text = { Text(libro.titulo) },
                            onClick = {
                                selectorTexto = libro.titulo
                                selectorId = libro.libro_id.toString()
                                expanded = false
                            })
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            ExposedDropdownMenuBox(
                expanded = expanded2,
                onExpandedChange = {expanded2 = !expanded2},
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                TextField(
                    value = selectorTexto2,
                    onValueChange = {},
                    readOnly = true,
                    label = {Text("Seleccione Miembro")},
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
                    viewModel.listamiembros.forEach { miembro ->
                        DropdownMenuItem(
                            text = { Text(miembro.nombre) },
                            onClick = {
                                selectorTexto2 = miembro.nombre
                                selectorId2 = miembro.miembro_id.toString()
                                expanded2 = false
                            })
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    DatePickerDialog(context, { _, seleccionYear, seleccionMes, seleccionDia ->
                        //Convertir la fecha a long

                        val cal = Calendar.getInstance()
                        cal.set(seleccionYear, seleccionMes, seleccionDia)
                        seleccionPrestamo.value= cal.timeInMillis

                    }, year, mes, dia).show()
                },
                    modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Seleccionar Fecha de Prestamo")
            }

            seleccionPrestamo.value?.let {
                TextField(
                    value = DateFormat.getDateInstance().format(Date(it)),
                    onValueChange = {},
                    label = {Text("Fecha De Prestamo")},
                    enabled = false,
                    modifier = Modifier.fillMaxWidth()
                )

                //Text(text = "Fecha: ${DateFormat.getDateInstance().format(Date(it))}")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    DatePickerDialog(context, { _, seleccionYear, seleccionMes, seleccionDia ->
                        //Convertir la fecha a long

                        val cal = Calendar.getInstance()
                        cal.set(seleccionYear, seleccionMes, seleccionDia)
                        seleccionDevolucion.value= cal.timeInMillis

                    }, year, mes, dia).show()
                },
                    modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Seleccionar Fecha de Devolución")
            }

            seleccionDevolucion.value?.let {
                TextField(
                    value = DateFormat.getDateInstance().format(Date(it)),
                    onValueChange = {},
                    label = {Text("Fecha De Devolución")},
                    enabled = false,
                    modifier = Modifier.fillMaxWidth()
                )

                //Text(text = "Fecha: ${DateFormat.getDateInstance().format(Date(it))}")
            }

            Button(
                onClick = {

                    if(selectorId.isEmpty() || selectorId2.isEmpty() || seleccionPrestamo.value == null || seleccionDevolucion.value == null){

                        Toast.makeText(context, "Completar todos los campos", Toast.LENGTH_SHORT).show()

                    }else{

                        val fechaPrestamo: Long = seleccionPrestamo.value!!
                        val fechaDevolucion: Long = seleccionDevolucion.value!!

                        viewModel.insertarPrestamo(selectorId.toInt(), selectorId2.toInt(), fechaPrestamo, fechaDevolucion)

                        selectorId = ""
                        selectorTexto = ""
                        selectorId2 = ""
                        selectorTexto2 = ""
                        seleccionPrestamo.value = null
                        seleccionDevolucion.value = null

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
                text = "LISTA DE PRESTAMOS",
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            viewModel.listaPrestamos.forEach {prestamos ->

                val fechaPres = DateFormat.getDateInstance().format(Date(prestamos.fecha_prestamo))
                val fechaDev = DateFormat.getDateInstance().format(Date(prestamos.fecha_devolucion))

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
                        text = "ID: ${prestamos.prestamo_id}\nLibro: ${prestamos.libro_nombre}\nMiembro: ${prestamos.miembro_nombre}\nFecha Prestamo: ${fechaPres}\n" +
                                "Fecha Devolución: ${fechaDev}",
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
                                viewModel.eliminarPrestamo(prestamos.prestamo_id)
                            }

                        ) {
                            Text(
                                text = "Eliminar"
                            )
                        }

                        Button(
                            onClick = {
                                prestamoActualizar = prestamos // guardar la info
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

            if (mostrarVentana && prestamoActualizar != null){

                var prestamoId by remember { mutableStateOf(prestamoActualizar?.prestamo_id ?.toString() ?: "") }
                var libroAct by remember { mutableStateOf(prestamoActualizar?.libro_nombre.toString() ?: "") }
                var miembroAct by remember { mutableStateOf(prestamoActualizar?.miembro_nombre.toString() ?: "") }
                var fechaPresAct = DateFormat.getDateInstance().format(Date(prestamoActualizar!!.fecha_prestamo))
                var fechaDevAct = DateFormat.getDateInstance().format(Date(prestamoActualizar!!.fecha_devolucion))

                AlertDialog(
                    onDismissRequest = { mostrarVentana = false },
                    title = {
                        Text(
                            text = "Actualizar Prestamo",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )

                    },
                    text = {
                        Column {

                            ExposedDropdownMenuBox(
                                expanded = expanded3,
                                onExpandedChange = {expanded3 = !expanded3},
                                modifier = Modifier
                                    .fillMaxWidth()

                            ) {
                                TextField(
                                    value = libroAct,
                                    onValueChange = {},
                                    readOnly = true,
                                    label = {Text("Seleccione Libro")},
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded3)
                                    },
                                    modifier = Modifier
                                        .menuAnchor()
                                        .fillMaxWidth()
                                )

                                ExposedDropdownMenu(
                                    expanded = expanded3,
                                    onDismissRequest = { expanded3 = false }
                                ) {
                                    viewModel.listaLibros.forEach { libros ->

                                        DropdownMenuItem(
                                            text = { Text(libros.titulo) },
                                            onClick = {
                                                libroAct = libros.titulo
                                                selectorId3 = libros.libro_id.toString()
                                                expanded3 = false
                                            })
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            ExposedDropdownMenuBox(
                                expanded = expanded4,
                                onExpandedChange = {expanded4 = !expanded4},
                                modifier = Modifier
                                    .fillMaxWidth()

                            ) {
                                TextField(
                                    value = miembroAct,
                                    onValueChange = {},
                                    readOnly = true,
                                    label = {Text("Seleccione Miembro")},
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded4)
                                    },
                                    modifier = Modifier
                                        .menuAnchor()
                                        .fillMaxWidth()
                                )

                                ExposedDropdownMenu(
                                    expanded = expanded4,
                                    onDismissRequest = { expanded4 = false }
                                ) {
                                    viewModel.listamiembros.forEach { miembros ->

                                        DropdownMenuItem(
                                            text = { Text(miembros.nombre) },
                                            onClick = {
                                                miembroAct = miembros.nombre
                                                selectorId4 = miembros.miembro_id.toString()
                                                expanded4 = false
                                            })
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // ACTUALIZAR SOLO LA FECHA DE DEVOLUCION.

                            TextField(
                                value = fechaPresAct,
                                onValueChange = {},
                                label = {Text("Fecha Prestamo")},
                                enabled = false,
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Button(
                                onClick = {
                                    DatePickerDialog(context, { _, seleccionYear, seleccionMes, seleccionDia ->
                                        //Convertir la fecha a long

                                        val cal = Calendar.getInstance()
                                        cal.set(seleccionYear, seleccionMes, seleccionDia)
                                        seleccionDevAct.value= cal.timeInMillis

                                    }, year, mes, dia).show()
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(text = "Seleccionar Fecha Devolución")
                            }

                            if (seleccionDevAct.value == null){
                                TextField(
                                    value = fechaDevAct,
                                    onValueChange = {},
                                    label = {Text("Fecha Inscripción")},
                                    enabled = false,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }else{
                                seleccionDevAct.value?.let {
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

                                if(selectorId3.isEmpty()){ // para evitar que quede pegado ademas por si no actualiza saber que opcion hizo.

                                    selectorId3 = viewModel.listaLibros.find {it.titulo == libroAct }?.libro_id.toString()

                                }

                                if(selectorId4.isEmpty()){

                                    selectorId4 = viewModel.listamiembros.find {it.nombre == miembroAct }?.miembro_id.toString()

                                }

                                if(seleccionDevAct.value == null){

                                    viewModel.actualizarPrestamo(prestamoId.toInt(),selectorId3.toInt(), selectorId4.toInt(), prestamoActualizar!!.fecha_prestamo, prestamoActualizar!!.fecha_devolucion)
                                    mostrarVentana = false

                                    selectorId3 = ""
                                    selectorId4 = ""

                                }else{

                                    val fecha: Long = seleccionDevAct.value!! //para desreferenciar
                                    viewModel.actualizarPrestamo(prestamoId.toInt(),selectorId3.toInt(), selectorId4.toInt(), prestamoActualizar!!.fecha_prestamo, fecha)
                                    mostrarVentana = false
                                    seleccionDevAct.value = null

                                    selectorId3 = ""
                                    selectorId4 = ""

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
                                seleccionDevAct.value = null
                                selectorId3 = ""
                                selectorId4 = ""

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
                        navController.navigate("screen_b")
                    },
                        modifier = Modifier.width(150.dp)

                ) {
                    Text(
                        text = "Libros"
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