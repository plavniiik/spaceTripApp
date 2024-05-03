package com.application.tripapp.ui.saved.pictures

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.tripapp.R
import com.application.tripapp.databinding.FragmentSavedPicturesBinding
import com.application.tripapp.db.PictureEntity
import com.application.tripapp.ui.saved.asteroids.SavedAsteroidsAction
import com.application.tripapp.ui.saved.pictures.adapter.SavedPicturesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SavedPicturesFragment : Fragment() {

    private var binding: FragmentSavedPicturesBinding? = null
    private val viewModel: SavedPictureViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedPicturesBinding.inflate(inflater)
        return binding?.root
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect(){state ->
                    when (state) {
                        is SavedPicturesState.PicturesLoaded -> {
                            state.pictures.let {
                                setList(it)
                            }
                            binding?.savedAsteroids?.setOnClickListener{
                                findNavController().navigate(R.id.action_savedPicturesFragment_to_savedAsteroidsFragment)
                            }
                        }

                        is SavedPicturesState.Error -> {
                            Toast.makeText(requireContext(), state.error, Toast.LENGTH_LONG).show()
                        }

                        else -> {

                        }
                    }

                }
            }
        }

        viewModel.processAction(SavedPicturesAction.Load,"")

    }

    private fun setList(list: List<PictureEntity>) {
        binding?.recyclerView?.run {
            if (adapter == null) {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = SavedPicturesAdapter { idPic ->
                    viewModel.processAction(SavedPicturesAction.DeletePictures,idPic)
                }
            }
            (adapter as? SavedPicturesAdapter)?.submitList(list)
        }
    }
}