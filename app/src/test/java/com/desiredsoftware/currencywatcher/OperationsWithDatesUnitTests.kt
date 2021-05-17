package com.desiredsoftware.currencywatcher

import com.desiredsoftware.currencywatcher.utils.convertCalendarDateFormatForApiCall
import com.desiredsoftware.currencywatcher.utils.parseStringToDate
import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.util.*

class OperationsWithDatesUnitTests {
    @Test
    fun convertCalendarDateFormatForApiCallLongNumbers() {

        val calendar = Calendar.getInstance()

        // month starts with 0, 0 - January
        calendar.set(2021, 11, 17 )

        val outputDate = convertCalendarDateFormatForApiCall(calendar)

        assertEquals("17/12/2021", outputDate)
    }

    @Test
    fun convertCalendarDateFormatForApiCallShortNumbersWithoutZeros() {

        val calendar = Calendar.getInstance()

        // month starts with 0, 0 - January
        calendar.set(2021, 0, 3 )

        val outputDate = convertCalendarDateFormatForApiCall(calendar)

        assertEquals("03/01/2021", outputDate)
    }

    @Test
    fun parseStringToDateTest() {
        val stringForParsing = "03/03/2021"

        val parsedDate = parseStringToDate(stringForParsing)

        val parsedDay = parsedDate.get(Calendar.DAY_OF_MONTH)
        val parsedMonth = parsedDate.get(Calendar.MONTH)
        val parsedYear = parsedDate.get(Calendar.YEAR)

        val outputDateArray = intArrayOf(parsedDay, parsedMonth, parsedYear)

        assertArrayEquals(intArrayOf(3, 2, 2021), outputDateArray)
    }
}