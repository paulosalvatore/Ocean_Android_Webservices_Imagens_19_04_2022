package com.oceanbrasil.ocean_android_webservices_imagens_19_04_2022

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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

        call.enqueue(object : Callback<PokemonApiResult> {
            override fun onResponse(
                call: Call<PokemonApiResult>,
                response: Response<PokemonApiResult>
            ) {
                if (response.isSuccessful) {
                    val pokemonApiResult = response.body()

                    pokemonApiResult?.let {
                        Toast.makeText(this@MainActivity, "Pokemon carregados com sucesso.", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<PokemonApiResult>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Falha na requisição.", Toast.LENGTH_LONG).show()
            }
        })
    }
}