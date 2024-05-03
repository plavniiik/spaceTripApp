package com.application.tripapp.ui.mars

import android.R.attr.button
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.noteproject.utils.validation.CalendarHelper
import com.application.tripapp.databinding.FragmentMarsRoversBinding
import com.application.tripapp.model.MarsImage
import com.application.tripapp.ui.mars.adapter.AsteroidsAdapter
import com.application.tripapp.ui.mars.adapter.MarsRoverAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar


@AndroidEntryPoint
class MarsFragment : Fragment() {
    private var binding: FragmentMarsRoversBinding? = null
    private val viewModel: MarsRoverViewModel by viewModels()
    private val calendarHelper: CalendarHelper<FragmentMarsRoversBinding> by lazy {
        CalendarHelper(requireContext(), binding)}

        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMarsRoversBinding.inflate(layoutInflater)
        return binding?.root
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.progressBar?.visibility = View.GONE
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is MarsRoverState.PicturesLoaded -> {

                            state.pictures.let {
                                if (it.isEmpty()) {
                                    setList(emptyList())
                                    binding?.check?.visibility = View.VISIBLE
                                } else {
                                    setList(it)
                                    binding?.check?.visibility = View.GONE
                                }
                            }
                            binding?.progressBar?.visibility = View.GONE
                        }

                        is MarsRoverState.PicturesError -> {
                            binding?.progressBar?.visibility = View.GONE
                        }

                        is MarsRoverState.Loading -> {

                        }

                        else -> {

                        }
                    }

                }
            }
        }
        binding?.calendar?.setOnClickListener(binding?.inputSearch?.let {
            calendarHelper.getStartDatePicker(
                it
            )
        })


        binding?.button?.setOnClickListener {
            binding?.check?.visibility = View.GONE
            binding?.progressBar?.visibility = View.VISIBLE
            val inputDate = binding?.inputSearch?.text.toString()
            if (inputDate.isNotEmpty()) {
                viewModel.processAction(MarsRoverAction.LoadPicture, inputDate)
            } else {

            }
        }
    }

    private fun setList(list: List<MarsImage>) {

        binding?.check?.visibility = View.GONE
        binding?.recyclerView?.run {
            if (adapter == null) {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = MarsRoverAdapter { idPic ->
                }
            }
            (adapter as? MarsRoverAdapter)?.submitList(list)
        }
    }
}


