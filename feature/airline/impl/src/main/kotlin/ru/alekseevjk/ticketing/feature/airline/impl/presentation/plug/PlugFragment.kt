package ru.alekseevjk.ticketing.feature.airline.impl.presentation.plug

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.alekseevjk.ticketing.feature.airline.impl.databinding.FragmentPlugBinding


class PlugFragment : Fragment() {
    private var binding: FragmentPlugBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlugBinding.inflate(layoutInflater)
        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


    private fun initListeners() {
        binding!!.backIB.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}