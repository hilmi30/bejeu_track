package com.vimark.bejeutrack.model
import com.google.gson.annotations.SerializedName


data class PositionModel(
    @SerializedName("accuracy")
    val accuracy: Double = 0.0,
    @SerializedName("address")
    val address: Any = Any(),
    @SerializedName("altitude")
    val altitude: Double = 0.0,
    @SerializedName("attributes")
    val attributes: Attributes = Attributes(),
    @SerializedName("course")
    val course: Double = 0.0,
    @SerializedName("deviceId")
    val deviceId: Int = 0,
    @SerializedName("deviceTime")
    val deviceTime: String = "",
    @SerializedName("fixTime")
    val fixTime: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("latitude")
    val latitude: Double = 0.0,
    @SerializedName("longitude")
    val longitude: Double = 0.0,
    @SerializedName("network")
    val network: Any = Any(),
    @SerializedName("outdated")
    val outdated: Boolean = false,
    @SerializedName("protocol")
    val protocol: String = "",
    @SerializedName("serverTime")
    val serverTime: String = "",
    @SerializedName("speed")
    val speed: Double = 0.0,
    @SerializedName("type")
    val type: Any = Any(),
    @SerializedName("valid")
    val valid: Boolean = false
)

data class Attributes(
    @SerializedName("batteryLevel")
    val batteryLevel: Int = 0,
    @SerializedName("blocked")
    val blocked: Boolean = false,
    @SerializedName("charge")
    val charge: Boolean = false,
    @SerializedName("distance")
    val distance: Double = 0.0,
    @SerializedName("ignition")
    val ignition: Boolean = false,
    @SerializedName("motion")
    val motion: Boolean = false,
    @SerializedName("rssi")
    val rssi: Int = 0,
    @SerializedName("status")
    val status: Int = 0,
    @SerializedName("totalDistance")
    val totalDistance: Double = 0.0
)