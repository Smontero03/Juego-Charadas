package com.example.charadas

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.charadas.ui.theme.CharadasTheme
import kotlinx.coroutines.sync.Mutex

class RotateActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val categoria=intent.getStringExtra("Categoria")
        val orientation = resources.configuration.orientation

        if(orientation== Configuration.ORIENTATION_LANDSCAPE){
            val intent= Intent(this,GameActivity::class.java)
            intent.putExtra("categoria",categoria)
            startActivity(intent)
            finish()
            return

        }

        setContent {
            CharadasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                RotationScreen(modifier = Modifier.padding(innerPadding), categoria = categoria)
                }
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val categoria=intent.getStringExtra("categoria")
        if (newConfig.orientation== Configuration.ORIENTATION_LANDSCAPE){
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("categoria",categoria)
            startActivity(intent)
            finish()

        }
    }
}

@Composable
fun RotationScreen(modifier: Modifier,categoria: String?){
    val context= LocalContext.current
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