package com.application.tripapp.ui.science

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.application.tripapp.R
import com.application.tripapp.databinding.FragmentRepositoryBinding
import com.application.tripapp.ui.asteroids.AsteroidAction
import com.application.tripapp.ui.asteroids.AsteroidState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ScienceRepositoryFragment : Fragment() {
    private var binding: FragmentRepositoryBinding? = null
    private val viewModel: ScienceViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRepositoryBinding.inflate(layoutInflater)
        return binding?.root
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is ScienceState.PayloadLoaded -> {
                            findNavController().navigate(R.id.action_scienceRepositoryFragment_to_payloadFragment)
                        }

                        is ScienceState.ScienceError -> {

                        }

                        else -> {

                        }
                    }

                }
            }
        }
        binding?.button?.setOnClickListener{
            viewModel.processAction(ScienceAction.LoadData,binding?.inputSearch?.text.toString())
        }
    }
}
