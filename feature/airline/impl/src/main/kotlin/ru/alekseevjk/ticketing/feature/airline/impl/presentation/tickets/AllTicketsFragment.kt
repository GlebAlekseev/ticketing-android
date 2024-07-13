package ru.alekseevjk.ticketing.feature.airline.impl.presentation.tickets

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import org.threeten.bp.format.DateTimeFormatter
import ru.alekseevjk.ticketing.core.common.Resource
import ru.alekseevjk.ticketing.core.common.base.BaseFragment
import ru.alekseevjk.ticketing.core.di.findDependencies
import ru.alekseevjk.ticketing.design.R
import ru.alekseevjk.ticketing.feature.airline.impl.databinding.FragmentAllTicketsBinding
import ru.alekseevjk.ticketing.feature.airline.impl.di.DaggerAirlineComponent
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.search.entity.SearchForCountryOfDeparture
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.tickets.mapper.toTicketWithTime
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.tickets.rv.adapter.TicketAdapter
import java.util.Locale
import javax.inject.Inject

class AllTicketsFragment : BaseFragment<FragmentAllTicketsBinding>(
    FragmentAllTicketsBinding::inflate
) {
    @Inject
    lateinit var ticketAdapter: TicketAdapter

    @Inject
    lateinit var allTicketsViewModelAssistedFactory: AllTicketsViewModel.AssistedFactory

    private val searchForCountryOfDeparture: SearchForCountryOfDeparture by lazy {
        requireArguments().getParcelable(
            SEARCH_FOR_COUNTRY_OF_DEPARTURE_ARGUMENT
        )!!
    }

    private val viewModelFactory by lazy {
        AllTicketsViewModel.provideFactory(
            allTicketsViewModelAssistedFactory,
            searchForCountryOfDeparture
        )
    }

    private lateinit var allTicketsViewModel: AllTicketsViewModel

    override fun onAttach(context: Context) {
        DaggerAirlineComponent.factory().create(findDependencies()).inject(this)
        super.onAttach(context)
        allTicketsViewModel =
            ViewModelProvider(this, viewModelFactory)[AllTicketsViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        setupRecyclerView()
        initListeners()
        observeViewState()
    }

    private fun loadData() {
        val state = allTicketsViewModel.viewState.value

        val pattern = DateTimeFormatter.ofPattern(getString(R.string.date_format), Locale.getDefault())
        val dateDepartureText = state.dateDeparture.format(pattern)
        binding!!.departureDateAndCountTV.text = String.format(
            format = getString(R.string.departure_date_and_passenger_count),
            dateDepartureText,
            state.classType.split(",").first().trim().toInt()
        )
        val departureAndArrivalTown = "${state.departureTown}-${state.arrivalTown}"
        binding!!.departureAndArrivalTownTV.text = departureAndArrivalTown
    }

    private fun setupRecyclerView() {
        binding!!.ticketRV.adapter = ticketAdapter
    }

    private fun initListeners() {
        binding!!.closeIB.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeViewState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                allTicketsViewModel.tickets.collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            ticketAdapter.submitList(resource.data.map { it.toTicketWithTime() })
                        }
                        is Resource.Failure -> {
                            Toast.makeText(
                                requireContext(),
                                resource.resourceException.localizedMessage ?: getString(R.string.unknown_error),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is Resource.Loading -> {
                        }

                        Resource.Idle -> {}
                    }
                }
            }
        }
    }

    companion object {
        const val SEARCH_FOR_COUNTRY_OF_DEPARTURE_ARGUMENT = "search_for_country_of_departure"
    }
}
