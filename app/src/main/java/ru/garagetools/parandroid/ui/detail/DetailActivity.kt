package ru.garagetools.parandroid.ui.detail

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.text.format.DateUtils
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_user_detail.*
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


class DetailActivity : AppCompatActivity() {

    val transLogViewModel by lazy { ViewModelProviders.of(this).get(DetailViewModel::class.java)}

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: DetailAdapter

    var currentDateTime: TextView? = null
    var dateAndTime = Calendar.getInstance()
    val apiser = ApiClient.client.create(ApiInterface::class.java)
    private var mItemTouchHelper: ItemTouchHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        val nick = intent.getStringExtra("nick")
        val fio = intent.getStringExtra("fio")
        textView2.text = fio

        currentDateTime = findViewById(R.id.currentDateTime)
        setInitialDateTime();

        recyclerView = findViewById(R.id.rv_recyclerview2)
        recyclerAdapter = DetailAdapter(this, transLogViewModel,nick)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recyclerAdapter

        val callback: ItemTouchHelper.Callback = SimpleItemTouchHelperCallback(recyclerAdapter)
        mItemTouchHelper = ItemTouchHelper(callback)
        mItemTouchHelper!!.attachToRecyclerView(recyclerView)



        transLogViewModel.loadList(nick)
        transLogViewModel.getListUsers().observe(this, androidx.lifecycle.Observer { it.let {
            recyclerAdapter.setUserListItems(it as MutableList<TransLog>)
        } })



        button.setOnClickListener {
            val date = SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH).format(dateAndTime.timeInMillis)
            val time = SimpleDateFormat("HH:mm", Locale.ENGLISH).format(dateAndTime.timeInMillis)

            val bd = BodyData(nick, 3, date, time)

            val addDateTime: Call<TransLog> = apiser.createUser(bd)
            addDateTime.enqueue(object : Callback<TransLog?> {
                override fun onFailure(call: Call<TransLog?>, t: Throwable) {
                }

                override fun onResponse(call: Call<TransLog?>, response: Response<TransLog?>) {
                    Toast.makeText(this@DetailActivity, "Данные добавлены", Toast.LENGTH_LONG).show()
                    transLogViewModel.loadList(nick)
                }
            })
        }
    }

    // отображаем диалоговое окно для выбора даты
    fun setDate(v: View?) {
        DatePickerDialog(
            this@DetailActivity, d,
            dateAndTime.get(Calendar.YEAR),
            dateAndTime.get(Calendar.MONTH),
            dateAndTime.get(Calendar.DAY_OF_MONTH)
        )
            .show()
    }

    // отображаем диалоговое окно для выбора времени
    fun setTime(v: View?) {
        TimePickerDialog(
            this@DetailActivity, t,
            dateAndTime.get(Calendar.HOUR_OF_DAY),
            dateAndTime.get(Calendar.MINUTE), true
        )
            .show()
    }

    // установка начальных даты и времени
    private fun setInitialDateTime() {
        currentDateTime?.text = DateUtils.formatDateTime(
            this,
            dateAndTime.getTimeInMillis(),
            DateUtils.FORMAT_SHOW_TIME or
                    DateUtils.FORMAT_SHOW_DATE or
                    DateUtils.FORMAT_SHOW_YEAR or
                    DateUtils.FORMAT_ABBREV_ALL
        )
    }

    // установка обработчика выбора времени
    var t =
        OnTimeSetListener { view, hourOfDay, minute ->
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
            dateAndTime.set(Calendar.MINUTE, minute)
            setInitialDateTime()
        }

    // установка обработчика выбора даты
    var d =
        OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            dateAndTime.set(Calendar.YEAR, year)
            dateAndTime.set(Calendar.MONTH, monthOfYear)
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            setInitialDateTime()
        }
}