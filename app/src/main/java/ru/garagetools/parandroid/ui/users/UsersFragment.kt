package ru.garagetools.parandroid.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_users.*
import ru.garagetools.parandroid.R


class UsersFragment: Fragment() {

    private val usersViewModel: UsersViewModel by lazy { ViewModelProvider(requireActivity()).get(UsersViewModel::class.java)}

    lateinit var recyclerView: RecyclerView
    lateinit var RUsers: UsersAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = rv_recyclerview_users
        RUsers = UsersAdapter()
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = RUsers

        usersViewModel.getListUsers().observe(viewLifecycleOwner, Observer {
            it.let {
                RUsers.setUserListItems(it)
            }
        })
    }
}