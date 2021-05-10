package com.desiredsoftware.currencywatcher.utils

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.util.rangeTo
import com.desiredsoftware.currencywatcher.data.ValCursList
import java.time.LocalDate
import java.util.*

fun getCurrencyValueByCharCode(currencyCharCode: String, responseValCurs: ValCursList) : Double?
    {
        responseValCurs.valCursList?.forEach{
            if (it.charCode.equals(currencyCharCode))
            {
                return it.value?.replace(",", ".")?.toDouble()
            }
        }
        return 0.0
    }

    fun refreshBoundaryValueOnSharedPrefs(context: Context, boundaryValue: String)
    {
        var sharedPreferences : SharedPreferences =  context.getSharedPreferences(
                SHARED_PREFERENCES_BOUNDARY_VALUE,
                Context.MODE_PRIVATE
        )

        val editor = sharedPreferences.edit()
        editor.putFloat(SHARED_PREFERENCES_BOUNDARY_VALUE, boundaryValue.toFloat()).apply()

        val boundary : Float = sharedPreferences.getFloat(SHARED_PREFERENCES_BOUNDARY_VALUE, 0.0f)
        Log.d("Shared preferences", "Read boundary value is: $boundary")
    }




