package ru.garagetools.parandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.garagetools.parandroid.models.User
import ru.garagetools.parandroid.rest.ApiClient
import ru.garagetools.parandroid.rest.ApiInterface

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var RUsers: RUsers

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rv_recyclerview)
        RUsers = RUsers()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RUsers

        val apiser = ApiClient.client.create(ApiInterface::class.java)
        val listUsers = apiser.getUsers()

        listUsers.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>?, response: Response<List<User>>?) {
                if (response?.body() != null) {
                    RUsers.setUserListItems(response.body()!!)
                }
            }

            override fun onFailure(call: Call<List<User>>?, t: Throwable?) {
            }
        })
    }
}