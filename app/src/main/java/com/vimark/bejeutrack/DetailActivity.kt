package com.vimark.bejeutrack

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.kaopiz.kprogresshud.KProgressHUD
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.vimark.bejeutrack.model.DeviceModel
import com.vimark.bejeutrack.model.PositionModel
import com.vimark.bejeutrack.network.ApiRepo
import com.vimark.bejeutrack.network.RetrofitBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast
import java.util.concurrent.TimeUnit

class DetailActivity : AppCompatActivity() {

    private var idArmada = 0
    private var dispo: Disposable? = null
    private val service = RetrofitBuilder.getInstance(BuildConfig.BASE_URL_API).create(ApiRepo::class.java)
    private lateinit var loading: KProgressHUD
    private lateinit var mapBoxMap: MapboxMap
    private var positionId = 0
    private var positionData: List<PositionModel> = listOf()
    private var deviceData: List<DeviceModel> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Mapbox Access token
        Mapbox.getInstance(applicationContext, getString(R.string.mapbox_access_token))
        // Mapbox.setAccessToken(getString(R.string.mapbox_access_token))
        // mapView.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        loading = KProgressHUD.create(this)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setLabel("Please wait")
            .setCancellable(false)
            .setAnimationSpeed(2)
            .setDimAmount(0.5f)

        loading.show()

        idArmada = intent.getIntExtra("id", 0)

        mapView.getMapAsync {

            mapBoxMap = it
            mapBoxMap.setStyle(Style.MAPBOX_STREETS)

            val center = LatLng(-7.164495, 110.077099)
            val cameraPos = CameraPosition.Builder()
                .zoom(5.0)
                .target(center)
                .build()

            mapBoxMap.cameraPosition = cameraPos
        }

        dispo = service.getDevice(idArmada)
            .debounce(300, TimeUnit.MILLISECONDS)
            .timeout(60, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { devices ->
                    loading.dismiss()

                    deviceData = devices
                    detail_tv_idarmada.text = devices[0].name
                    positionId = devices[0].positionId
                    loadPosition(positionId)
                },
                onError = {
                    loading.dismiss()
                    toast("terjadi kesalahan, silahkan coba lagi")
                }
            )

        detail_btn_refresh.visibility = View.GONE
        detail_btn_refresh.onClick {
            loadPosition(positionId)
        }

        // https://maps.google.com/maps?q=24.197611,120.780512

        btn_share.onClick {
            val text = "Cek lokasi bus ${deviceData[0].name} di https://maps.google.com/maps?q=" +
                    "${positionData[0].latitude},${positionData[0].longitude}\n\n" +
                    "Download BejeuTrack di https://play.google.com/store/apps/details?id=com.vimark.bejeutrack"

            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, text)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadPosition(positionId: Int) {
        loading.show()
        dispo = service.getPosition(positionId)
            .debounce(300, TimeUnit.MILLISECONDS)
            .timeout(60, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { positions ->
                    loading.dismiss()

                    positionData = positions
                    detail_btn_refresh.visibility = View.VISIBLE

                    mapBoxMap.clear()

                    val pos = LatLng(positions[0].latitude, positions[0].longitude)

                    val marker = MarkerOptions()
                        .position(pos)

                    val cameraPos = CameraPosition.Builder()
                        .target(pos)
                        .zoom(8.0)
                        .build()

                    mapBoxMap.addMarker(marker)
                    mapBoxMap.cameraPosition = cameraPos

                    val course = positions[0].course.toInt()
                    val speed = positions[0].speed.toInt()

                    detail_tv_kecepatan.text = "Kecepatan : $speed km/h"
                    detail_tv_arah.text = "Arah : ${arahMataAngin(course)}"
                },
                onError = {
                    loading.dismiss()
                    toast("terjadi kesalahan, silahkan coba lagi")
                }
            )
    }

    private fun arahMataAngin(derajat: Int): String {

        val arah: String

        when (derajat) {
            in 340..350, in 0..20 -> {
                arah = "Utara"
            }
            in 21..70 -> {
                arah = "Timur Laut"
            }
            in 71..110 -> {
                arah = "Timur"
            }
            in 111..160 -> {
                arah = "Tenggara"
            }
            in 161..200 -> {
                arah = "Selatan"
            }
            in 201..250 -> {
                arah = "Barat Daya"
            }
            in 251..290 -> {
                arah = "Barat"
            }
            in 291..339 -> {
                arah = "Barat Laut"
            }
            else -> arah = "tidak diketahui"
        }

        return arah
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }
}
