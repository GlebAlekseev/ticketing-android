package ru.alekseevjk.ticketing.feature.airline.impl.presentation.plug

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import ru.alekseevjk.ticketing.core.common.base.BaseFragment
import ru.alekseevjk.ticketing.feature.airline.impl.databinding.FragmentPlugBinding


class PlugFragment : BaseFragment<FragmentPlugBinding>(FragmentPlugBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        binding!!.backIB.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}