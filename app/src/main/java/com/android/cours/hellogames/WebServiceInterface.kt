package com.android.cours.hellogames

import retrofit2.Call
import retrofit2.http.GET

interface WebServiceInterface {
    @GET("game/list")
    fun listGames(): Call<List<Game>>
}