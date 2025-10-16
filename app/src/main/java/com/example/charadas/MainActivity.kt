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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
                       AppNavigator(modifier = Modifier.padding(innerPadding))

                    }

                }
        }
    }
}

@Composable
fun Inicio(modifier: Modifier,onPlayClick:()-> Unit){
    Column(Modifier
        .fillMaxSize()
        .background(Color(0xFF6DBDF2))
        .padding(10.dp)) {  Text("CHARADAS", modifier = Modifier
        .align(Alignment.CenterHorizontally)
        .padding(top = 150.dp), fontSize = 40.sp, color = Color(0xFFFFFFFF))
        Spacer(modifier= Modifier.height(325.dp))

        Button(onClick = onPlayClick,modifier= Modifier.align(Alignment.CenterHorizontally).height(60.dp).width(250.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xff8879f7), contentColor = Color.White)) {
            Text("Jugar", fontSize = 30.sp)
        }

    }

}

@Composable
fun AppNavigator(modifier: Modifier) {
    val navController = rememberNavController()
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    NavHost(navController = navController, startDestination = "Inicio") {

        //  Pantalla de inicio
        composable("Inicio") {
            Inicio(
                modifier = Modifier.fillMaxSize(),
                onPlayClick = { navController.navigate("menu") }
            )
        }

        //  Menú principal
        composable("menu") {
            GameModeMenu(
                modifier = Modifier.fillMaxSize(),
                onCategorySelected = { category ->
                    selectedCategory = category
                },
                onStartClick = {
                    selectedCategory?.let {
                        navController.navigate("rotation/$it")
                    }
                }
            )
        }

        // Pantalla de rotación
        composable(
            route = "rotation/{categoria}",
            arguments = listOf(navArgument("categoria") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoria = backStackEntry.arguments?.getString("categoria")
            RotationScreen(
                modifier = Modifier.fillMaxSize(),
                categoria = categoria,
                navController = navController
            )
        }

        //  Pantalla de juego
        composable(
            route = "game/{categoria}",
            arguments = listOf(navArgument("categoria") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoria = backStackEntry.arguments?.getString("categoria") ?: "Desconocido"
            Gameplay(
                modifier = Modifier.fillMaxSize(),
                categoria = categoria,
                navController = navController
            )
        }

        // pantalla de resultados
        composable(
            route = "resultados/{puntos1}/{puntos2}",
            arguments = listOf(
                navArgument("puntos1") { type = NavType.IntType },
                navArgument("puntos2") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val puntos1 = backStackEntry.arguments?.getInt("puntos1") ?: 0
            val puntos2 = backStackEntry.arguments?.getInt("puntos2") ?: 0

            ResultsScreen(
                aciertosEquipo1 = puntos1,
                aciertosEquipo2 = puntos2,
                onVolverMenu = {
                    navController.navigate("menu") {
                        // Evita duplicar pantallas en la pila
                        popUpTo("Inicio") { inclusive = false }
                    }
                }
            )
        }
    }
}

