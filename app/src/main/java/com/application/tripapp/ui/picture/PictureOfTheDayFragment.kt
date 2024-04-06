package com.application.tripapp.ui.picture

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
import com.application.tripapp.R
import com.application.tripapp.databinding.FragmentPictureOfTheDayBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PictureOfTheDayFragment : Fragment() {
    private var binding: FragmentPictureOfTheDayBinding? = null
    private val viewModel: PictureViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPictureOfTheDayBinding.inflate(layoutInflater)
        return binding?.root
    }


    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is PictureState.PictureLoaded -> {
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
                                descriptionText.text = state.picture?.explanation
                                toHome.setOnClickListener {
                                    findNavController().navigate(R.id.action_pictureOfTheDayFragment_to_mainFragment)
                                }
                                like?.setOnClickListener {
                                    if (viewModel.isPictureAdded.value) {
                                        viewModel.processAction(PictureAction.DeletePicture(state.picture))
                                        like.setImageResource(R.drawable.heart)
                                    } else {
                                        viewModel.processAction(PictureAction.AddPicture(state.picture))
                                        like.setImageResource(R.drawable.full_heart)
                                    }
                                }
                            }
                        }

                        is PictureState.PictureAdded -> {
                            viewModel.isPictureAdded.value = true
                        }

                        is PictureState.PictureDeleted -> {
                            viewModel.isPictureAdded.value = false
                        }

                        is PictureState.PictureError -> {
                            Toast.makeText(requireContext(), state.str, Toast.LENGTH_LONG).show()
                        }

                        else -> {

                        }
                    }
                }
            }
        }

        viewModel.processAction(PictureAction.LoadPicture)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isPictureAdded.collect { isAdded ->
                    if (isAdded) {
                        binding?.like?.setImageResource(R.drawable.full_heart)
                    } else {
                        binding?.like?.setImageResource(R.drawable.heart)
                    }
                }
            }
        }
    }
}

