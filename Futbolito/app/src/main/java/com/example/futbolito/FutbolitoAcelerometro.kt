package com.example.futbolito

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.ricknout.composesensors.accelerometer.isAccelerometerSensorAvailable
import dev.ricknout.composesensors.accelerometer.rememberAccelerometerSensorValueAsState

@Composable
fun FutbolitoAcelerometro()
{
    var topScore by remember { mutableStateOf(0) }
    var bottomScore by remember { mutableStateOf(0) }

    if (isAccelerometerSensorAvailable()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ScoreDisplay(topScore, bottomScore)

            // Lógica del juego
            GameLogic(topScore, bottomScore) { newTopScore, newBottomScore ->
                topScore = newTopScore
                bottomScore = newBottomScore
            }
        }
    } else {
        Text(
            text = "Sensor de acelerómetro no disponible",
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp)
        )
    }
}