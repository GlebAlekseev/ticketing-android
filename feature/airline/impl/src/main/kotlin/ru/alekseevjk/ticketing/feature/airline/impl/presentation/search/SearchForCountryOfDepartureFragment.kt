package ru.alekseevjk.ticketing.feature.airline.impl.presentation.search

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import ru.alekseevjk.ticketing.core.common.Resource
import ru.alekseevjk.ticketing.core.di.findDependencies
import ru.alekseevjk.ticketing.feature.airline.impl.R
import ru.alekseevjk.ticketing.feature.airline.impl.databinding.FragmentSearchForCountryOfDepartureBinding
import ru.alekseevjk.ticketing.feature.airline.impl.di.DaggerAirlineComponent
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.search.mapper.mapToPopularDestinationWithImage
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.search.rv.adapter.TicketOfferAdapter
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.tickets.AllTicketsFragment
import java.util.Locale
import javax.inject.Inject

class SearchForCountryOfDepartureFragment(
    private val navigateToFilters: () -> Unit
) : Fragment() {
    private var binding: FragmentSearchForCountryOfDepartureBinding? = null
    private val departureTown: String by lazy {
        requireArguments().getString(
            DEPARTURE_TOWN_ARGUMENT
        )!!
    }
    private val arrivalTown: String by lazy {
        requireArguments().getString(
            ARRIVAL_TOWN_ARGUMENT
        )!!
    }

    @Inject
    lateinit var ticketOfferAdapter: TicketOfferAdapter

    @Inject
    lateinit var searchForCountryOfDepartureViewModelAssistedFactory: SearchForCountryOfDepartureViewModel.AssistedFactory
    private val viewModelFactory by lazy {
        SearchForCountryOfDepartureViewModel.provideFactory(
            searchForCountryOfDepartureViewModelAssistedFactory,
            departureTown,
            arrivalTown
        )
    }
    private lateinit var searchForCountryOfDepartureViewModel: SearchForCountryOfDepartureViewModel


    override fun onAttach(context: Context) {
        DaggerAirlineComponent.factory().create(findDependencies()).inject(this)
        super.onAttach(context)
        searchForCountryOfDepartureViewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            )[SearchForCountryOfDepartureViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchForCountryOfDepartureBinding.inflate(layoutInflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        initListeners()
        observeViewState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun setupRecyclerView() {
        with(binding!!.offersTicketsSection.ticketOfferRV) {
            this.adapter = ticketOfferAdapter
        }
    }

    private fun initListeners() {
        binding!!.searchPanel3.backIB.setOnClickListener {
            findNavController().popBackStack()
        }
        binding!!.filtersChip.setOnClickListener {
            navigateToFilters.invoke()
        }
        binding!!.searchPanel3.changeIB.setOnClickListener {
            searchForCountryOfDepartureViewModel.changeTown()
        }
        setOnReturnBackDateClickListener()
        setOnDepartureDateClickListener()
        binding!!.openAllTicketsBtn.setOnClickListener {
            findNavController().navigate(
                resId = R.id.action_searchForCountryOfDepartureFragment_to_allTicketsFragment,
                args = Bundle().apply {
                    putParcelable(
                        AllTicketsFragment.SEARCH_FOR_COUNTRY_OF_DEPARTURE_ARGUMENT,
                        searchForCountryOfDepartureViewModel.viewState.value
                    )
                }
            )
        }
    }

    private fun setOnDepartureDateClickListener() {
        binding!!.dateChip.setOnClickListener {
            val dateSetListener = DatePickerDialog.OnDateSetListener { _, p1, p2, p3 ->
                val date = LocalDate.of(p1, p2, p3)
                searchForCountryOfDepartureViewModel.setDateDeparture(date)
            }
            val state = searchForCountryOfDepartureViewModel.viewState.value

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                dateSetListener,
                state.dateDeparture.year,
                state.dateDeparture.monthValue,
                state.dateDeparture.dayOfMonth
            )
            datePickerDialog.show()
        }
    }

    private fun setOnReturnBackDateClickListener() {
        binding!!.returnDateChip.setOnClickListener {
            val dateSetListener = DatePickerDialog.OnDateSetListener { _, p1, p2, p3 ->
                val date = LocalDate.of(p1, p2, p3)
                searchForCountryOfDepartureViewModel.setDateReturnBack(date)
            }
            val state = searchForCountryOfDepartureViewModel.viewState.value

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                dateSetListener,
                state.dateReturnBack?.year ?: state.dateDeparture.year,
                state.dateReturnBack?.monthValue ?: state.dateDeparture.monthValue,
                state.dateReturnBack?.dayOfMonth ?: state.dateDeparture.dayOfMonth
            )
            datePickerDialog.show()
        }
    }

    private fun observeViewState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchForCountryOfDepartureViewModel.viewState.collect { state ->
                    binding!!.searchPanel3.originTV.text = state.departureTown
                    binding!!.searchPanel3.destinationTV.text = state.arrivalTown
                    binding!!.classChip.text = state.classType

                    val pattern = DateTimeFormatter.ofPattern("dd MMM, E", Locale("ru"))

                    val text = state.dateDeparture.format(pattern)
                    val spannable = SpannableString(text)
                    val start = spannable.indexOf(',') + 1
                    val end = text.length
                    spannable.setSpan(
                        ForegroundColorSpan(Color.RED),
                        start,
                        end,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    binding!!.dateChip.text = spannable

                    val text2 = state.dateReturnBack?.format(pattern)
                    if (text2 != null) {
                        val spannable = SpannableString(text2)
                        val start = spannable.indexOf(',') + 1
                        val end = text2.length
                        spannable.setSpan(
                            ForegroundColorSpan(Color.RED),
                            start,
                            end,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        binding!!.returnDateChip.text = spannable
                        binding!!.returnDateChip.isChipIconVisible = false
                    } else {
                        binding!!.returnDateChip.text = "Обратно"
                        binding!!.returnDateChip.isChipIconVisible = true

                    }


                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchForCountryOfDepartureViewModel.ticketsOffersState.collect {
                    when (val state = it) {
                        is Resource.Success -> {
                            ticketOfferAdapter.submitList(
                                state.data.mapToPopularDestinationWithImage(
                                    requireContext()
                                )
                            )
                        }

                        is Resource.Loading -> {}
                        else -> Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    companion object {
        const val DEPARTURE_TOWN_ARGUMENT = "departure_town"
        const val ARRIVAL_TOWN_ARGUMENT = "arrival_town"
    }
}