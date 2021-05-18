package com.desiredsoftware.currencywatcher.ui.main

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.desiredsoftware.currencywatcher.BoundaryWatchWorker
import com.desiredsoftware.currencywatcher.R
import com.desiredsoftware.currencywatcher.databinding.MainFragmentBinding
import com.desiredsoftware.currencywatcher.utils.*
import java.util.concurrent.TimeUnit


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var fragmentViewModel: MainFragmentViewModel
    private lateinit var currenciesInfoAdapter: CurrenciesInfoRecyclerViewAdapter

    private var _binding : MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        fragmentViewModel = ViewModelProvider(this).get(MainFragmentViewModel::class.java)

        _binding = MainFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        fragmentViewModel.boundaryValueLD.value = requireContext().getSharedPreferences(
                SHARED_PREFERENCES_BOUNDARY_VALUE,
                Context.MODE_PRIVATE
        )
                .getFloat(SHARED_PREFERENCES_BOUNDARY_VALUE, 0.0f)

        if (fragmentViewModel.boundaryValueLD.value!=0.0f)
        {
            binding.editTextBoundaryValue.setText(fragmentViewModel.boundaryValueLD.value.toString())
        }

        binding.button.setOnClickListener {
            binding.progressBar.isVisible = true
            fragmentViewModel.getCurrenciesForDateRange(getDaysLastMonth())
        }

        // Set items for the currencies spinner
        ArrayAdapter.createFromResource(
                requireContext(),
                R.array.currencies_spinner_items_array,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerCurrency.adapter = adapter
        }

        binding.spinnerCurrency.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedCurrency = parent.getItemAtPosition(position).toString()
                fragmentViewModel.currencyForShowingLD.value = selectedCurrency
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


        binding.editTextBoundaryValue.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                Log.d("Edit text", "Text was edited")
                setWorker(REPEAT_TIME_UNIT_FOR_WORKER, REPEAT_INTERVAL_WORKER, DELAY_TIME_UNIT_WORKER, DELAY_INTERVAL_WORKER)
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(
                    s: CharSequence, start: Int,
                    before: Int, count: Int
            ) {
                Log.d(
                        "Shared preferences",
                        "Refresh boundary value on the shared preferences: " + binding.editTextBoundaryValue.text.toString()
                )

                if (s.isNotEmpty())
                    refreshBoundaryValueOnSharedPrefs(requireContext(), binding.editTextBoundaryValue.text.toString())
            }
        })

        fragmentViewModel.getCurrenciesForDateRange(getDaysLastMonth())
        binding.progressBar.isVisible = true

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fragmentViewModel.currencyResultsLD.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            currenciesInfoAdapter = fragmentViewModel.currencyForShowingLD.value?.let { it1 ->
                CurrenciesInfoRecyclerViewAdapter(requireContext(),
                        it, it1)
            }!!
            binding.recyclerViewExchangeRate.adapter = currenciesInfoAdapter
            binding.recyclerViewExchangeRate.layoutManager = GridLayoutManager(context, 1)
        })

        fragmentViewModel.currencyForShowingLD.observe(viewLifecycleOwner, Observer {
            fragmentViewModel.getCurrenciesForDateRange(getDaysLastMonth())
            setWorker(REPEAT_TIME_UNIT_FOR_WORKER, REPEAT_INTERVAL_WORKER, DELAY_TIME_UNIT_WORKER, DELAY_INTERVAL_WORKER)
        })


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setWorker(repeatTimeUnit : TimeUnit, repeatInterval : Long, delayTimeUnit : TimeUnit, delayInterval : Long)
    {
        val inputData: Data = Data.Builder()
                .putString(CURRENCY_CHAR_CODE_INPUT_DATA_WORKER, fragmentViewModel.currencyForShowingLD.value)
                .build()

        val comparingRequest =
                PeriodicWorkRequest.Builder(BoundaryWatchWorker::class.java, repeatInterval, repeatTimeUnit)
                        .setInputData(inputData)
                        .setInitialDelay(delayInterval, delayTimeUnit)
                        .build()

        WorkManager
                .getInstance(requireContext())
                .enqueueUniquePeriodicWork(UNIQUE_WORK_NAME_WORKER, ExistingPeriodicWorkPolicy.REPLACE, comparingRequest)
    }
}