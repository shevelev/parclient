package ru.garagetools.parandroid.ui.detail

import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.garagetools.parandroid.models.BodyData
import ru.garagetools.parandroid.models.TransLog

class DetailViewModel : ViewModel() {

    private val detailRepository = DetailRepository()

    var userDetailList: MutableLiveData<List<TransLog>> = MutableLiveData()

    fun loadList(nick: String?) {
        Log.d("qwe","LoadList")
        userDetailList = detailRepository.loadList(nick) as MutableLiveData<List<TransLog>>
    }

    fun getDetailList() = userDetailList

    fun deleteTransLog(position: Int) {

        val tran = userDetailList.value?.get(position)?.idTran

        if (tran != null) {
            detailRepository.deleteTransLog(tran)
        }
    }

    fun addTransLog(bd: BodyData) {
        detailRepository.addTransLog(bd)
        Handler().postDelayed({ loadList(bd.nick) }, 2000)
    }
}