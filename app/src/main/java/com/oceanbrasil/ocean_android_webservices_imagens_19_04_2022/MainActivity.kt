package com.oceanbrasil.ocean_android_webservices_imagens_19_04_2022

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicialização do retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Inicializar o Service
        val service = retrofit.create(PokemonService::class.java)

        // Fazer a requisição
        val call = service.listarPokemon()

        val tvResultado = findViewById<TextView>(R.id.tvResultado)

        call.enqueue(object : Callback<PokemonApiResult> {
            override fun onResponse(
                call: Call<PokemonApiResult>,
                response: Response<PokemonApiResult>
            ) {
                if (response.isSuccessful) {
                    val pokemonApiResult = response.body()

                    pokemonApiResult?.let {
                        Toast.makeText(this@MainActivity, "Pokemon carregados com sucesso.", Toast.LENGTH_LONG).show()

                        tvResultado.text = ""

                        it.results.forEach { pokemon ->
                            tvResultado.append(pokemon.name + "\n")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<PokemonApiResult>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Falha na requisição.", Toast.LENGTH_LONG).show()
            }
        })

        // Carregamento de imagem (faremos o carregamento de cada pokémon na aula de RecyclerView)

        val ivPokemon = findViewById<ImageView>(R.id.ivPokemon)

        val urlImagem = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/001.png"
        Glide.with(this).load(urlImagem).into(ivPokemon)
    }
}