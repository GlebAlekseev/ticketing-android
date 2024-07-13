package ru.alekseevjk.ticketing.feature.airline.impl.presentation.filters

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import ru.alekseevjk.ticketing.core.common.base.BaseFragment
import ru.alekseevjk.ticketing.core.di.findDependencies
import ru.alekseevjk.ticketing.feature.airline.impl.databinding.FragmentFiltersBinding
import ru.alekseevjk.ticketing.feature.airline.impl.di.DaggerAirlineComponent
import javax.inject.Inject

class FiltersFragment : BaseFragment<FragmentFiltersBinding>(FragmentFiltersBinding::inflate) {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var filtersViewModel: FiltersViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectDependencies()
        initializeViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            loadInitialValues()
        }
        setupListeners()
    }

    private fun injectDependencies() {
        DaggerAirlineComponent.factory().create(findDependencies()).inject(this)
    }

    private fun initializeViewModel() {
        filtersViewModel = ViewModelProvider(this, viewModelFactory)[FiltersViewModel::class.java]
    }

    private fun loadInitialValues() {
        lifecycleScope.launch {
            binding?.apply {
                noTransfersSwitch.isChecked = filtersViewModel.getWithoutTransfers()
                withLuggageSwitch.isChecked = filtersViewModel.getOnlyWithLuggage()
            }
        }
    }

    private fun setupListeners() {
        binding?.apply {
            closeIB.setOnClickListener {
                findNavController().popBackStack()
            }
            applyBtn.setOnClickListener {
                applyFilters()
            }
        }
    }

    private fun applyFilters() {
        lifecycleScope.launch {
            binding?.apply {
                filtersViewModel.setWithoutTransfers(noTransfersSwitch.isChecked)
                filtersViewModel.setOnlyWithLuggage(withLuggageSwitch.isChecked)
                findNavController().popBackStack()
            }
        }
    }
}
