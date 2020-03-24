package ru.garagetools.parandroid.ui.users

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.garagetools.parandroid.models.User
import ru.garagetools.parandroid.rest.ApiClient
import ru.garagetools.parandroid.rest.ApiInterface

class UserRepository  {
    private val webservice = ApiClient.client.create(ApiInterface::class.java)

    fun getUsers(): LiveData<List<User>> {
        val data = MutableLiveData<List<User>>()
        webservice.getUsers().enqueue(object : Callback<List<User>> {
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.d("qwe","ViewMovel 1 -> Error load usersList ----> ${t.message}")
            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                data.value = response.body()
            }
        })
        return data
    }
}