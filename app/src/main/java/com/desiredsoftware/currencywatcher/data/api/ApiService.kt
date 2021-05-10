package com.desiredsoftware.currencywatcher.data.api

import com.desiredsoftware.currencywatcher.data.ValCursList
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.OkHttpClient
import org.simpleframework.xml.convert.AnnotationStrategy
import org.simpleframework.xml.core.Persister
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("scripts/XML_daily.asp")
    fun getCurrenciesForDate(@Query("date_req") date: String): Observable<ValCursList>

    companion object Factory {

        fun create(baseUrl: String): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(OkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                    SimpleXmlConverterFactory.createNonStrict(
                        Persister(AnnotationStrategy())
                    )
                )
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}