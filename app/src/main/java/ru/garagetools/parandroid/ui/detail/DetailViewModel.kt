package ru.garagetools.parandroid.ui.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.garagetools.parandroid.models.TransLog
import ru.garagetools.parandroid.rest.ApiClient
import ru.garagetools.parandroid.rest.ApiInterface

class DetailViewModel : ViewModel() {
    var userList: MutableLiveData<List<TransLog>> = MutableLiveData()

    val apiser = ApiClient.client.create(ApiInterface::class.java)

    fun loadList(nick: String?) {
        val listUsers = apiser.getUser7(nick!!)

        listUsers.enqueue(object : Callback<List<TransLog>> {
            override fun onFailure(call: Call<List<TransLog>>, t: Throwable) {
                Log.d("qwe", "ViewMovel 2 -> Error load Translog ----> ${t.message}")
            }

            override fun onResponse(
                call: Call<List<TransLog>>,
                response: Response<List<TransLog>>
            ) {
                if (response.body() != null) {
                    userList.value = response.body()
                }
            }
        })
    }

    fun getListUsers() = userList

    fun deleteTransLog(position: Int) {
        val delUserTime = userList.value?.get(position)?.idTran?.let { apiser.deleteTransLog(it) }

        if (delUserTime != null) {
            delUserTime.enqueue(object : Callback<Int> {
                override fun onFailure(call: Call<Int>, t: Throwable) {
                    Log.d("qwe", "оштбка")
                }

                override fun onResponse(call: Call<Int>, response: Response<Int>) {
                    Log.d("qwe", "удалили")
                }
            })
        }
    }
}