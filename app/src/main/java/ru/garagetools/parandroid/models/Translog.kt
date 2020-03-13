package ru.garagetools.parandroid.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class TransLog(
    @SerializedName("id_TRAN") var idTran: Int,
    @SerializedName("trandatetime") var trandatetime: Date,
    @SerializedName("trancode") var trancode: Int,
    @SerializedName("isalarm") var isalarm: Int,
    @SerializedName("trandesc") var trandesc: String,
    @SerializedName("tranarea") var tranarea: String,
    @SerializedName("tranuser") var tranuser: String,
    @SerializedName("tranctrlid") var tranctrlid: Int?,
    @SerializedName("tranuserid") var tranuserid: Int?,
    @SerializedName("tranoperid") var tranoperid: Int?,
    @SerializedName("param1") var param1: Int?,
    @SerializedName("param2") var param2: Int?,
    @SerializedName("operdatetime") var operdatetime: Date?,
    @SerializedName("arch_id") var archid: Int? = null
)

data class User(
    @SerializedName("nick") var nick: String,
    @SerializedName("name") var name: String,
    @SerializedName("code") var code: String
)

data class BodyData(
    @SerializedName("nick") var nick: String,
    @SerializedName("id") val id: Int?,
    @SerializedName("date") val date: String?,
    @SerializedName("time") val time: String?)