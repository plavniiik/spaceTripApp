package com.application.tripapp.ui.mars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.application.tripapp.databinding.FragmentMarsRoversBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MarsFragment : Fragment() {
    private var binding: FragmentMarsRoversBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMarsRoversBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}
