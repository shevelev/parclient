package ru.garagetools.parandroid.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recylcerview_item.view.*
//import ru.garagetools.parandroid.ui.detail.DetailActivity
import ru.garagetools.parandroid.R
import ru.garagetools.parandroid.models.User

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.MyViewHolder>() {

    private var usersList : List<User> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recylcerview_item,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = usersList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(usersList[position])
    }

    fun setUserListItems(usersList: List<User>){
        this.usersList = usersList.sortedBy { it.name }
        notifyDataSetChanged()
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private val tvName = view.tv_name
        private var nick = ""
        private var fio = ""

        init {
            view.setOnClickListener(this)
        }

        fun bind(user: User) {
            tvName.text = user.name
            nick = user.nick
            fio = user.name
        }

        override fun onClick(v: View) {
            val bundle = Bundle()
            bundle.putString("nick", nick)
            bundle.putString("fio", fio)
            v.findNavController().navigate(R.id.fragmentB, bundle)
        }
    }
}