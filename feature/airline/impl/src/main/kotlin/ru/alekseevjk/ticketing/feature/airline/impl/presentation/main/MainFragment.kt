package ru.alekseevjk.ticketing.feature.airline.impl.presentation.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import ru.alekseevjk.ticketing.core.common.Resource
import ru.alekseevjk.ticketing.core.di.findDependencies
import ru.alekseevjk.ticketing.feature.airline.impl.R
import ru.alekseevjk.ticketing.feature.airline.impl.databinding.FragmentMainBinding
import ru.alekseevjk.ticketing.feature.airline.impl.di.DaggerAirlineComponent
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.main.rv.adapter.OffersAdapter
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.main.rv.decoration.OfferSpaceItemDecoration
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.main.rv.helper.StartSnapHelper
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.searchBottomSheet.SearchBottomSheetDialogFragment
import javax.inject.Inject


class MainFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var offersAdapter: OffersAdapter
    private lateinit var mainViewModel: MainViewModel
    private var binding: FragmentMainBinding? = null
    private val offerSpaceItemDecoration by lazy {
        OfferSpaceItemDecoration(this.requireContext(), 167)
    }
    private val startSnapHelper by lazy {
        StartSnapHelper()
    }

    override fun onAttach(context: Context) {
        DaggerAirlineComponent.factory().create(findDependencies()).inject(this)
        super.onAttach(context)
        mainViewModel =
            ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        initListeners()
        if (savedInstanceState == null) {
            loadLastDepartureTown()
        }
        observeStates()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun setupRecyclerView() {
        with(binding!!.offersSection.offersRV) {
            this.adapter = offersAdapter
            this.addItemDecoration(offerSpaceItemDecoration)
            startSnapHelper.attachToRecyclerView(this)
        }
    }

    private fun initListeners() {
        binding!!.searchPanel.destinationTV.setOnClickListener {
            findNavController().navigate(
                resId = R.id.action_mainFragment_to_searchBottomSheetDialogFragment,
                args = Bundle().apply {
                    this.putString(
                        SearchBottomSheetDialogFragment.DEPARTURE_TOWN_ARGUMENT,
                        binding!!.searchPanel.originET.text.toString()
                    )
                }
            )
        }
        binding!!.searchPanel.originET.addTextChangedListener {
            mainViewModel.setLastDepartureTown(it.toString())
        }
        with(binding!!.searchPanel.originET) {
            this.setOnEditorActionListener { v, actionId, event ->
                if (actionId ==EditorInfo.IME_ACTION_NEXT) {
                    val inputText = this.text.toString()
                    findNavController().navigate(
                        resId = R.id.action_mainFragment_to_searchBottomSheetDialogFragment,
                        args = Bundle().apply {
                            this.putString(
                                SearchBottomSheetDialogFragment.DEPARTURE_TOWN_ARGUMENT,
                                inputText
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

    private fun loadLastDepartureTown() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                val initValue = mainViewModel.getLastDepartureTownUseCase()
                with(binding!!.searchPanel.originET) {
                    if (this.text.isEmpty()) {
                        this.setText(initValue)
                    }
                }
            }
        }
    }

    private fun observeStates() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.offers.collect {
                    when (val result = it) {
                        is Resource.Failure -> {
                            Toast.makeText(
                                this@MainFragment.requireContext(),
                                "Ошибка: ${result.resourceException}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        Resource.Idle -> {}
                        Resource.Loading -> {}
                        is Resource.Success -> {
                            offersAdapter.submitList(result.data)
                        }
                    }
                }
            }
        }
    }
}