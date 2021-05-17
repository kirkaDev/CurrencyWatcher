package com.desiredsoftware.currencywatcher.data
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import java.text.DateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

@Root(name = "ValCurs", strict = false)
    class ValCursList() {
    @set:ElementList(inline = true, required = false)
    @get:ElementList(inline = true, required = false)
    var valCursList: ArrayList<ValCursItem>? = null

    // The closest date of updating the exchange rate to the requested date
    @set:Attribute(name = "Date", required = false)
    @get:Attribute(name = "Date", required = false)
    var dateUpdated: String? = null

    // Date on the string format for API call
    var dateRequested: String? = null

    // Date for ordering
    var dateRequestedDate: Calendar? = null
}

@Root(name="Valute")
class ValCursItem()
{
    @set:Element(name = "NumCode")
    @get:Element(name = "NumCode")
    var numCode: String? = null

    @set:Element(name = "CharCode")
    @get:Element(name = "CharCode")
    var charCode: String? = null

    @set:Element(name = "Nominal")
    @get:Element(name = "Nominal")
    var nominal: String? = null

    @set:Element(name = "Name")
    @get:Element(name = "Name")
    var name: String? = null

    @set:Element(name = "Value")
    @get:Element(name = "Value")
    var value: String? = null
}