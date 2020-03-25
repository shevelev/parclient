package ru.garagetools.parandroid.rest

import retrofit2.Call
import retrofit2.http.*
import ru.garagetools.parandroid.models.BodyData
import ru.garagetools.parandroid.models.TransLog
import ru.garagetools.parandroid.models.User


interface ApiInterface {
    @GET("api/users/")
    fun getUsers(): Call<List<User>>

    @GET("api/user/{user}/7")
    fun getUser7(@Path("user") user: String): Call<List<TransLog>>

    @GET("api/user/{user}/1")
    fun getUser1(@Path("user") user: String): Call<TransLog>

    //@Headers("Content-Type: application/json")
    @POST("api/users/add")
    fun createUser(@Body bodyData: BodyData): Call<TransLog>

    @DELETE("api/users/{name}/{id}")
    fun deleteTransLog(@Path("name") name: String,@Path("id") id: Int): Call<Int>

}