package ru.garagetools.parandroid.rest

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiClient {

    companion object {
        val baseURL : String = "http://garage-pc127:8081/"

        var retrofit : Retrofit? = null

        var gson = GsonBuilder()
            .setLenient()
            .create()

        val client2 =  OkHttpClient.Builder()
            .addInterceptor(BasicAuthInterceptor("api", "testpass"))
            .build()

        val client : Retrofit
            get(){
                if (retrofit == null) {
                    retrofit = Retrofit.Builder()
                        .baseUrl(baseURL)
                        .client(client2)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build()
                }
                return retrofit!!
            }
    }

}