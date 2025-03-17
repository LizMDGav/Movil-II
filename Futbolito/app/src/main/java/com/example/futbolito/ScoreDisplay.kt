package com.example.futbolito

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ScoreDisplay(topScore: Int, bottomScore: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black) // Fondo negro para todo el espacio del Box
    ) {
        Surface(
            color = Color.Black, // Fondo negro para el Surface
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 37.dp, bottom = 16.dp) // Padding superior e inferior
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically, // Alinea los elementos verticalmente al centro
                modifier = Modifier.padding(16.dp) // Padding interior del Row
            ) {
                // Logotipo del Barcelona (izquierda)
                Image(
                    painter = painterResource(id = R.drawable.barcelona_logo), // Cambia "barcelona_logo" por el nombre de tu recurso
                    contentDescription = "Barcelona Logo",
                    modifier = Modifier.size(40.dp) // Tamaño del logotipo
                )

                // Texto del marcador
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.White)) {
                            append(" FCB    ")
                        }
                        withStyle(style = SpanStyle(color = Color.White)) {
                            append(topScore.toString())
                        }
                        withStyle(style = SpanStyle(color = Color.White)) {
                            append(" - ")
                        }
                        withStyle(style = SpanStyle(color = Color.White)) {
                            append(bottomScore.toString())
                        }
                        withStyle(style = SpanStyle(color = Color.White)) {
                            append("    RM ")
                        }
                    },
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f) // Ocupa el espacio restante
                )

                // Logotipo del Real Madrid (derecha)
                Image(
                    painter = painterResource(id = R.drawable.realmadrid_logo), // Cambia "real_madrid_logo" por el nombre de tu recurso
                    contentDescription = "Real Madrid Logo",
                    modifier = Modifier.size(40.dp) // Tamaño del logotipo
                )
            }
        }
    }
}