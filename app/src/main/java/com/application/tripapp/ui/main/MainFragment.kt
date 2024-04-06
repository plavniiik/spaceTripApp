package com.application.tripapp.ui.main

import android.animation.AnimatorInflater
import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
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
import com.application.tripapp.R
import com.application.tripapp.databinding.FragmentMainBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class MainFragment : Fragment() {
    private var binding: FragmentMainBinding? = null
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding?.root
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is MainState.PictureLoaded -> {
                            binding?.run {
                                title?.text = state.picture?.title
                                image.let {
                                    if (it != null) {
                                        binding?.root?.let { it1 ->
                                            Glide.with(it1.context)
                                                .load(state.picture?.url)
                                                .into(it)
                                        }
                                    }
                                }
                                toDiscover.setOnClickListener {
                                    findNavController().navigate(R.id.action_mainFragment_to_pictureOfTheDayFragment)
                                }
                            }
                        }
                        is MainState.PictureError -> {
                            Toast.makeText(requireContext(), state.str, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
        viewModel.processAction(MainAction.LoadPicture)
    }
}
