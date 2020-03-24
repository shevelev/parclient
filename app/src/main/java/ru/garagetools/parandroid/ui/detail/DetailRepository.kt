package ru.garagetools.parandroid.ui.detail

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.garagetools.parandroid.models.BodyData
import ru.garagetools.parandroid.models.TransLog
import ru.garagetools.parandroid.models.User
import ru.garagetools.parandroid.rest.ApiClient
import ru.garagetools.parandroid.rest.ApiInterface

class DetailRepository {
    private val webservice = ApiClient.client.create(ApiInterface::class.java)

    fun loadList(nick: String?): LiveData<List<TransLog>> {
        val data = MutableLiveData<List<TransLog>>()

        if (nick != null) {
            webservice.getUser7(nick).enqueue(object : Callback<List<TransLog>> {
                override fun onFailure(call: Call<List<TransLog>>, t: Throwable) {
                    Log.d("qwe", "webservice: DetailRepository -> Error load Translog ----> ${t.message}")
                }

                override fun onResponse(
                    call: Call<List<TransLog>>,
                    response: Response<List<TransLog>>
                ) {
                    if (response.body() != null) {
                        data.value = response.body()
                    }
                }
            })
        }
        Log.d("qwe","webservice: LoadList -> nick: $nick")
        return data
    }

    fun deleteTransLog(tranId: Int) {
        webservice.deleteTransLog(tranId).enqueue(object : Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Log.d("qwe", "webservice: error delete $tranId")
            }

            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                Log.d("qwe", "webservice: $tranId delete")
            }
        })
    }

    fun addTransLog(bd: BodyData) {
        webservice.createUser(bd).enqueue(object : Callback<TransLog?> {
            override fun onFailure(call: Call<TransLog?>, t: Throwable) {
            }

            override fun onResponse(call: Call<TransLog?>, response: Response<TransLog?>) {
                Log.d("qwe", "webservice: add nick: ${bd.nick} --> ${bd.date} ${bd.time}")
            }
        })
    }
}