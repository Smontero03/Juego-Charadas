package com.example.charadas

import android.R.attr.orientation
import android.content.Intent
import android.content.res.Configuration
import android.graphics.ImageDecoder
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.charadas.ui.theme.CharadasTheme
import kotlinx.coroutines.sync.Mutex



@Composable
fun RotationScreen(modifier: Modifier,categoria: String?,navController: NavController){
    val context= LocalContext.current
    val configuration = LocalConfiguration.current
    val  orientation = configuration.orientation
    LaunchedEffect(orientation) {
        if (orientation == Configuration.ORIENTATION_LANDSCAPE && categoria != null) {
            navController.navigate("game/$categoria") {
                popUpTo("rotation/$categoria") { inclusive = true }
            }
        }
    }
    Column(modifier.fillMaxSize().background(Brush.verticalGradient( colors = listOf(Color(0xFF6DBDF2), Color(0xFF1565C0)))),
    verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {



        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(R.drawable.icono_girar)
                .decoderFactory(
                    if (android.os.Build.VERSION.SDK_INT>=28)
                        ImageDecoderDecoder.Factory()
                    else
                        GifDecoder.Factory()
                )
                .build(),
            contentDescription = "Gif de rotacion",
            modifier= Modifier.size(200.dp)
        )
        Spacer(modifier=Modifier.height(50.dp))
        Text("Gire su dispositivo para iniciar")
    }


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    CharadasTheme {
        //RotationScreen(modifier = Modifier)
    }
}