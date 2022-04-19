package com.oceanbrasil.ocean_android_webservices_imagens_19_04_2022

import retrofit2.Call
import retrofit2.http.GET

interface PokemonService {
    @GET("pokemon")
    fun listarPokemon(): Call<PokemonApiResult>
}