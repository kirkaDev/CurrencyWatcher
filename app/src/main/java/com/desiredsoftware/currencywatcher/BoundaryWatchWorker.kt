package com.desiredsoftware.currencywatcher

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.desiredsoftware.currencywatcher.data.api.ApiClient
import com.desiredsoftware.currencywatcher.utils.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class BoundaryWatchWorker(context: Context, workerParams: WorkerParameters) : Worker(context,
        workerParams
) {

    val context : Context = context

    override fun doWork(): Result {
        Log.d("Work manager", "Work manager's method doWork() started")

        val apiClient = ApiClient(BASE_URL)
        val dateToday : String = convertCalendarDateFormatForApiCall(Calendar.getInstance())
        val currencyCharCode = inputData.getString(CURRENCY_CHAR_CODE_INPUT_DATA_WORKER)

        val observable = apiClient.apiService.getCurrenciesForDate(dateToday)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                    {
                        valCursList ->
                        val sharedPreferences: SharedPreferences = context.getSharedPreferences(
                                SHARED_PREFERENCES_BOUNDARY_VALUE,
                                Context.MODE_PRIVATE
                        )

                        val boundaryValue: Float = sharedPreferences.getFloat(SHARED_PREFERENCES_BOUNDARY_VALUE, 0.0f)
                        Log.d("Shared preferences", "Read boundary value is: ${boundaryValue}")
                        Log.d("Work manager", "Today currency rate is : ${currencyCharCode?.let { getCurrencyValueByCharCode(it, valCursList) }}" + " RUR")

                        if (currencyCharCode?.let { getCurrencyValueByCharCode(it, valCursList) }!! > boundaryValue) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                                val newMessageNotification = Notification.Builder(context, CHANNEL_ID)
                                        .setSmallIcon(R.drawable.ic_launcher_background)
                                        .setContentTitle(context.getString(R.string.boundary_exceeded_title))
                                        .setContentText("Курс ${currencyCharCode} превысил значение ${boundaryValue} RUR")
                                        .build()

                                val notification = (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)

                                with(NotificationManagerCompat.from(context)) {
                                    notification.notify(1, newMessageNotification)
                                }
                            }
                        }
                    },
                    { throwable ->
                        Log.d("Rx - Work manager", "Error with api method call on the Worker: ${throwable.printStackTrace()}")
                    }
            )
        return Result.success()
    }
}