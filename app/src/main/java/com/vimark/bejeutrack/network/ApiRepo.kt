package com.vimark.bejeutrack.network

import com.vimark.bejeutrack.model.DeviceModel
import com.vimark.bejeutrack.model.PositionModel
import io.reactivex.Observable
import retrofit2.http.*

interface ApiRepo {
    @GET("devices")
    fun getDevice(
        @Query("id") id: Int
    ): Observable<List<DeviceModel>>

    @GET("positions")
    fun getPosition(
        @Query("id") id: Int
    ): Observable<List<PositionModel>>
}