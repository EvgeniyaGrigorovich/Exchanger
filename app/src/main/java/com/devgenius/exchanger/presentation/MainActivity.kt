package com.devgenius.exchanger.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.devgenius.exchanger.R
import com.devgenius.exchanger.databinding.ActivityMainBinding
import com.devgenius.exchanger.domain.action.MainScreenAction
import com.devgenius.exchanger.presentation.states.SortedState
import com.devgenius.exchangerdi.di.App
import com.devgenius.exchangerdi.di.AppComponent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels {
        (application as App).appComponent.viewModelFactory()
    }
    private val binding: ActivityMainBinding by viewBinding()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding.button.setOnClickListener {
            viewModel.executeAction(MainScreenAction.OpenMainScreen(sortedState = SortedState.Default))
        }

        observeProducts()
    }

    private fun observeProducts() {
        viewModel.mainScreenRates
            .flowWithLifecycle(this.lifecycle, Lifecycle.State.STARTED)
            .onEach { rate ->
                Log.i("RATE", "${rate.size}")
            }
            .launchIn(this.lifecycleScope)
    }
}