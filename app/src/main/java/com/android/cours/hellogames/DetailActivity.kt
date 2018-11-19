package com.android.cours.hellogames

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.cours.hellogames.R.id.name
import com.android.cours.hellogames.R.id.type
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.view.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailActivity : AppCompatActivity() {

    var game = Game()

    // extract data from the intent

    // The base URL where the WebService is located
    val baseURL = "https://androidlessonsapi.herokuapp.com/api/"
    // Use GSON library to create our JSON parser
    val jsonConverter = GsonConverterFactory.create(GsonBuilder().create()) // Create a Retrofit client object targeting the provided URL
    // and add a JSON converter (because we are expecting json responses)
    val retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(jsonConverter)
        .build()
    // Use the client to create a service:
    // an object implementing the interface to the WebService
    val service: WebServiceInterface = retrofit.create(WebServiceInterface::class.java)

    val gameCallback = object : Callback<Game> {
        override fun onFailure(call: Call<Game>?, t: Throwable?) {
            // Code here what happens if calling the WebService fails
            Log.d("TAG", "WebService call failed")
        }

        override fun onResponse(
            call: Call<Game>?,
            response: Response<Game>?
        ) {
            // Code here what happens when WebService responds
            if (response != null) {
                if (response.code() == 200) {
                    // We got our data !
                    val responseData = response.body()
                    if (responseData != null) {
                        name.text = responseData.name
                        type.text = responseData.type
                        player.text = responseData.players.toString()
                        year.text = responseData.year.toString()
                    }
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val originIntent = intent
        var id: Int = originIntent.getIntExtra("id", 0)
        service.detail(id).enqueue(gameCallback)

    }
}
