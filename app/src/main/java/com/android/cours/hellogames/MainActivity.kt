package com.android.cours.hellogames

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    // A List to store or objects
    val data = arrayListOf<Game>()
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

    val listCallback = object : Callback<List<Game>> {
        override fun onFailure(call: Call<List<Game>>?, t: Throwable?) {
            // Code here what happens if calling the WebService fails
            Log.d("TAG", "WebService call failed")
        }

        override fun onResponse(
            call: Call<List<Game>>?,
            response: Response<List<Game>>?
        ) {
            // Code here what happens when WebService responds
            if (response != null) {
                if (response.code() == 200) {
                    // We got our data !
                    val responseData = response.body()
                    if (responseData != null) {
                        data.addAll(responseData)
                        Log.d("TAG", "WebService success : " + data.size)

                        button.text = data[0].name
                        button2.text = data[1].name
                        button3.text = data[2].name
                        button4.text = data[3].name

                        button.setOnClickListener {
                            val explicitIntent = Intent(this@MainActivity, DetailActivity::class.java)
                            explicitIntent.putExtra("id", data[0].id)
                            startActivity(explicitIntent)
                        }

                        button2.setOnClickListener {
                            val explicitIntent = Intent(this@MainActivity, DetailActivity::class.java)
                            explicitIntent.putExtra("id", data[1].id)
                            startActivity(explicitIntent)
                        }

                        button3.setOnClickListener {
                            val explicitIntent = Intent(this@MainActivity, DetailActivity::class.java)
                            explicitIntent.putExtra("id", data[2].id)
                            startActivity(explicitIntent)
                        }

                        button4.setOnClickListener {
                            val explicitIntent = Intent(this@MainActivity, DetailActivity::class.java)
                            explicitIntent.putExtra("id", data[3].id)
                            startActivity(explicitIntent)
                        }
                    }
                }
            }
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        service.listGames().enqueue(listCallback)

    }
}
