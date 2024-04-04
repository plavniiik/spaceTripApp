package com.application.tripapp.ui.science

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.application.tripapp.databinding.FragmentRepositoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScienceRepositoryFragment : Fragment() {
    private var binding: FragmentRepositoryBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRepositoryBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}
