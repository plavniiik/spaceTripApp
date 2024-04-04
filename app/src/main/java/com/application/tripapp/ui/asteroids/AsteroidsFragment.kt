package com.application.tripapp.ui.asteroids

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.application.tripapp.databinding.FragmentAsteroidsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AsteroidsFragment : Fragment() {
    private var binding: FragmentAsteroidsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAsteroidsBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}
