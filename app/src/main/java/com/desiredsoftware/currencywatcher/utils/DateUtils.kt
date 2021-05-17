package com.desiredsoftware.currencywatcher.utils

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

fun convertCalendarDateFormatForApiCall(date: Calendar) : String
{
    var day = date.get(Calendar.DAY_OF_MONTH).toString()
    var month = (date.get(Calendar.MONTH)+1).toString()  // The day in the month starts from 0
    val year = date.get(Calendar.YEAR).toString()

    if (day.length==1)
    {
        day = StringBuilder(day).insert(0, "0").toString()
    }
    if (month.length==1)
    {
        month = StringBuilder(month).insert(0, "0").toString()
    }

    return day
            .plus("/")
            .plus(month)
            .plus("/")
            .plus(year)
}

fun parseStringToDate(stringToParse: String) : Calendar
{
    val pattern = "dd/MM/yyyy"
    val dateFormat : SimpleDateFormat = SimpleDateFormat(pattern)

    val calendarDate : Calendar = Calendar.getInstance()
    calendarDate.time = dateFormat.parse(stringToParse)

    return calendarDate
}

fun getDaysLastMonth() : ArrayList<String>
{
    val dateList : ArrayList<String> = ArrayList()

    val currentDate = Calendar.getInstance()

    val dateForDaysInMonthComputing = Calendar.getInstance()

    // It will be size of list for currencies rates showing in the last month
    val daysOnPreviousMonth : Int

    dateForDaysInMonthComputing.add(Calendar.MONTH, -1)
    daysOnPreviousMonth = dateForDaysInMonthComputing.getActualMaximum(Calendar.DAY_OF_MONTH)

    for (i in 0..(daysOnPreviousMonth-1))
    {
        dateList.add(convertCalendarDateFormatForApiCall(currentDate))

        currentDate.add(Calendar.DAY_OF_MONTH, -1)
    }

    return dateList
}


