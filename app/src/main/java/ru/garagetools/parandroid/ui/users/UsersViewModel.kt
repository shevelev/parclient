package ru.garagetools.parandroid.ui.users

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.garagetools.parandroid.models.User
import ru.garagetools.parandroid.rest.ApiClient
import ru.garagetools.parandroid.rest.ApiInterface

class UsersViewModel: ViewModel() {
    var userList: MutableLiveData<List<User>> = MutableLiveData()
    val apiser = ApiClient.client.create(ApiInterface::class.java)

    init {

        val t = apiser.getUsers()

        t.enqueue(object : Callback<List<User>> {
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.d("qwe","ViewMovel 1 -> Error load usersList ----> ${t.message}")
            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                userList.value = response.body()
            }
        })

    }

    fun getListUsers() = userList
}