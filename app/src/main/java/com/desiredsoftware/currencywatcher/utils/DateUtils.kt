package com.desiredsoftware.currencywatcher.utils

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

fun convertDateFormatForApiCall(date: Calendar) : String
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

fun parseStringToDate(stringToParse: String) : Date
{
    val pattern = "dd/MM/yyyy"
    val dateFormat : SimpleDateFormat = SimpleDateFormat(pattern)

    val date : Date = dateFormat.parse(stringToParse)

    return date
}

fun getDaysLastMonth() : ArrayList<String>
{
    var dateList : ArrayList<String> = ArrayList()

    var currentDate = Calendar.getInstance()

    var dateForDaysInMonthComputing = Calendar.getInstance()

    // It will be size of list for currencies rates showing in the last month
    val daysOnPreviousMonth : Int

    dateForDaysInMonthComputing.add(Calendar.MONTH, -1)
    daysOnPreviousMonth = dateForDaysInMonthComputing.getActualMaximum(Calendar.DAY_OF_MONTH)

    for (i in 0..(daysOnPreviousMonth-1))
    {
        dateList.add(convertDateFormatForApiCall(currentDate))

        currentDate.add(Calendar.DAY_OF_MONTH, -1)
    }

    return dateList
}

fun getLastDays(daysNumber: Int) : ArrayList<String>
{
    var dateList : ArrayList<String> = ArrayList()

    var currentDate = Calendar.getInstance()


    for (i in 0..(daysNumber-1))
    {
        dateList.add(convertDateFormatForApiCall(currentDate))

        currentDate.add(Calendar.DAY_OF_MONTH, -1)
    }

    return dateList
}


