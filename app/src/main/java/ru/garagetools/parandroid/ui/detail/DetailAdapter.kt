package ru.garagetools.parandroid.ui.detail

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recylcerview_adapter.view.*
import ru.garagetools.parandroid.R
import ru.garagetools.parandroid.helper.ItemTouchHelperAdapter
import ru.garagetools.parandroid.models.TransLog
import java.text.SimpleDateFormat
import java.util.*

class DetailAdapter(
    private val context: Context,
    transLogViewModel: DetailViewModel,
    nick: String
) : RecyclerView.Adapter<DetailAdapter.MyViewHolder>(),
    ItemTouchHelperAdapter {

    //var transLogViewModel = ViewModelProviders.of(FragmentActivity()).get(DetailViewModel::class.java)
    var t = transLogViewModel
    val nick = nick
    private var usersList: MutableList<TransLog> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recylcerview_adapter, parent, false)
        return MyViewHolder(view)
    }


    override fun getItemCount() = usersList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(usersList[position])
    }

    fun setUserListItems(usersList: MutableList<TransLog>) {
        this.usersList = usersList.sortedByDescending { it.trandatetime }.toMutableList()
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
       //deleteUser(position)
        t.deleteTransLog(position)
        t.loadList(nick)
    }

//    private fun deleteUser(position: Int) {
//        val apiser = ApiClient.client.create(ApiInterface::class.java)
//       d
//        Toast.makeText(this.context, "удалено",Toast.LENGTH_LONG).show()
//        notifyItemRemoved(position);
//        notifyDataSetChanged()
//    }
}