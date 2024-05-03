package com.application.tripapp.ui.saved.asteroids

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.tripapp.R
import com.application.tripapp.databinding.FragmentSavedAsteroidsBinding
import com.application.tripapp.db.AsteroidEntity
import com.application.tripapp.ui.saved.asteroids.adapter.SavedAsteroidsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SavedAsteroidsFragment : Fragment() {
    private var binding: FragmentSavedAsteroidsBinding? = null
    private val viewModel: SavedAsteroidsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedAsteroidsBinding.inflate(layoutInflater)
        return binding?.root
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is SavedAsteroidsState.AsteroidsLoaded -> {
                            state.asteroids.let {
                                it?.let { it1 -> setList(it1) }
                            }
                            binding?.savedPictures?.setOnClickListener{
                                findNavController().navigate(R.id.action_savedAsteroidsFragment_to_savedPicturesFragment)
                            }
                        }

                        is SavedAsteroidsState.Error -> {

                        }

                        else -> {

                        }
                    }

                }
            }
        }
        viewModel.processAction(SavedAsteroidsAction.Load, 1L)

    }

    private fun setList(list: List<AsteroidEntity>) {
        binding?.recyclerView?.run {
            if (adapter == null) {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = SavedAsteroidsAdapter { id ->
                    viewModel.processAction(SavedAsteroidsAction.DeleteAsteroid,id)
                }
            }
            (adapter as? SavedAsteroidsAdapter)?.submitList(list)
        }
    }
}