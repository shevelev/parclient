package ru.garagetools.parandroid.ui.detail

import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.garagetools.parandroid.models.BodyData
import ru.garagetools.parandroid.models.TransLog
import ru.garagetools.parandroid.rest.ApiClient
import ru.garagetools.parandroid.rest.ApiInterface

class DetailViewModel : ViewModel() {

    private val detailRepository = DetailRepository()

    private var userDetailList: MutableLiveData<List<TransLog>> = MutableLiveData<List<TransLog>>()
    val webService = ApiClient.client.create(ApiInterface::class.java)

    fun loadList(nick: String?) {

        webService.getUser7(nick!!).enqueue(object : Callback<List<TransLog>> {
            override fun onFailure(call: Call<List<TransLog>>, t: Throwable) {
                Log.d("qwe", "ViewMovel 2 -> Error load Translog ----> ${t.message}")
            }

            override fun onResponse(
                call: Call<List<TransLog>>,
                response: Response<List<TransLog>>
            ) {
                if (response.body() != null) {
//                    userDetailList.value = response.body()
                    userDetailList.postValue(response.body())
                }
            }
        })
        Log.d("qwe2", "DetailViewModel: LIVEDATA: ${userDetailList}")
    }


//    fun loadList(nick: String?) {
//        Log.d("qwe", "DetailViewModel: LoadList -> nick: $nick")
//        userDetailList = detailRepository.loadList(nick)
//        //userDetailList.postValue(detailRepository.loadList2(nick))
//
//        Log.d("qwe2", "DetailViewModel: LIVEDATA: ${userDetailList}")
//    }

    fun getDetailList() = this.userDetailList

    fun deleteTransLog(nick: String?, position: Int) {

        val tran = this.userDetailList.value?.get(position)?.idTran

        if (tran != null && nick !=null) {
            detailRepository.deleteTransLog(nick, tran)
        }
    }

    fun addTransLog(bd: BodyData) {
        detailRepository.addTransLog(bd)
    }
}