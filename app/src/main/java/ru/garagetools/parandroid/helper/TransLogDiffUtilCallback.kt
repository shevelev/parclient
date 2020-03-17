package ru.garagetools.parandroid.helper

import androidx.recyclerview.widget.DiffUtil
import ru.garagetools.parandroid.models.TransLog

class TransLogDiffUtilCallback(
    oldList: MutableList<TransLog>,
    newList: MutableList<TransLog>
) : DiffUtil.Callback() {

    lateinit var oldList : MutableList<TransLog>
    lateinit var newList :MutableList<TransLog>

    fun ProductDiffUtilCallback(
        oldList: MutableList<TransLog>,
        newList: MutableList<TransLog>
    ) {
        this.oldList = oldList
        this.newList = newList
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldTransLog: TransLog = oldList[oldItemPosition]
        val newTransLog: TransLog = newList[newItemPosition]
        return oldTransLog.idTran == newTransLog.idTran
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldTransLog: TransLog = oldList[oldItemPosition]
        val newTransLog: TransLog = newList[newItemPosition]
        return (oldTransLog.trandatetime == newTransLog.trandatetime)
    }

}