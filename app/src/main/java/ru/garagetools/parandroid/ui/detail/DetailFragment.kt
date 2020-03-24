package ru.garagetools.parandroid.ui.detail

import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_detail.*
import ru.garagetools.parandroid.ui.pickers.DatePickerFragment
import ru.garagetools.parandroid.R
import ru.garagetools.parandroid.ui.pickers.TimePickerFragment
import ru.garagetools.parandroid.helper.SimpleItemTouchHelperCallback
import ru.garagetools.parandroid.models.BodyData
import ru.garagetools.parandroid.models.TransLog
import ru.garagetools.parandroid.rest.ApiClient
import ru.garagetools.parandroid.rest.ApiInterface
import java.text.SimpleDateFormat
import java.util.*


class DetailFragment : Fragment() {

    val transLogViewModel by lazy { ViewModelProviders.of(this).get(DetailViewModel::class.java) }
//    val transLogViewModel by lazy { ViewModelProvider(requireActivity()).get(DetailViewModel::class.java) }

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: DetailAdapter

    var dateAndTime = Calendar.getInstance()
    private var mItemTouchHelper: ItemTouchHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nick = arguments?.getString("nick")
        val fio = arguments?.getString("fio")
        user_info.text = fio

        setInitialDateTime();

        recyclerView = rv_recyclerview2
        recyclerAdapter = DetailAdapter(transLogViewModel, nick)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = recyclerAdapter

        val callback: ItemTouchHelper.Callback = SimpleItemTouchHelperCallback(recyclerAdapter)
        mItemTouchHelper = ItemTouchHelper(callback)
        mItemTouchHelper!!.attachToRecyclerView(recyclerView)

        transLogViewModel.loadList(nick)

        Log.d("qwe2", "DetailFragment: LIVEDATA: ${transLogViewModel.getDetailList()}")

        transLogViewModel.getDetailList().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it.let {
                Log.d("qwe","DetailFragment: userDetailList.observe")
                recyclerAdapter.setUserListItems(it as MutableList<TransLog>)
            }
        })

        button.setOnClickListener {
            val date =
                SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH).format(dateAndTime.timeInMillis)
            val time = SimpleDateFormat("HH:mm", Locale.ENGLISH).format(dateAndTime.timeInMillis)

            val bd = BodyData(nick!!, 3, date, time)

            transLogViewModel.addTransLog(bd)
            transLogViewModel.loadList(bd.nick)
        }

        dateButton.setOnClickListener {
            val newFragment =
                DatePickerFragment(
                    dateAndTime,
                    currentDateTime
                )
            newFragment.show(childFragmentManager, "datePicker")
        }

        timeButton.setOnClickListener {
            val newFragment =
                TimePickerFragment(
                    dateAndTime,
                    currentDateTime
                )
            newFragment.show(childFragmentManager, "datePicker")
        }
    }

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
}