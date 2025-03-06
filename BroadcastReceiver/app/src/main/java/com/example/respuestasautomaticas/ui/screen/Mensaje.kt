package com.example.respuestasautomaticas.ui.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColor
import com.example.respuestasautomaticas.R
import com.example.respuestasautomaticas.ViewModel.ViewModelMensaje
import com.example.respuestasautomaticas.ui.Telefono

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Mensaje(viewModel: ViewModelMensaje){
    val context = LocalContext.current




    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color(0xFF87CEEB))
            ) {
                Text(
                    text = "Contestador personalizado",
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 40.dp)){
            Column(
                modifier = Modifier
                    .wrapContentHeight(align = Alignment.CenterVertically) // Asegura que la altura del Column se ajuste al contenido y esté centrado verticalmente
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = viewModel.Numero,
                    onValueChange ={viewModel.updateNumero(it)},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    label = { Text(text = "Numero de Telefono")},
                    singleLine = true,
                    modifier = Modifier
                        .size(350.dp, 70.dp)
                        .background(Color.Transparent)
                )
                Divider(modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 13.dp, 0.dp, 8.dp)
                )
                OutlinedTextField(
                    value = viewModel.Mensaje,
                    onValueChange ={viewModel.updateMensaje(it)},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    label = { Text(text = "Mensaje de respuesta")},
                    placeholder = { Text(text = "Ingrese el mensaje de respuesta")},
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        Telefono.Telefono.mensaje = viewModel.Mensaje
                        Telefono.Telefono.numero = viewModel.Numero
                        Toast.makeText(context, "Información guardada correctamente", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 40.dp) // Asegura que el botón ocupe todo el ancho
                ) {
                    Text(
                        text = "Guardar",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    )
                }

            }
        }
    }
}
