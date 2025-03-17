package com.example.futbolito

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import dev.ricknout.composesensors.accelerometer.rememberAccelerometerSensorValueAsState

@Composable
fun GameLogic(topScore: Int, bottomScore: Int, onScoreUpdate: (Int, Int) -> Unit) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val width = constraints.maxWidth.toFloat()
        val height = constraints.maxHeight.toFloat()
        var center by remember { mutableStateOf(Offset(width / 2, height / 2)) }
        val orientation = LocalConfiguration.current.orientation
        val radius = with(LocalDensity.current) { 10.dp.toPx() }

        // Obtiene los valores del sensor de acelerómetro
        val sensorValue by rememberAccelerometerSensorValueAsState()
        val (x, y, z) = sensorValue.value

        // Calcula la nueva posición del balón basado en la orientación y valores del acelerómetro
        center = if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Offset(
                x = (center.x - x).coerceIn(radius, width - radius),
                y = (center.y + y).coerceIn(radius, height - radius),
            )
        } else {
            Offset(
                x = (center.x + y).coerceIn(radius, width - radius),
                y = (center.y + x).coerceIn(radius, height - radius),
            )
        }

        // Verifica si el balón toca los límites superior o inferior del centro y actualiza el marcador
        when {
            center.y - radius <= 0 && (width / 2) < (center.x) + 100 && (width / 2) > center.x - 100 -> {
                onScoreUpdate(topScore + 1, bottomScore)
                center = Offset(width / 2, height / 2) // Reposiciona el balón en el centro
            }
            center.y + radius >= height && (width / 2) < (center.x) + 100 && (width / 2) > center.x - 100 -> {
                onScoreUpdate(topScore, bottomScore + 1)
                center = Offset(width / 2, height / 2) // Reposiciona el balón en el centro
            }
        }

        // Imagen de fondo y balón
        val image = ImageBitmap.imageResource(id = R.drawable.canchasoccer)
        val ballImage = ImageBitmap.imageResource(id = R.drawable.balon)

        Canvas(modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight(0.85f)
        ) {
            drawImage(
                image = image,
                dstSize = IntSize(width.toInt(), height.toInt())
            )
            drawImage(
                image = ballImage,
                dstSize = IntSize((radius * 2).toInt(), (radius * 2).toInt()), // Tamaño del balón
                dstOffset = IntOffset(
                    (center.x - radius).toInt(), // Posición X del balón
                    (center.y - radius).toInt()  // Posición Y del balón
                )
            )

        }
    }
}