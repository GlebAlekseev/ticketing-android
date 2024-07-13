package ru.alekseevjk.ticketing.feature.airline.impl.presentation.main

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import ru.alekseevjk.ticketing.core.common.Resource
import ru.alekseevjk.ticketing.core.common.base.BaseFragment
import ru.alekseevjk.ticketing.core.di.findDependencies
import ru.alekseevjk.ticketing.feature.airline.impl.R
import ru.alekseevjk.ticketing.feature.airline.impl.databinding.FragmentMainBinding
import ru.alekseevjk.ticketing.feature.airline.impl.di.DaggerAirlineComponent
import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.Offer
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.main.mapper.toPopularDestinationWithImage
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.main.rv.adapter.OffersAdapter
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.main.rv.decoration.OfferSpaceItemDecoration
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.main.rv.helper.StartSnapHelper
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.searchBottomSheet.SearchBottomSheetDialogFragment
import javax.inject.Inject

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var offersAdapter: OffersAdapter

    private lateinit var mainViewModel: MainViewModel

    private val offerSpaceItemDecoration by lazy {
        val offset =
            resources.getDimensionPixelSize(ru.alekseevjk.ticketing.design.R.dimen.offer_space_item_decoration)
        OfferSpaceItemDecoration(requireContext(), offset)
    }

    private val startSnapHelper by lazy {
        StartSnapHelper()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectDependencies()
        initializeViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        initListeners()
        if (savedInstanceState == null) {
            loadLastDepartureTown()
        }
        observeOffers()
    }

    private fun injectDependencies() {
        DaggerAirlineComponent.factory().create(findDependencies()).inject(this)
    }

    private fun initializeViewModel() {
        mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    private fun setupRecyclerView() {
        binding?.offersSection?.offersRV?.apply {
            adapter = offersAdapter
            addItemDecoration(offerSpaceItemDecoration)
            startSnapHelper.attachToRecyclerView(this)
        }
    }

    private fun initListeners() {
        binding?.searchPanel?.apply {
            destinationTV.setOnClickListener {
                navigateToSearchFragment()
            }
            originET.addTextChangedListener {
                lifecycleScope.launch {
                    mainViewModel.setLastDepartureTown(it.toString())
                }
            }
            originET.setOnEditorActionListener { _, actionId, _ ->
                handleEditorAction(actionId)
            }
        }
    }

    private fun navigateToSearchFragment() {
        val departureTown = binding?.searchPanel?.originET?.text.toString()
        findNavController().navigate(
            R.id.action_mainFragment_to_searchBottomSheetDialogFragment,
            Bundle().apply {
                putString(SearchBottomSheetDialogFragment.DEPARTURE_TOWN_ARGUMENT, departureTown)
            }
        )
    }

    private fun handleEditorAction(actionId: Int): Boolean {
        return if (actionId == EditorInfo.IME_ACTION_NEXT) {
            navigateToSearchFragment()
            true
        } else {
            false
        }
    }

    private fun loadLastDepartureTown() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                val initValue = mainViewModel.getLastDepartureTownUseCase()
                binding?.searchPanel?.originET?.apply {
                    if (text.isEmpty()) {
                        setText(initValue)
                    }
                }
            }
        }
    }

    private fun observeOffers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.offers.collect { result ->
                    handleOffersResult(result)
                }
            }
        }
    }

    private fun handleOffersResult(result: Resource<List<Offer>>) {
        when (result) {
            is Resource.Failure -> {
                Toast.makeText(
                    requireContext(),
                    "Ошибка: ${result.resourceException}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            Resource.Idle -> {
            }

            Resource.Loading -> {
            }

            is Resource.Success -> {
                offersAdapter.submitList(result.data.map { it.toPopularDestinationWithImage() })
            }
        }
    }
}
