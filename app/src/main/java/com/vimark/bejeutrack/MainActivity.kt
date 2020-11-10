package com.vimark.bejeutrack

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.vimark.bejeutrack.network.ApiRepo
import com.vimark.bejeutrack.network.RetrofitBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast
import java.util.concurrent.TimeUnit
import com.google.firebase.firestore.QuerySnapshot
import com.kaopiz.kprogresshud.KProgressHUD
import com.vimark.bejeutrack.model.DeviceModel
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import org.jetbrains.anko.startActivity


class MainActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var loading: KProgressHUD
    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        handleIntent(intent)

        loading = KProgressHUD.create(this)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setLabel("Please wait")
            .setCancellable(false)
            .setAnimationSpeed(2)
            .setDimAmount(0.5f)

        db = FirebaseFirestore.getInstance()

        main_btn_lacak.onClick {
            if (main_edt_kode_armada.text.isEmpty()) return@onClick
            lacakArmada()
        }
    }

    @SuppressLint("DefaultLocale")
    private fun lacakArmada() {

        loading.show()

        var kode = main_edt_kode_armada.text.toString()
        kode = kode.replace("-", "")
        kode = kode.replace(" ", "")

        db.collection("devices")
            .whereEqualTo("nama", kode.toLowerCase()).get().addOnCompleteListener {
                if (it.isSuccessful) {
                    loading.dismiss()

                    for (document in it.result as QuerySnapshot) {
                        id = document.data["id"].toString().toInt()
                        startActivity<DetailActivity>(
                            "id" to id
                        )
                        break
                    }

                    if (id == 0) {
                        alert {
                            isCancelable = false
                            message = "Data tidak ditemukan"
                            okButton { dialog ->
                                dialog.dismiss()
                            }
                        }.show()
                    }
                } else {
                    loading.dismiss()
                    Log.w("data", "Error getting documents.", it.exception)
                }
            }
    }
}
