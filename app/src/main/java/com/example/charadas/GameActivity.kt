package com.example.charadas


import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.charadas.ui.theme.CharadasTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun Gameplay(modifier: Modifier,
             categoria: String,
             navController: NavController
){  var palabra by remember { mutableStateOf("") }
    var showPauseMenu by remember { mutableStateOf(false) }
    var tiempoRestante by remember { mutableStateOf(60) }
    var equipoActual by remember { mutableStateOf(1) } // 1 o 2
    var aciertosEquipo1 by remember { mutableStateOf(0) }
    var aciertosEquipo2 by remember { mutableStateOf(0) }
    var rondasCompletadas by remember { mutableStateOf(0) }
    val listaPalabras = remember { RepositorioPalabras.ObtenerPalabra(categoria).toMutableList() }
    val context = LocalContext.current

    // --- SENSOR ---
    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    val scope = rememberCoroutineScope()


    LaunchedEffect(categoria) {
        val list= RepositorioPalabras.ObtenerPalabra(categoria)
        palabra=if (list.isNotEmpty()) list.random() else "Sin palabra"
    }

    DisposableEffect(Unit) {
        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                val x = event.values[0] // Eje X: detecta inclinación arriba/abajo en horizontal

                // Cuando el dispositivo se inclina hacia arriba marca acierto
                if (x < 2f) {
                    scope.launch {
                        // Cambiar palabra
                        val nueva = listaPalabras.random()
                        palabra = nueva
                        // Aumentar aciertos
                        if (equipoActual == 1) {
                            aciertosEquipo1++
                        } else {
                            aciertosEquipo2++
                        }
                        // Pequeña pausa para evitar múltiples detecciones seguidas
                        delay(5000)
                    }
                    // Cuando el dispositivo se inclina hacia abajo cambia de palabra
                }else if(x < -2f){
                    scope.launch {
                        val nueva = listaPalabras.random()
                        palabra = nueva
                        delay(5000)
                    }

                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL)

        onDispose {
            sensorManager.unregisterListener(listener)
        }
    }

    LaunchedEffect(tiempoRestante, showPauseMenu) {
        if (!showPauseMenu && tiempoRestante > 0) {
            delay(1000L)
            tiempoRestante--
        }

        if (tiempoRestante == 0) {
            val mensaje = "¡Tiempo terminado para el equipo $equipoActual!"
            Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()

            rondasCompletadas++

            if (rondasCompletadas == 2) {

                navController.navigate("resultados/$aciertosEquipo1/$aciertosEquipo2") {
                    popUpTo("menu") { inclusive = false }
                }
            } else {

                equipoActual = if (equipoActual == 1) 2 else 1
                tiempoRestante = 60
                palabra = listaPalabras.random()
            }
        }
    }

    val tiempoFormateado = String.format("%02d:%02d", tiempoRestante / 60, tiempoRestante % 60)

    Box(modifier=modifier.fillMaxSize()
        .background(Brush.verticalGradient( colors = listOf(Color(0xFF6DBDF2), Color(0xFF1565C0))))
        .padding(16.dp)
    ){
        IconButton(
            onClick = { showPauseMenu = true },
            modifier = Modifier
                .align(Alignment.TopStart)
                .background(color = Color.Gray, shape = CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Pausa",
                tint = Color.Black
            )
        }

        Text(text = tiempoFormateado, fontSize = 28.sp, color = Color.White,modifier= Modifier.align(Alignment.TopCenter))
        Text(text = palabra, fontSize = 36.sp, fontWeight = FontWeight.Bold, color = Color.White,modifier= Modifier.align(
            Alignment.Center))
    }

    if (showPauseMenu) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xAA000000))
                .clickable { } // Evita clics fuera del menú
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = { showPauseMenu = false }) {
                    Text("Reanudar")
                }
                Spacer(Modifier.height(8.dp))
                Button(onClick = {
                    // Reiniciar el juego (reiniciar contador, palabra, etc.)
                    showPauseMenu = false
                    tiempoRestante = 60
                    //aciertos = 0
                    palabra = listaPalabras.random()
                }) {
                    Text("Reiniciar")
                }
                Spacer(Modifier.height(8.dp))
                Button(onClick = {
                    //  Vuelve al menú principal
                    navController.navigate("menu") {
                        popUpTo("game/$categoria") { inclusive = true }
                    }
                }) {
                    Text("Volver al menú")
                }
            }
        }
    }


}
/*
@Preview(showBackground = true, widthDp = 700, heightDp = 320)
@Composable
fun GreetingPreview3() {
    CharadasTheme {
Gameplay(modifier = Modifier)
    }
}*/