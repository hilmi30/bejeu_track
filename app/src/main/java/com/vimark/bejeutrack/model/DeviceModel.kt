package com.vimark.bejeutrack.model
import com.google.gson.annotations.SerializedName


data class DeviceModel(
    @SerializedName("category")
    val category: String = "",
    @SerializedName("contact")
    val contact: String = "",
    @SerializedName("disabled")
    val disabled: Boolean = false,
    @SerializedName("groupId")
    val groupId: Int = 0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("lastUpdate")
    val lastUpdate: String = "",
    @SerializedName("model")
    val model: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("phone")
    val phone: String = "",
    @SerializedName("positionId")
    val positionId: Int = 0,
    @SerializedName("status")
    val status: String = "",
    @SerializedName("uniqueId")
    val uniqueId: String = ""
)