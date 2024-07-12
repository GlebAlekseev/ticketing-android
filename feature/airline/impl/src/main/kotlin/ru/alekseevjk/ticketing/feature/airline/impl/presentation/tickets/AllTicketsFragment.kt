package ru.alekseevjk.ticketing.feature.airline.impl.presentation.tickets

import android.content.Context
import android.os.Bundle
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
import org.threeten.bp.format.DateTimeFormatter
import ru.alekseevjk.ticketing.core.common.Resource
import ru.alekseevjk.ticketing.core.di.findDependencies
import ru.alekseevjk.ticketing.feature.airline.impl.databinding.FragmentAllTicketsBinding
import ru.alekseevjk.ticketing.feature.airline.impl.di.DaggerAirlineComponent
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.search.entity.SearchForCountryOfDeparture
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.tickets.mapper.toTicketWithTime
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.tickets.rv.adapter.TicketAdapter
import java.util.Locale
import javax.inject.Inject


class AllTicketsFragment : Fragment() {
    private var binding: FragmentAllTicketsBinding? = null
    private val searchForCountryOfDeparture: SearchForCountryOfDeparture by lazy {
        requireArguments().getParcelable(
            SEARCH_FOR_COUNTRY_OF_DEPARTURE_ARGUMENT
        )!!
    }

    @Inject
    lateinit var ticketAdapter: TicketAdapter

    @Inject
    lateinit var allTicketsViewModelAssistedFactory: AllTicketsViewModel.AssistedFactory
    private val viewModelFactory by lazy {
        AllTicketsViewModel.provideFactory(
            allTicketsViewModelAssistedFactory,
            searchForCountryOfDeparture,
        )
    }
    private lateinit var allTicketsViewModel: AllTicketsViewModel


    override fun onAttach(context: Context) {
        DaggerAirlineComponent.factory().create(findDependencies()).inject(this)
        super.onAttach(context)
        allTicketsViewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            )[AllTicketsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllTicketsBinding.inflate(layoutInflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        setupRecyclerView()
        initListeners()
        observeViewState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun loadData() {
        val state = allTicketsViewModel.viewState.value

        val pattern = DateTimeFormatter.ofPattern("dd MMMM", Locale("ru"))

        val dateDepartureText = state.dateDeparture.format(pattern)
        binding!!.departureDateAndCountTV.text =
            "$dateDepartureText, ${state.classType.split(",").first().trim().toInt()} пассажир"

        val departureAndArrivalTown = state.departureTown + "-" + state.arrivalTown
        binding!!.departureAndArrivalTownTV.text = departureAndArrivalTown
    }

    private fun setupRecyclerView() {
        with(binding!!.ticketRV) {
            this.adapter = ticketAdapter
        }
    }

    private fun initListeners() {
        binding!!.closeIB.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun observeViewState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                allTicketsViewModel.tickets.collect {
                    when (val state = it) {
                        is Resource.Success -> {
                            ticketAdapter.submitList(
                                state.data.map { it.toTicketWithTime() }
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
        const val SEARCH_FOR_COUNTRY_OF_DEPARTURE_ARGUMENT = "search_for_country_of_departure"
    }
}