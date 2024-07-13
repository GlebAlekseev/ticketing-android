package ru.alekseevjk.ticketing.feature.airline.impl.presentation.search

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import ru.alekseevjk.ticketing.core.common.Resource
import ru.alekseevjk.ticketing.core.common.base.BaseFragment
import ru.alekseevjk.ticketing.core.di.findDependencies
import ru.alekseevjk.ticketing.feature.airline.impl.R
import ru.alekseevjk.ticketing.feature.airline.impl.databinding.FragmentSearchForCountryOfDepartureBinding
import ru.alekseevjk.ticketing.feature.airline.impl.di.DaggerAirlineComponent
import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.TicketOffer
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.search.entity.SearchForCountryOfDeparture
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.search.mapper.mapToPopularDestinationWithImage
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.search.rv.adapter.TicketOfferAdapter
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.tickets.AllTicketsFragment
import java.util.Locale
import javax.inject.Inject

class SearchForCountryOfDepartureFragment(
    private val navigateToFilters: () -> Unit
) : BaseFragment<FragmentSearchForCountryOfDepartureBinding>(
    FragmentSearchForCountryOfDepartureBinding::inflate
) {
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
        initializeViewModel()
    }

    private fun initializeViewModel() {
        searchForCountryOfDepartureViewModel = ViewModelProvider(this, viewModelFactory)
            .get(SearchForCountryOfDepartureViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        initListeners()
        observeViewState()
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
            lifecycleScope.launch {
                searchForCountryOfDepartureViewModel.changeTown()
            }
        }
        setOnReturnBackDateClickListener()
        setOnDepartureDateClickListener()
        binding!!.openAllTicketsBtn.setOnClickListener {
            navigateToAllTicketsFragment()
        }
    }

    private fun navigateToAllTicketsFragment() {
        val currentState = searchForCountryOfDepartureViewModel.viewState.value
        val args = Bundle().apply {
            putParcelable(
                AllTicketsFragment.SEARCH_FOR_COUNTRY_OF_DEPARTURE_ARGUMENT,
                currentState
            )
        }
        findNavController().navigate(
            R.id.action_searchForCountryOfDepartureFragment_to_allTicketsFragment,
            args
        )
    }

    private fun setOnDepartureDateClickListener() {
        binding!!.dateChip.setOnClickListener {
            showDatePickerDialog { date ->
                lifecycleScope.launch {
                    searchForCountryOfDepartureViewModel.setDateDeparture(date)
                }
            }
        }
    }

    private fun setOnReturnBackDateClickListener() {
        binding!!.returnDateChip.setOnClickListener {
            showDatePickerDialog { date ->
                lifecycleScope.launch {
                    searchForCountryOfDepartureViewModel.setDateReturnBack(date)
                }
            }
        }
    }

    private fun showDatePickerDialog(onDateSetListener: (LocalDate) -> Unit) {
        val currentState = searchForCountryOfDepartureViewModel.viewState.value
        val defaultYear = currentState.dateDeparture.year
        val defaultMonth =
            currentState.dateDeparture.monthValue - 1
        val defaultDay = currentState.dateDeparture.dayOfMonth

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                val selectedDate = LocalDate.of(year, monthOfYear + 1, dayOfMonth)
                onDateSetListener(selectedDate)
            }

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            dateSetListener,
            defaultYear,
            defaultMonth,
            defaultDay
        )
        datePickerDialog.show()
    }

    private fun observeViewState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchForCountryOfDepartureViewModel.viewState.collect { state ->
                    updateViewState(state)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchForCountryOfDepartureViewModel.ticketsOffersState.collect { state ->
                    handleTicketOffersState(state)
                }
            }
        }
    }

    private fun updateViewState(state: SearchForCountryOfDeparture) {
        binding!!.apply {
            searchPanel3.originTV.text = state.departureTown
            searchPanel3.destinationTV.text = state.arrivalTown
            classChip.text = state.classType

            val dateFormatter = DateTimeFormatter.ofPattern(
                getString(ru.alekseevjk.ticketing.design.R.string.date_format_2),
                Locale("ru")
            )
            updateDateChip(dateChip, state.dateDeparture, dateFormatter)
            updateDateChip(returnDateChip, state.dateReturnBack, dateFormatter)
        }
    }

    private fun updateDateChip(chip: Chip, date: LocalDate?, formatter: DateTimeFormatter) {
        if (date != null) {
            val formattedDate = date.format(formatter).replace(".", "")
            val spannable = SpannableString(formattedDate)
            val start = spannable.indexOf(',')
            val end = formattedDate.length
            spannable.setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        requireContext(),
                        ru.alekseevjk.ticketing.design.R.color.grey_6
                    )
                ),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            chip.text = spannable
            chip.isChipIconVisible = false
        } else {
            chip.text =
                getString(ru.alekseevjk.ticketing.design.R.string.return_date_chip_placeholder)
            chip.isChipIconVisible = true
        }
    }

    private fun handleTicketOffersState(state: Resource<List<TicketOffer>>) {
        when (state) {
            is Resource.Success -> {
                ticketOfferAdapter.submitList(
                    state.data.mapToPopularDestinationWithImage(
                        requireContext()
                    )
                )
            }

            is Resource.Loading -> {
            }

            is Resource.Failure -> {
                Toast.makeText(
                    requireContext(),
                    state.resourceException.localizedMessage,
                    Toast.LENGTH_SHORT
                ).show()
            }

            Resource.Idle -> TODO()
        }
    }

    companion object {
        const val DEPARTURE_TOWN_ARGUMENT = "departure_town"
        const val ARRIVAL_TOWN_ARGUMENT = "arrival_town"
    }
}