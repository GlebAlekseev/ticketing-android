package ru.alekseevjk.ticketing.presentation.withMenu

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.alekseevjk.ticketing.R
import ru.alekseevjk.ticketing.appComponent
import ru.alekseevjk.ticketing.databinding.FragmentWithMenuBinding
import javax.inject.Inject


class WithMenuFragment(
    private val navigateToFilters: () -> Unit
) : Fragment() {
    private var binding: FragmentWithMenuBinding? = null
    private lateinit var mNavHostFragment: NavHostFragment
    private lateinit var navController: NavController

    @Inject
    lateinit var withMenuFragmentFactory: WithMenuFragmentFactory.AssistedFactory

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        parentFragmentManager.fragmentFactory = withMenuFragmentFactory.create(navigateToFilters)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWithMenuBinding.inflate(layoutInflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mNavHostFragment =
            childFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController = mNavHostFragment.navController

        binding!!.bottomNavigationView.setupWithNavController(navController)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}