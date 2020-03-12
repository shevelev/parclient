package ru.garagetools.parandroid

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recylcerview_adapter.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.garagetools.parandroid.helper.ItemTouchHelperAdapter
import ru.garagetools.parandroid.models.TransLog
import ru.garagetools.parandroid.rest.ApiClient
import ru.garagetools.parandroid.rest.ApiInterface
import java.text.SimpleDateFormat
import java.util.*

class RUserDetail(private val context: Context) : RecyclerView.Adapter<RUserDetail.MyViewHolder>(),
    ItemTouchHelperAdapter {

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
       deleteUser(position)
    }

    private fun deleteUser(position: Int) {
        val apiser = ApiClient.client.create(ApiInterface::class.java)
        val delUserTime = apiser.deleteTransLog(usersList[position].idTran)

        delUserTime.enqueue(object : Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Log.d("qwe","оштбка")
            }

            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                Log.d("qwe","удалили")
            }
        })

        usersList.remove(usersList[position])
        Toast.makeText(this.context, "удалено",Toast.LENGTH_LONG).show()
        notifyItemRemoved(position);
        notifyDataSetChanged()
    }
}