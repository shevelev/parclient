package ru.garagetools.parandroid.ui.users

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recylcerview_adapter.view.*
import ru.garagetools.parandroid.ui.detail.DetailActivity
import ru.garagetools.parandroid.R
import ru.garagetools.parandroid.models.User

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.MyViewHolder>() {

    private var usersList : List<User> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recylcerview_adapter,parent,false)
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
            val nextScreen = Intent(v.context, DetailActivity::class.java)
            nextScreen.putExtra("nick", nick)
            nextScreen.putExtra("fio", fio)
            startActivity(v.context, nextScreen, null)
        }
    }
}