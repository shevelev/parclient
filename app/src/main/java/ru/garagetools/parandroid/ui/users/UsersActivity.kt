package ru.garagetools.parandroid.ui.users

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.garagetools.parandroid.R

class UsersActivity : AppCompatActivity() {

    private val usersViewModel by lazy { ViewModelProviders.of(this).get(UsersViewModel::class.java)}

    lateinit var recyclerView: RecyclerView
    lateinit var RUsers: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rv_recyclerview)
        RUsers = UsersAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RUsers

        usersViewModel.getListUsers().observe(this, Observer {
            it.let {
                RUsers.setUserListItems(it)
            }
        })

//        val apiser = ApiClient.client.create(ApiInterface::class.java)
//        val listUsers = apiser.getUsers()
//
//        listUsers.enqueue(object : Callback<List<User>> {
//            override fun onResponse(call: Call<List<User>>?, response: Response<List<User>>?) {
//                if (response?.body() != null) {
//                    RUsers.setUserListItems(response.body()!!)
//                }
//            }
//
//            override fun onFailure(call: Call<List<User>>?, t: Throwable?) {
//            }
//        })
    }
}