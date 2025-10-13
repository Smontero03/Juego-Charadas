package com.example.charadas

import android.os.Bundle
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.charadas.ui.theme.CharadasTheme

class GameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CharadasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Gameplay(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Gameplay(modifier: Modifier,onPauseClick:()-> Unit={},
word:String="Grillo", time: String="00:00"
){
    var showPauseMenu by remember { mutableStateOf(false) }

    Box(modifier=modifier.fillMaxSize()
        .background(Brush.verticalGradient( colors = listOf(Color(0xFF6DBDF2), Color(0xFF1565C0))))
        .padding(16.dp)
    ){
        IconButton(onPauseClick, modifier = Modifier
            .align(Alignment.TopStart)
            .background(color = Color.Gray,shape= CircleShape))
        {
            Icon(imageVector = Icons.Default.Menu, contentDescription = "Pausa", tint = Color.Black)

        }

        Text(text = time, fontSize = 28.sp, color = Color.White,modifier= Modifier.align(Alignment.TopCenter))
        Text(text = word, fontSize = 36.sp, fontWeight = FontWeight.Bold, color = Color.White,modifier= Modifier.align(
            Alignment.Center))
    }

    if (showPauseMenu){
        Box(    modifier = Modifier
            .fillMaxSize()
            .background(Color(0xAA000000)) //
            .clickable { } ){
            Column(modifier= Modifier.align(Alignment.Center).background(Color.White, shape = RoundedCornerShape(16.dp))
                .padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = {showPauseMenu=false}) {
                    Text("Reanudar")
                }
                Spacer(Modifier.height(8.dp))
                Button(onClick = {}) {
                    Text("Reiniciar")
                }
                Button(onClick = {}) {
                    Text("Volver al menu")

                }

            }

        }

    }


}

@Preview(showBackground = true, widthDp = 700, heightDp = 320)
@Composable
fun GreetingPreview3() {
    CharadasTheme {
Gameplay(modifier = Modifier)
    }
}