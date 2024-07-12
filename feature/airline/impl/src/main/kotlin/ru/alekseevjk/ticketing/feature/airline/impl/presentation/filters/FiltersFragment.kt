package ru.alekseevjk.ticketing.feature.airline.impl.presentation.filters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import ru.alekseevjk.ticketing.core.di.findDependencies
import ru.alekseevjk.ticketing.feature.airline.impl.databinding.FragmentFiltersBinding
import ru.alekseevjk.ticketing.feature.airline.impl.di.DaggerAirlineComponent
import javax.inject.Inject


class FiltersFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var filtersViewModel: FiltersViewModel
    private var binding: FragmentFiltersBinding? = null

    override fun onAttach(context: Context) {
        DaggerAirlineComponent.factory().create(findDependencies()).inject(this)
        super.onAttach(context)
        filtersViewModel =
            ViewModelProvider(this, viewModelFactory)[FiltersViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFiltersBinding.inflate(layoutInflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null){
            loadValues()
        }
        initListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun loadValues() {
        lifecycleScope.launch {
            binding!!.noTransfersSwitch.isChecked = filtersViewModel.withoutTransfers
            binding!!.withLuggageSwitch.isChecked = filtersViewModel.onlyWithLuggage
        }
    }

    private fun initListeners() {
        binding!!.closeIB.setOnClickListener {
            findNavController().popBackStack()
        }
        binding!!.applyBtn.setOnClickListener {
            lifecycleScope.launch {
                filtersViewModel.withoutTransfers = binding!!.noTransfersSwitch.isChecked
                filtersViewModel.onlyWithLuggage = binding!!.withLuggageSwitch.isChecked

                findNavController().popBackStack()
            }
        }
        binding!!.noTransfersSwitch
        binding!!.withLuggageSwitch
    }
}