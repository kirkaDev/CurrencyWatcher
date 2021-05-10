package com.desiredsoftware.currencywatcher.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.desiredsoftware.currencywatcher.data.ValCursList
import com.desiredsoftware.currencywatcher.data.api.ApiClient
import com.desiredsoftware.currencywatcher.utils.BASE_URL
import com.desiredsoftware.currencywatcher.utils.parseStringToDate
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class MainFragmentViewModel : ViewModel() {

    private val apiClient: ApiClient = ApiClient(BASE_URL)

    var currencyResultsLD: MutableLiveData<ArrayList<ValCursList>> = MutableLiveData()
    var boundaryValueLD: MutableLiveData<Float> = MutableLiveData()
    var currencyForShowingLD: MutableLiveData<String> = MutableLiveData()

    fun getCurrenciesForDateRange(dateRangeForTheCurrencyReport: ArrayList<String>) {

        val valCursForDateRange : ArrayList<ValCursList> = ArrayList()

        val observable = Observable.fromIterable(dateRangeForTheCurrencyReport)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { dateRequestedItem ->
                    apiClient.apiService.getCurrenciesForDate(dateRequestedItem)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(
                                    { currenciesRateResult ->
                                        currenciesRateResult.dateRequested = dateRequestedItem
                                        currenciesRateResult.dateRequestedDate = parseStringToDate(dateRequestedItem)
                                        valCursForDateRange.add(currenciesRateResult)
                                        valCursForDateRange.sortByDescending { it.dateRequestedDate }
                                        currencyResultsLD.value = valCursForDateRange

                                        Log.d("Rx", "onNext called in the getCurrenciesForDate API method for the date requested: ${currenciesRateResult.dateRequested}")
                                    },
                                    { throwable ->
                                        Log.d("Rx", "onError called in the getCurrenciesForDate API method: ${throwable.printStackTrace()}")
                                    })
                }
    }
}