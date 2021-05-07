package com.desiredsoftware.currencywatcher.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.desiredsoftware.currencywatcher.data.ValCursList
import com.desiredsoftware.currencywatcher.data.api.ApiClient
import com.desiredsoftware.currencywatcher.utils.BASE_URL
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class MainFragmentViewModel : ViewModel() {

    private val apiClient: ApiClient = ApiClient(BASE_URL)

    var currencyResultsLD: MutableLiveData<ArrayList<ValCursList>> = MutableLiveData()

    fun getCurrenciesForDateRange(dateRangeForTheCurrencyReport: ArrayList<String>) {

        val valCursForDateRange : ArrayList<ValCursList> = ArrayList()

        Observable.fromIterable(dateRangeForTheCurrencyReport)
                .flatMap { dateItem -> apiClient.apiService.getCurrenciesForDate(dateItem).toObservable()  }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { currenciesRateResult ->
                            valCursForDateRange.add(currenciesRateResult)
                            Log.d("Rx", "onComplete in the getCurrenciesForDate API method: ${currenciesRateResult.toString()}")
                        },
                        { throwable ->
                            Log.d("Rx", "Got throwable in the getCurrenciesForDate API method: ${throwable.printStackTrace()}")

                        },
                        {
                            Log.d("Rx", "All requests for the date range was completed")
                            currencyResultsLD.value = valCursForDateRange
                        }
                )
    }
}