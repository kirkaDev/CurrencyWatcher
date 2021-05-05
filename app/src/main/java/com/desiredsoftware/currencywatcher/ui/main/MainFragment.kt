package com.desiredsoftware.currencywatcher.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.desiredsoftware.currencywatcher.R

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var fragmentViewModel: MainFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val root = inflater.inflate(R.layout.main_fragment, container, false)

        val button : Button = root.findViewById(R.id.button)

        fragmentViewModel = ViewModelProvider(this).get(MainFragmentViewModel::class.java)

        button.setOnClickListener {
            fragmentViewModel.getCurrenciesForDate("02/03/2002")
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fragmentViewModel.currencyResultsLD.observe(viewLifecycleOwner, Observer {
            Log.d("Rx", "currencyResultsLD is updated")
        })
    }

}