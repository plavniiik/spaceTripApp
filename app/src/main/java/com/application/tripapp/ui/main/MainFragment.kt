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
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.application.tripapp.R
import com.application.tripapp.databinding.FragmentMainBinding
import com.application.tripapp.model.Picture
import com.application.tripapp.ui.asteroids.AsteroidsFragmentDirections
import com.application.tripapp.ui.main.images.ImagesFragmentDirections
import com.application.tripapp.ui.main.images.adapter.ImageAdapter
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
                                                .error(
                                                    Glide.with(it1.context)
                                                        .load("https://hightech.fm/wp-content/uploads/2023/02/8888889.jpg")
                                                )
                                                .into(it)
                                        }
                                    }
                                }
                                toDiscover.setOnClickListener {
                                    findNavController().navigate(R.id.action_mainFragment_to_pictureOfTheDayFragment)
                                }

                                button.setOnClickListener {
                                    val action =
                                        MainFragmentDirections.actionMainFragmentToImagesFragment(
                                            inputSearch.text.toString()
                                        )
                                    findNavController().navigate(action)
                                }
                            }
                        }

                        is MainState.PictureError -> {
                            Toast.makeText(requireContext(), state.str, Toast.LENGTH_LONG).show()
                        }

                        else -> {}
                    }
                }

            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateGalaxy.collect { pagingData ->
                    setList(pagingData, binding?.recyclerView)
                }

            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateAstronomy.collect { pagingData ->
                    setList(pagingData, binding?.recyclerView2)
                }
            }
        }
        viewModel.processAction(MainAction.LoadPicture)

    }

    private suspend fun setList(pagingData: PagingData<Picture>, recyclerView: RecyclerView?) {
        recyclerView?.run {
            if (adapter == null) {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = ImageAdapter { idPic ->
                    val action = MainFragmentDirections.actionMainFragmentToImagePageFragment(idPic)
                    findNavController().navigate(action)
                }
            }
            (adapter as? ImageAdapter)?.submitData(pagingData)
        }
    }
}
