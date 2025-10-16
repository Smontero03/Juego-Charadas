package com.example.charadas

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.charadas.ui.theme.CharadasTheme
import kotlinx.coroutines.CoroutineStart




@Composable
fun GameModeMenu(
    modifier: Modifier,
    onCategorySelected: (String) -> Unit,
    onStartClick: () -> Unit
) {
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val activity = context as? Activity

    DisposableEffect(Unit) {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        onDispose {

            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF6DBDF2), Color(0xFF1565C0))
                )
            )
            .padding(start = 16.dp, end = 16.dp, bottom = 160.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Seleccione la categoria",
            modifier = Modifier.padding(top = 60.dp, bottom = 100.dp),
            color = Color.White,
            fontSize = 30.sp
        )

        CategoryGrid(onCategorySelected = {
            selectedCategory = it
            onCategorySelected(it)
        })

        Spacer(modifier = Modifier.weight(1f))

        StartButton(onClick = onStartClick)
    }
}
@Composable
fun CategoryItem(name: String,imageRes: Int?,onClick:()-> Unit){
    val shape= RoundedCornerShape(12.dp)

    Card (
        modifier = Modifier
            .aspectRatio(1f)
            .fillMaxWidth()
            .clickable(onClick=onClick)
            .border(2.dp, color = Color(0xff775cd6),shape),
        shape=shape,
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ){
        Column (modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top)
        {


            Text(
                text = name,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                ,Color.Black
            )
            imageRes?.let {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = name,
                    modifier = Modifier
                        .size(80.dp)
                        .padding(bottom = 1.dp)
                )
            }


        }

    }
}


@Composable
fun CategoryGrid(onCategorySelected:(String)-> Unit){
    val categoriesToShow= RepositorioPalabras.categoryNames
    val categoryImages = mapOf(
        "Animales" to R.drawable.loro,
        "Peliculas" to R.drawable.cine,
        "ArtistasMusicales" to R.drawable.microfono,
        "Paises" to R.drawable.paises
    )

    LazyVerticalGrid(columns= GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(categoriesToShow) { categoryName ->
            CategoryItem(
                name = categoryName,
                imageRes = categoryImages[categoryName],
                onClick = { onCategorySelected(categoryName) }
            )
        }

    }
}
@Composable
fun StartButton(onClick: () -> Unit){
    Button(onClick=onClick,
        modifier= Modifier
            .fillMaxWidth()

            .height(56.dp),
        shape=RoundedCornerShape(50),
        colors= ButtonDefaults.buttonColors(
            contentColor = Color.White, containerColor = Color(0xff775cd6)

        )


    ){
        Text("Iniciar", modifier = Modifier , fontSize = 20.sp)

    }

}


