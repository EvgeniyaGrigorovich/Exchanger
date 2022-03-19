package com.devgenius.exchanger.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.devgenius.exchanger.R
import com.devgenius.exchanger.databinding.ActivityMainBinding
import com.devgenius.exchanger.domain.action.MainScreenAction
import com.devgenius.exchanger.presentation.adapter.CurrencyItemAdapter
import com.devgenius.exchanger.presentation.extension.gone
import com.devgenius.exchanger.presentation.extension.visible
import com.devgenius.exchanger.presentation.states.MainScreenGlobalState
import com.devgenius.exchanger.presentation.states.MainScreenViewState
import com.devgenius.exchanger.presentation.states.SortedState
import com.devgenius.exchangerdi.app.App
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels {
        (application as App).appComponent.provideViewModelFactory()
    }
    private val binding: ActivityMainBinding by viewBinding()

    private val currencyAdapter by lazy { CurrencyItemAdapter(listOf()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.executeAction(MainScreenAction.OpenMainScreen(sortedState = SortedState.Default))

        observeProducts()
        observeState()
    }

    private fun setupRecyclerView() {

        binding.currencyRecycler.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = currencyAdapter

        }
    }

    private fun observeProducts() {
        lifecycleScope.launch {
            viewModel.mainScreenRates
                .collect { rates ->
                    if (rates.isNotEmpty()) {
                        Log.i("RATE", "Rate ${rates.get(0).currency}")
                        binding.currencyRecycler.adapter.let {
                            if (it is CurrencyItemAdapter) {
                                it.setList(rates)
                            }
                        }
                    }
                }

        }
    }

    private fun observeState() {
        lifecycleScope.launch {
            viewModel.mainScreenState
                .collect { state ->
                    handleState(state.globalState)
                }
        }
    }

    private fun handleState(state: MainScreenGlobalState) {
        when (state) {
            is MainScreenGlobalState.LOADING -> handleLoading(state.isLoading)
        }

    }

    private fun handleLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.loadingProgressBar.visible()
        } else {
            binding.loadingProgressBar.gone()
        }
    }
}