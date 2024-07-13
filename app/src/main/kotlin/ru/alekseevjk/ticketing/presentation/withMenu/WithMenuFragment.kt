package ru.alekseevjk.ticketing.presentation.withMenu

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.alekseevjk.ticketing.R
import ru.alekseevjk.ticketing.appComponent
import ru.alekseevjk.ticketing.core.common.base.BaseFragment
import ru.alekseevjk.ticketing.databinding.FragmentWithMenuBinding
import javax.inject.Inject

class WithMenuFragment(
    private val navigateToFilters: () -> Unit
) : BaseFragment<FragmentWithMenuBinding>(FragmentWithMenuBinding::inflate) {
    private lateinit var mNavHostFragment: NavHostFragment
    private lateinit var navController: NavController

    @Inject
    lateinit var withMenuFragmentFactory: WithMenuFragmentFactory.AssistedFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies(requireContext())
        setupFragmentFactory()
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavController()
        setupBottomNavigationView()
    }

    private fun injectDependencies(context: Context) {
        context.appComponent.inject(this)
    }

    private fun setupFragmentFactory() {
        parentFragmentManager.fragmentFactory = withMenuFragmentFactory.create(navigateToFilters)
    }

    private fun setupNavController() {
        mNavHostFragment =
            childFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController = mNavHostFragment.navController
    }

    private fun setupBottomNavigationView() {
        binding?.bottomNavigationView?.setupWithNavController(navController)
    }
}
