package ru.garagetools.parandroid.ui.detail

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recylcerview_item.view.*
import ru.garagetools.parandroid.R
import ru.garagetools.parandroid.helper.ItemTouchHelperAdapter
import ru.garagetools.parandroid.models.TransLog
import java.text.SimpleDateFormat
import java.util.*

class DetailAdapter(

    val transLogViewModel: DetailViewModel,
    val nick: String?
) : RecyclerView.Adapter<DetailAdapter.MyViewHolder>(),
    ItemTouchHelperAdapter {

    private var userDetailList: MutableList<TransLog> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recylcerview_item, parent, false)
        return MyViewHolder(view)
    }


    override fun getItemCount() = userDetailList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.d("qwe", "bind, position = " + position);
        holder.bind(userDetailList[position])
    }

    fun setUserListItems(usersList: MutableList<TransLog>) {
        this.userDetailList = usersList.sortedByDescending { it.trandatetime }.toMutableList()
        notifyDataSetChanged()
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvName = view.tv_name


        fun bind(transLog: TransLog) {
            tvName.text = toSimpleString(transLog.trandatetime)
        }

        fun toSimpleString(date: Date?) = with(date ?: Date()) {
            SimpleDateFormat("dd/MM/yyyy HH:mm").format(this)
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        Log.d("qwe", "on item move")
    }

    override fun onItemDismiss(position: Int) {
        transLogViewModel.deleteTransLog(position)
        transLogViewModel.loadList(nick)
    }
}