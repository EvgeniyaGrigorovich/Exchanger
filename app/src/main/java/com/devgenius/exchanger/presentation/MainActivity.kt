package com.devgenius.exchanger.presentation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.devgenius.exchanger.R
import com.devgenius.exchanger.databinding.ActivityMainBinding
import com.devgenius.exchanger.domain.action.MainScreenAction
import com.devgenius.exchanger.domain.entity.Rate
import com.devgenius.exchanger.presentation.adapter.CurrencyItemAdapter
import com.devgenius.exchanger.presentation.dialog.RemoveDialogFragment
import com.devgenius.exchanger.utils.extension.gone
import com.devgenius.exchanger.utils.extension.visible
import com.devgenius.exchanger.presentation.states.MainScreenGlobalState
import com.devgenius.exchanger.presentation.states.SortedState
import com.devgenius.exchangerdi.app.App
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private val viewModel: MainViewModel by viewModels {
        (application as App).appComponent.provideViewModelFactory()
    }
    private val binding: ActivityMainBinding by viewBinding()
    private val currencyAdapter by lazy { CurrencyItemAdapter(listOf()) }
    private val spinnerList = mutableListOf("EUR")
    private val dialog = RemoveDialogFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
        setBottomNavigation()
        setSpinner()
    }

    override fun onResume() {
        super.onResume()

        observeProducts()
        observeState()
        observeSymbols()
        setMenu()
    }

    private fun observeSymbols() {
        lifecycleScope.launch {
            viewModel.mainScreenSymbol.collect {
                spinnerList.addAll(it)
            }
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
                        binding.currencyRecycler.smoothScrollToPosition(1)
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

    private fun setupRecyclerView() {
        viewModel.executeAction(
            MainScreenAction.OpenMainScreen(
                sortedState = SortedState.ByAlphabet(
                    true
                )
            )
        )

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

        binding.swiperefresh.setOnRefreshListener {
            viewModel.executeAction(MainScreenAction.Refresh)
        }

        currencyAdapter.onLongClickListener = { rate ->
            viewModel.executeAction(MainScreenAction.LongClickAction(rate))
        }
    }

    private fun handleState(state: MainScreenGlobalState) {
        when (state) {
            is MainScreenGlobalState.LOADING -> handleLoading(state.isLoading)
            is MainScreenGlobalState.SHOW_MESSAGE -> showMessage(state.message)
            is MainScreenGlobalState.REFRESHING -> handleRefreshing(state.isRefresh)
            is MainScreenGlobalState.SHOW_DIALOG_TO_DELETE -> showDialogToDelete(state.rate)
        }
    }

    private fun showDialogToDelete(rate: Rate) {
        dialog.show(supportFragmentManager, RemoveDialogFragment.TAG)
        dialog.onCancelClickListener = {
            dialog.dismiss()
        }

        dialog.onDeleteCurrencyClickListener = {
            viewModel.executeAction(MainScreenAction.DeleteCurrency(rate))
        }
    }

    private fun handleRefreshing(refresh: Boolean) {
        binding.swiperefresh.isRefreshing = refresh
    }

    private fun handleLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.loadingProgressBar.visible()
        } else {
            binding.loadingProgressBar.gone()
        }
    }

    @SuppressLint("NewApi")
    private fun setMenu() {
        binding.textViewSort.setOnClickListener {
            val popup = PopupMenu(this, it)
            val inflater: MenuInflater = popup.menuInflater
            inflater.inflate(R.menu.actions, popup.menu)
            popup.show()
            popup.setForceShowIcon(true)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_alphabet_desc -> {
                        binding.textViewSort.text = getString(R.string.button_sort_alphabet_desc)
                        viewModel.executeAction(
                            MainScreenAction.ChangeSortedState(
                                SortedState.ByAlphabet(isAscending = false)
                            )
                        )
                    }
                    R.id.menu_alphabet_asc -> {
                        binding.textViewSort.text = getString(R.string.button_sort_alphabet_asc)
                        viewModel.executeAction(
                            MainScreenAction.ChangeSortedState(
                                SortedState.ByAlphabet(isAscending = true)
                            )
                        )
                    }
                    R.id.menu_value_desc -> {
                        binding.textViewSort.text = getString(R.string.button_sort_value_desc)
                        viewModel.executeAction(
                            MainScreenAction.ChangeSortedState(
                                SortedState.ByValue(isAscending = false)
                            )
                        )
                    }
                    R.id.menu_value_asc -> {
                        binding.textViewSort.text = getString(R.string.button_sort_value_asc)
                        viewModel.executeAction(
                            MainScreenAction.ChangeSortedState(
                                SortedState.ByValue(isAscending = true)
                            )
                        )
                    }
                }
                true
            }
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

    private fun setSpinner() {
        binding.currencySpinner.onItemSelectedListener = this
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            spinnerList
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.currencySpinner.adapter = adapter
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        viewModel.executeAction(
            MainScreenAction.ChangeCurrency(
                parent?.selectedItem.toString()
            )
        )
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}