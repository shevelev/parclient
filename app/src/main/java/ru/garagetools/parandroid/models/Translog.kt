package ru.garagetools.parandroid.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class TransLog(
    @SerializedName("id_TRAN")
    var idTran: Int,
    var trandatetime: Date,
    var trancode: Int,
    var isalarm: Int,
    var trandesc: String,
    var tranarea: String,
    var tranuser: String,
    var tranctrlid: Int?,
    var tranuserid: Int?,
    var tranoperid: Int?,
    var param1: Int?,
    var param2: Int?,
    var operdatetime: Date?,
    var arch_id: Int? = null)

data class User(var nick: String, var name: String, var code: String)
data class BodyData(var nick: String, val id: Int?, val date: String?, val time: String?)