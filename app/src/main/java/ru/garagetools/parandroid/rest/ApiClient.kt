package ru.garagetools.parandroid.rest

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiClient {

    companion object {
        val baseURL : String = "http://garage-pc127:8080/"
        var retrofit : Retrofit? = null

        var gson = GsonBuilder()
            .setLenient()
            .create()

        val client : Retrofit
            get(){
                if (retrofit == null) {
                    retrofit = Retrofit.Builder()
                        .baseUrl(baseURL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build()
                }
                return retrofit!!
            }
    }

}