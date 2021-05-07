package com.desiredsoftware.currencywatcher.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Spinner
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.desiredsoftware.currencywatcher.R
import com.desiredsoftware.currencywatcher.utils.GenerateMockStringsForRequest
import java.util.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var fragmentViewModel: MainFragmentViewModel
    private lateinit var currenciesInfoAdapter: CurrenciesInfoRecyclerViewAdapter

    private lateinit var recyclerViewExchangeRate : RecyclerView
    private lateinit var spinnerCurrency : Spinner

    private lateinit var progressBar : ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val root = inflater.inflate(R.layout.main_fragment, container, false)

        val button : Button = root.findViewById(R.id.button)
        spinnerCurrency = root.findViewById(R.id.spinnerCurrency)
        recyclerViewExchangeRate = root.findViewById(R.id.recyclerViewExchangeRate)

        progressBar = root.findViewById(R.id.progressBar)

        fragmentViewModel = ViewModelProvider(this).get(MainFragmentViewModel::class.java)

        button.setOnClickListener {
            progressBar.isVisible = true
            fragmentViewModel.getCurrenciesForDateRange(GenerateMockStringsForRequest())
        }

        fragmentViewModel.getCurrenciesForDateRange(GenerateMockStringsForRequest())
        progressBar.isVisible = true

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fragmentViewModel.currencyResultsLD.observe(viewLifecycleOwner, Observer {

        Log.d("Rx", "ValCursList items number is  ${it.size}")

        progressBar.isVisible = false
        currenciesInfoAdapter = CurrenciesInfoRecyclerViewAdapter(it, "USD")
        recyclerViewExchangeRate.adapter = currenciesInfoAdapter
        recyclerViewExchangeRate.layoutManager = GridLayoutManager(context, 2)
        Log.d("Rx", "currencyResultsLD is updated")
        })
    }
}