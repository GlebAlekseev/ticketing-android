package ru.alekseevjk.ticketing.feature.airline.impl.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.alekseevjk.ticketing.core.di.findDependencies
import ru.alekseevjk.ticketing.feature.airline.impl.R
import ru.alekseevjk.ticketing.feature.airline.impl.di.DaggerAirlineComponent
import javax.inject.Inject


class AirlineMainFragment(
    private val navigateToFilters: () -> Unit
) : Fragment() {
    @Inject
    lateinit var airlineFragmentFactory: AirlineFragmentFactory.AssistedFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerAirlineComponent.factory().create(findDependencies()).inject(this)
        parentFragmentManager.fragmentFactory = airlineFragmentFactory.create(navigateToFilters)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_airline_main, container, false)
    }
}