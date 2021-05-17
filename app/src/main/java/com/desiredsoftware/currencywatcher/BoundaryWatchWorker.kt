package com.desiredsoftware.currencywatcher

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        Log.d("Work manager", "Work manager's method doWork() started")

        val apiClient: ApiClient = ApiClient(BASE_URL)
        val dateToday : String = convertCalendarDateFormatForApiCall(Calendar.getInstance())
        val currencyCharCode = inputData.getString(CURRENCY_CHAR_CODE_INPUT_DATA_WORKER)

        val observable = apiClient.apiService.getCurrenciesForDate(dateToday)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {
                            valCursList ->
                            var sharedPreferences: SharedPreferences = context.getSharedPreferences(
                                    SHARED_PREFERENCES_BOUNDARY_VALUE,
                                    Context.MODE_PRIVATE
                            )

                            var boundaryValue: Float = sharedPreferences.getFloat(SHARED_PREFERENCES_BOUNDARY_VALUE, 0.0f)
                            Log.d("Shared preferences", "Read boundary value is: ${boundaryValue}")
                            Log.d("Work manager", "Today currency rate is : ${currencyCharCode?.let { getCurrencyValueByCharCode(it, valCursList) }}" + " RUR")

                            if (currencyCharCode?.let { getCurrencyValueByCharCode(it, valCursList) }!! > boundaryValue!!) {
                                Log.d("Work manager", "Boundary value was exceeded")


                                    var newMessageNotification = Notification.Builder(context, CHANNEL_ID)
                                            .setSmallIcon(R.drawable.ic_launcher_background)
                                            .setContentTitle(context.getString(R.string.boundary_exceeded_title))
                                            .setContentText("Курс ${currencyCharCode} превысил значение ${boundaryValue} RUR")

                                    var notificationManager = (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    val name = CHANNEL_NAME
                                    val descriptionText = CHANNEL_DESCRIPTION
                                    val importance = NotificationManager.IMPORTANCE_DEFAULT
                                    val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                                        description = descriptionText
                                    }

                                    notificationManager.createNotificationChannel(channel)
                                }

                                    with(NotificationManagerCompat.from(context)) {
                                        notificationManager.notify(1, newMessageNotification.build())
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