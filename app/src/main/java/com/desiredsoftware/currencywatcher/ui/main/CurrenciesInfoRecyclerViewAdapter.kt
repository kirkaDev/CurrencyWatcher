package com.desiredsoftware.currencywatcher.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.desiredsoftware.currencywatcher.R
import com.desiredsoftware.currencywatcher.data.ValCursList
import com.desiredsoftware.currencywatcher.utils.GetCurrencyValueByCharCode
import java.util.*

class CurrenciesInfoRecyclerViewAdapter(private val currenciesInfo: ArrayList<ValCursList>, private val currencyCharCode : String) : RecyclerView.Adapter<CurrenciesInfoRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_exchange_rate_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textViewDate?.text = currenciesInfo[position].date

        holder.textViewExchangeRate?.text = "1 " + currencyCharCode + "= "  + GetCurrencyValueByCharCode(currencyCharCode, currenciesInfo[position]).toString() + " руб."
    }

    override fun getItemCount(): Int {
        return currenciesInfo.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewDate: TextView? = null
        var textViewExchangeRate: TextView? = null

        init {
            textViewDate = itemView.findViewById(R.id.textViewDate)
            textViewExchangeRate = itemView.findViewById(R.id.textViewExchangeRate)
        }
    }
}