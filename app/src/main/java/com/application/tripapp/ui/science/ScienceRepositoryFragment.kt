package com.application.tripapp.ui.science

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.application.tripapp.R
import com.application.tripapp.databinding.FragmentRepositoryBinding
import com.application.tripapp.ui.asteroids.AsteroidAction
import com.application.tripapp.ui.asteroids.AsteroidState
import com.application.tripapp.ui.science.payload.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ScienceRepositoryFragment : Fragment() {
    private var binding: FragmentRepositoryBinding? = null
    private val viewModel: ScienceViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

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
                            state.payload?.let {
                                sharedViewModel.selectPayload(it)
                            }
                            findNavController().navigate(R.id.action_scienceRepositoryFragment_to_payloadFragment)
                        }

                        is ScienceState.ScienceError -> {
                            binding?.progressBar?.visibility = View.GONE
                            binding?.check?.visibility = View.VISIBLE
                        }

                        else -> {

                        }
                    }

                }
            }
        }
        binding?.button?.setOnClickListener {
            binding?.progressBar?.visibility = View.VISIBLE
            binding?.check?.visibility = View.GONE
            val inputText = binding?.inputSearch?.text.toString().toUpperCase().trim()
            viewModel.processAction(ScienceAction.LoadData, inputText)
        }
    }
}
