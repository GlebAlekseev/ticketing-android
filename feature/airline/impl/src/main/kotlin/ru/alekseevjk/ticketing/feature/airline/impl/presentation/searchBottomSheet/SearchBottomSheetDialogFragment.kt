package ru.alekseevjk.ticketing.feature.airline.impl.presentation.searchBottomSheet

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import ru.alekseevjk.ticketing.core.common.Resource
import ru.alekseevjk.ticketing.core.di.findDependencies
import ru.alekseevjk.ticketing.design.R
import ru.alekseevjk.ticketing.feature.airline.impl.databinding.FragmentSearchBinding
import ru.alekseevjk.ticketing.feature.airline.impl.di.DaggerAirlineComponent
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.search.SearchForCountryOfDepartureFragment
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.searchBottomSheet.rv.adapter.PopularDestinationAdapter
import javax.inject.Inject

class SearchBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private val departureTown: String by lazy {
        requireArguments().getString(
            DEPARTURE_TOWN_ARGUMENT
        )!!
    }
    private var binding: FragmentSearchBinding? = null

    @Inject
    lateinit var searchBottomSheetViewModelAssistedFactory: SearchBottomSheetViewModel.AssistedFactory
    private val viewModelFactory by lazy {
        SearchBottomSheetViewModel.provideFactory(
            searchBottomSheetViewModelAssistedFactory,
            departureTown
        )
    }
    private lateinit var searchBottomSheetViewModel: SearchBottomSheetViewModel

    override fun onAttach(context: Context) {
        DaggerAirlineComponent.factory().create(findDependencies()).inject(this)
        super.onAttach(context)
        searchBottomSheetViewModel =
            ViewModelProvider(this, viewModelFactory)[SearchBottomSheetViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadPopularDestinations()
        if (savedInstanceState == null) {
            binding!!.searchPanel2.originTV.text = departureTown
        }
        initListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun loadPopularDestinations() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchBottomSheetViewModel.popularDestinationsWithImage.collect {
                    when (it) {
                        is Resource.Success -> {
                            val adapter = PopularDestinationAdapter(
                                onClick = {
                                    binding!!.searchPanel2.destinationET.setText(it)
                                },
                                list = it.data
                            )
                            binding!!.popularDestinationRV.adapter = adapter
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    private fun initListeners() {
        binding!!.searchPanel2.clearIB.setOnClickListener {
            binding!!.searchPanel2.destinationET.setText(requireContext().getString(R.string.empty))
        };

        {
            findNavController().navigate(
                resId = ru.alekseevjk.ticketing.feature.airline.impl.R.id.action_searchBottomSheetDialogFragment_to_plugFragment
            )
        }.let { body ->
            with(binding!!) {
                this.routeIV.setOnClickListener { body.invoke() }
                this.routeTV.setOnClickListener { body.invoke() }
                this.calendarIV.setOnClickListener { body.invoke() }
                this.calendarTV.setOnClickListener { body.invoke() }
                this.fireIV.setOnClickListener { body.invoke() }
                this.fireTV.setOnClickListener { body.invoke() }
            }
        };
        {
            lifecycleScope.launch {
                val destinations =
                    searchBottomSheetViewModel.popularDestinationsWithImage.replayCache.last()
                when (destinations) {
                    is Resource.Success -> {
                        val item = destinations.data.randomOrNull()
                        val text = item!!.popularDestination.destinationName
                        binding!!.searchPanel2.destinationET.setText(text)
                    }

                    else -> {}
                }
            }
        }.let { body ->
            with(binding!!) {
                this.globeIV.setOnClickListener { body.invoke() }
                this.globeTV.setOnClickListener { body.invoke() }
            }
        }
        with(binding!!.searchPanel2.destinationET) {
            this.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    val inputText = this.text.toString()
                    findNavController().navigate(
                        resId = ru.alekseevjk.ticketing.feature.airline.impl.R.id.action_searchBottomSheetDialogFragment_to_searchForCountryOfDepartureFragment,
                        args = Bundle().apply {
                            putString(
                                SearchForCountryOfDepartureFragment.ARRIVAL_TOWN_ARGUMENT,
                                inputText
                            )
                            putString(
                                SearchForCountryOfDepartureFragment.DEPARTURE_TOWN_ARGUMENT,
                                departureTown
                            )
                        }
                    )
                    true
                } else {
                    false
                }
            }
        }

    }


    companion object {
        const val DEPARTURE_TOWN_ARGUMENT = "departure_town"
    }
}