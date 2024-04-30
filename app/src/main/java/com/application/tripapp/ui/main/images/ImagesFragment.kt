package com.application.tripapp.ui.main.images

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
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.application.tripapp.R
import com.application.tripapp.databinding.FragmentImagesBinding
import com.application.tripapp.model.Picture
import com.application.tripapp.ui.asteroids.AsteroidsFragmentDirections
import com.application.tripapp.ui.main.images.adapter.ImageAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImagesFragment : Fragment() {
    private var binding: FragmentImagesBinding? = null
    private val viewModel: ImageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImagesBinding.inflate(layoutInflater)
        return binding?.root
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val keyWord = arguments?.getString("keyWord")
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is ImageState.PicturesLoaded -> {
                            setList(state.pictures)
                        }
                        is ImageState.PicturesError -> {
                            Toast.makeText(requireContext(), state.str, Toast.LENGTH_LONG).show()
                        }

                        else -> {}
                    }
                }
            }
        }
        viewModel.processAction(ImageAction.LoadPictures(keyWord))
    }

    private suspend fun setList(pagingData: PagingData<Picture>) {
        binding?.recyclerView?.run {
            if (adapter == null) {
                layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                adapter = ImageAdapter { idPic ->
                    val action = ImagesFragmentDirections.actionImagesFragmentToImagePageFragment(idPic)
                    findNavController().navigate(action)
                }
            }
            (adapter as? ImageAdapter)?.submitData(pagingData)
        }
    }
}
