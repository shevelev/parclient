package ru.garagetools.parandroid.ui.detail

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.garagetools.parandroid.R
import ru.garagetools.parandroid.helper.SimpleItemTouchHelperCallback
import ru.garagetools.parandroid.models.BodyData
import ru.garagetools.parandroid.models.TransLog
import ru.garagetools.parandroid.rest.ApiClient
import ru.garagetools.parandroid.rest.ApiInterface
import java.text.SimpleDateFormat
import java.util.*

class DetailFragment: Fragment() {

    val transLogViewModel by lazy { ViewModelProviders.of(this).get(DetailViewModel::class.java)}

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: DetailAdapter

    var currentDateTime: TextView? = null
    var dateAndTime = Calendar.getInstance()
    val apiser = ApiClient.client.create(ApiInterface::class.java)
    private var mItemTouchHelper: ItemTouchHelper? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nick = arguments?.getString("nick")
        val fio = arguments?.getString("fio")
        textView2.text = fio

        setInitialDateTime();

        recyclerView = rv_recyclerview2
        recyclerAdapter = DetailAdapter( transLogViewModel,nick)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = recyclerAdapter

        val callback: ItemTouchHelper.Callback = SimpleItemTouchHelperCallback(recyclerAdapter)
        mItemTouchHelper = ItemTouchHelper(callback)
        mItemTouchHelper!!.attachToRecyclerView(recyclerView)



        transLogViewModel.loadList(nick)
        transLogViewModel.getListUsers().observe(viewLifecycleOwner, androidx.lifecycle.Observer { it.let {
            recyclerAdapter.setUserListItems(it as MutableList<TransLog>)
        } })



        button.setOnClickListener {
            val date = SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH).format(dateAndTime.timeInMillis)
            val time = SimpleDateFormat("HH:mm", Locale.ENGLISH).format(dateAndTime.timeInMillis)

            val bd = BodyData(nick!!, 3, date, time)

            val addDateTime: Call<TransLog> = apiser.createUser(bd)
            addDateTime.enqueue(object : Callback<TransLog?> {
                override fun onFailure(call: Call<TransLog?>, t: Throwable) {
                }

                override fun onResponse(call: Call<TransLog?>, response: Response<TransLog?>) {
                    Toast.makeText(activity, "Данные добавлены", Toast.LENGTH_LONG).show()
                    transLogViewModel.loadList(nick)
                }
            })
        }
    }

    fun setDate(v: View?) {
        DatePickerDialog(
            activity, d,
            dateAndTime.get(Calendar.YEAR),
            dateAndTime.get(Calendar.MONTH),
            dateAndTime.get(Calendar.DAY_OF_MONTH)
        )
            .show()
    }

    // отображаем диалоговое окно для выбора времени
    fun setTime(v: View?) {
        TimePickerDialog(
            activity, t,
            dateAndTime.get(Calendar.HOUR_OF_DAY),
            dateAndTime.get(Calendar.MINUTE), true
        )
            .show()
    }

    // установка начальных даты и времени
    private fun setInitialDateTime() {
        currentDateTime?.text = DateUtils.formatDateTime(
            activity,
            dateAndTime.getTimeInMillis(),
            DateUtils.FORMAT_SHOW_TIME or
                    DateUtils.FORMAT_SHOW_DATE or
                    DateUtils.FORMAT_SHOW_YEAR or
                    DateUtils.FORMAT_ABBREV_ALL
        )
    }

    // установка обработчика выбора времени
    var t =
        TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
            dateAndTime.set(Calendar.MINUTE, minute)
            setInitialDateTime()
        }

    // установка обработчика выбора даты
    var d =
        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            dateAndTime.set(Calendar.YEAR, year)
            dateAndTime.set(Calendar.MONTH, monthOfYear)
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            setInitialDateTime()
        }

}