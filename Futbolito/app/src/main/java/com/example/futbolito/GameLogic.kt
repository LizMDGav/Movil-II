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
        var velocity by remember { mutableStateOf(Offset.Zero) }

        val orientation = LocalConfiguration.current.orientation
        val radius = with(LocalDensity.current) { 10.dp.toPx() }
        val sensorValue by rememberAccelerometerSensorValueAsState()
        val (x, y, z) = sensorValue.value

        val accelerationFactor = 0.05f

        velocity = if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Offset(
                x = (velocity.x - x * accelerationFactor),
                y = (velocity.y + y * accelerationFactor)
            )
        } else {
            Offset(
                x = (velocity.x + y * accelerationFactor),
                y = (velocity.y + x * accelerationFactor)
            )
        }

        center = Offset(
            x = (center.x + velocity.x).coerceIn(radius, width - radius),
            y = (center.y + velocity.y).coerceIn(radius, height - radius)
        )

        // Revisa rebote lateral
        if (center.x - radius <= 0 || center.x + radius >= width) {
            velocity = velocity.copy(x = -velocity.x * 0.8f)
        }

        // Revisa rebote vertical (fuera del área del gol)
        if ((center.y - radius <= 0 && (center.x !in (width/2 - 100f)..(width/2 + 100f))) ||
            (center.y + radius >= height && (center.x !in (width/2 - 100f)..(width/2 + 100f)))) {
            velocity = velocity.copy(y = -velocity.y * 0.8f)
        }

        // Verifica gol (área central superior/inferior)
        when {
            center.y - radius <= 0 && center.x in (width/2 - 100f)..(width/2 + 100f) -> {
                onScoreUpdate(topScore + 1, bottomScore)
                center = Offset(width / 2, height / 2)
                velocity = Offset.Zero
            }
            center.y + radius >= height && center.x in (width/2 - 100f)..(width/2 + 100f) -> {
                onScoreUpdate(topScore, bottomScore + 1)
                center = Offset(width / 2, height / 2)
                velocity = Offset.Zero
            }
        }

        // Dibujar imágenes del fondo y balón
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
                dstSize = IntSize((radius * 2).toInt(), (radius * 2).toInt()),
                dstOffset = IntOffset(
                    (center.x - radius).toInt(),
                    (center.y - radius).toInt()
                )
            )
        }
    }
}