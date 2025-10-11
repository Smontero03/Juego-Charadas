package com.example.charadas


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.charadas.ui.theme.CharadasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
                CharadasTheme {
                    Scaffold(
                        modifier = Modifier.fillMaxSize()
                    ) { innerPadding ->
                       // Inicio(modifier = Modifier.padding(innerPadding))
                        RotationScreen(modifier = Modifier.padding(innerPadding))
                    }

                }
        }
    }
}

@Composable
fun Inicio(modifier: Modifier){
    Column(Modifier
        .fillMaxSize()
        .background(Color(0xFF6DBDF2))
        .padding(10.dp)) {  Text("CHARADAS", modifier = Modifier
        .align(Alignment.CenterHorizontally)
        .padding(top = 150.dp), fontSize = 40.sp, color = Color(0xFFFFFFFF))
        Spacer(modifier= Modifier.height(325.dp))

        Button(onClick = {},modifier= Modifier.align(Alignment.CenterHorizontally).height(80.dp).width(250.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xff8879f7), contentColor = Color.White)) {
            Text("Jugar", fontSize = 30.sp)
        }

    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Inicio(modifier = Modifier)
}