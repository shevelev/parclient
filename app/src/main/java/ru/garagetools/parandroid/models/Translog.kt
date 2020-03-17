package ru.garagetools.parandroid.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class TransLog(
    @SerializedName("id_TRAN") var idTran: Int,
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
    @SerializedName("arch_id") var archid: Int? = null
)