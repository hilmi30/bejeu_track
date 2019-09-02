package com.vimark.bejeutrack

import android.util.Log
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {

        val arah: String

        when (71) {
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

        assertEquals("Timur", arah)
    }
}
