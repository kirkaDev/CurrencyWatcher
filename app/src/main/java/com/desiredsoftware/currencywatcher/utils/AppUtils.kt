package com.desiredsoftware.currencywatcher.utils

import com.desiredsoftware.currencywatcher.data.ValCursList
import kotlin.collections.ArrayList

    fun GenerateMockStringsForRequest() : ArrayList<String>
    {
        val dateRange : ArrayList<String> = ArrayList()

        dateRange.add("05/05/2021")
        dateRange.add("03/02/2020")
        dateRange.add("03/03/2019")
        dateRange.add("03/04/2019")
        dateRange.add("03/05/2019")
        dateRange.add("03/03/2019")
        dateRange.add("03/07/2005")
        dateRange.add("03/09/2019")
        dateRange.add("03/08/2019")
        dateRange.add("03/06/2005")
        dateRange.add("03/07/2019")
        dateRange.add("03/03/2019")
        dateRange.add("04/06/2019")
        dateRange.add("03/06/2005")
        dateRange.add("03/06/2005")
        dateRange.add("03/03/2019")
        dateRange.add("03/06/2005")
        dateRange.add("03/04/2021")
        dateRange.add("05/05/2021")
        dateRange.add("03/02/2020")
        dateRange.add("03/03/2019")
        dateRange.add("03/04/2019")
        dateRange.add("03/05/2019")
        dateRange.add("03/03/2019")
        dateRange.add("03/07/2005")
        dateRange.add("03/09/2019")
        dateRange.add("03/08/2019")
        dateRange.add("03/06/2005")
        dateRange.add("03/07/2019")
        dateRange.add("03/03/2019")
        dateRange.add("04/06/2019")
        dateRange.add("03/06/2005")
        dateRange.add("03/06/2005")
        dateRange.add("03/03/2019")
        dateRange.add("03/06/2005")

        return dateRange
    }

    fun GetCurrencyValueByCharCode(currencyCharCode : String, responseValCurs : ValCursList) : Double?
    {
        responseValCurs.valCursList?.forEach{
            if (it.charCode.equals(currencyCharCode))
            {
                return it.value?.replace(",", ".")?.toDouble()
            }
        }
        return 0.0
    }
