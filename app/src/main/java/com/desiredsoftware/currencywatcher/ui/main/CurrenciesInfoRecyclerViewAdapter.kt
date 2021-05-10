package com.desiredsoftware.currencywatcher.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.desiredsoftware.currencywatcher.R
import com.desiredsoftware.currencywatcher.data.ValCursList
import com.desiredsoftware.currencywatcher.utils.getCurrencyValueByCharCode
import java.util.*

class CurrenciesInfoRecyclerViewAdapter(private val context: Context, private val currenciesInfo: ArrayList<ValCursList>, var currencyCharCode : String) : RecyclerView.Adapter<CurrenciesInfoRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_exchange_rate_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textViewRequestedDate?.text = context.getString(R.string.requested_date).plus(currenciesInfo[position].dateRequested?.replace("/",".", false))
        holder.textViewUpdatedDate?.text = context.getString(R.string.update_date).plus(currenciesInfo[position].dateUpdated)
        holder.textViewExchangeRate?.text = context.getString(R.string.currency_unit).plus(currencyCharCode)
                .plus(context.getString(R.string.equal_sign)
                .plus(getCurrencyValueByCharCode(currencyCharCode, currenciesInfo[position]).toString())
                .plus(context.getString(R.string.rub_currency)))

    }

    override fun getItemCount(): Int {
        return currenciesInfo.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewRequestedDate: TextView? = null
        var textViewUpdatedDate: TextView? = null
        var textViewExchangeRate: TextView? = null

        init {
            textViewRequestedDate = itemView.findViewById(R.id.textViewRequestedDate)
            textViewUpdatedDate = itemView.findViewById(R.id.textViewUpdatedDate)
            textViewExchangeRate = itemView.findViewById(R.id.textViewExchangeRate)
        }
    }
}