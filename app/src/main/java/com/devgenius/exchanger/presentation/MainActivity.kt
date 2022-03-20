package com.devgenius.exchanger.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.devgenius.exchanger.R
import com.devgenius.exchanger.databinding.ActivityMainBinding
import com.devgenius.exchanger.domain.action.MainScreenAction
import com.devgenius.exchanger.presentation.adapter.CurrencyItemAdapter
import com.devgenius.exchanger.utils.extension.gone
import com.devgenius.exchanger.utils.extension.visible
import com.devgenius.exchanger.presentation.states.MainScreenGlobalState
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
        setBottomNavigation()
    }

    override fun onResume() {
        super.onResume()

        observeProducts()
        observeState()

    }

    private fun setupRecyclerView() {
        viewModel.executeAction(MainScreenAction.OpenMainScreen(sortedState = SortedState.Default))

        binding.currencyRecycler.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = currencyAdapter
        }

        currencyAdapter.onSaveRateClickListener = { rate ->
            viewModel.executeAction(
                MainScreenAction.SaveToFavourites(
                    rate
                )
            )
        }
    }

    private fun observeProducts() {
        lifecycleScope.launch {
            viewModel.mainScreenRates
                .collect { rates ->
                    if (rates.isNotEmpty()) {
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
            is MainScreenGlobalState.SHOW_MWSSAGES -> showMessage(state.message)
        }
    }

    private fun handleLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.loadingProgressBar.visible()
        } else {
            binding.loadingProgressBar.gone()
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun setBottomNavigation() {
        with(binding) {
            bottomNavigationView.setOnItemReselectedListener { item ->
                when (item.itemId) {
                    R.id.buttonFavourites -> viewModel.executeAction(
                        MainScreenAction.OpenFavouritesScreen(
                            sortedState = viewModel.mainScreenState.value.internalState.isSorted
                        )
                    )
                    R.id.buttonHome -> viewModel.executeAction(
                        MainScreenAction.OpenMainScreen(
                            sortedState = viewModel.mainScreenState.value.internalState.isSorted
                        )
                    )
                }
            }
        }
    }
}