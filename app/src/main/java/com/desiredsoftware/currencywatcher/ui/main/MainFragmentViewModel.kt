package com.desiredsoftware.currencywatcher.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.desiredsoftware.currencywatcher.data.ValCursList
import com.desiredsoftware.currencywatcher.data.api.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainFragmentViewModel : ViewModel() {

    private val apiClient: ApiClient = ApiClient("https://www.cbr.ru/")

    var currencyResultsLD: MutableLiveData<ValCursList> = MutableLiveData()

    fun getCurrenciesForDate(dateForTheCurrencyReport: String) {
        apiClient.apiService.getCurrenciesForDate(dateForTheCurrencyReport)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { it ->
                    currencyResultsLD.value = it
                    Log.d(
                        "Rx",
                        "onComplete: ${it.toString()}")
                },
                { throwable ->
                    Log.d(
                        "Rx",
                        "Got throwable in the getCurrenciesForDate API method: ${throwable.printStackTrace()}"
                    )
                    throwable.printStackTrace()
                },)
    }
}