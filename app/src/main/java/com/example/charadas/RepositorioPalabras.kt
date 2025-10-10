package com.example.charadas

object RepositorioPalabras {
    val animales = listOf(
        "León", "Elefante", "Perro", "Gato", "Tigre",
        "Águila", "Serpiente", "Caballo", "Conejo", "Jirafa",
        "Rana", "Delfín", "Oso", "Cebra", "Murciélago"
    )
    val peliculas = listOf(
        "Titanic", "Avatar", "Inception", "Matrix", "Frozen",
        "Coco", "Jurassic Park", "El Rey León", "Gladiador", "Avengers",
        "Harry Potter", "Shrek", "Toy Story", "Star Wars", "Rapunzel"
    )
    val artistasMusicales = listOf(
        "Shakira", "Bad Bunny", "Maluma", "Karol G", "J Balvin",
        "Daddy Yankee", "Ozuna", "Sebastián Yatra", "Camilo", "Luis Fonsi",
        "Rihanna", "Michael Jackson", "Adele", "Ed Sheeran", "Beyoncé"
    )
    val paises = listOf(
        "Colombia", "México", "Argentina", "España", "Francia",
        "Italia", "Brasil", "Japón", "Canadá", "Chile",
        "Alemania", "China", "India", "Perú", "Australia"
    )

    val categoryNames = listOf("Animales", "Películas", "Artistas Musicales", "Países")

    fun ObtenerPalabra(categoria: String):List<String>{
        return when(categoria){
            "Animales"->animales
            "Peliculas"->peliculas
            "ArtistasMusicales"->artistasMusicales
            "Paises"->paises
            else -> listOf()
        }


    }
}