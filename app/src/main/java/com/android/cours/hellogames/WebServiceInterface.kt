package com.android.cours.hellogames

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WebServiceInterface {
    @GET("game/list")
    fun listGames(): Call<List<Game>>

    @GET("game/details")
    fun detail(@Query("game_id") id: Int): Call<Game>
}