package com.application.tripapp.ui.asteroids

import android.annotation.SuppressLint
import android.app.DatePickerDialog
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
import com.application.noteproject.utils.validation.CalendarHelper
import com.application.tripapp.R
import com.application.tripapp.databinding.FragmentAsteroidsBinding
import com.application.tripapp.model.Asteroid
import com.application.tripapp.model.MarsImage
import com.application.tripapp.ui.mars.MarsRoverAction
import com.application.tripapp.ui.mars.MarsRoverState
import com.application.tripapp.ui.mars.MarsRoverViewModel
import com.application.tripapp.ui.mars.adapter.AsteroidsAdapter
import com.application.tripapp.ui.mars.adapter.MarsRoverAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar

@AndroidEntryPoint
class AsteroidsFragment : Fragment() {
    private var binding: FragmentAsteroidsBinding? = null
    private val viewModel: AsteroidViewModel by viewModels()
    private val calendarHelper: CalendarHelper<FragmentAsteroidsBinding> by lazy {
        CalendarHelper(requireContext(), binding)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAsteroidsBinding.inflate(layoutInflater)
        return binding?.root
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is AsteroidState.AsteroidLoaded -> {
                            state.asteroids.let {
                                binding?.progressBar?.visibility = View.GONE
                                it?.let { list ->
                                    if (list.isEmpty()) {
                                        setList(emptyList())
                                        binding?.check?.visibility = View.VISIBLE
                                    } else {
                                        setList(list)
                                        binding?.check?.visibility = View.GONE
                                    }
                                }
                            }
                        }

                        is AsteroidState.AsteroidsError -> {
                            binding?.check?.visibility = View.VISIBLE
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
        binding?.calendarEnd?.setOnClickListener(binding?.inputSearchEnd?.let {
            calendarHelper.getEndDatePicker(
                it
            )
        })

        binding?.button?.setOnClickListener {
            binding?.progressBar?.visibility = View.VISIBLE
            binding?.check?.visibility = View.GONE
            val inputDate = binding?.inputSearch?.text.toString()
            val inputDateEnd = binding?.inputSearchEnd?.text.toString()
            if (inputDate.isNotEmpty()) {
                viewModel.processAction(AsteroidAction.LoadAsteroid, inputDate, inputDateEnd)
            } else {

            }
        }
    }

    private fun setList(list: List<Asteroid>) {
        binding?.recyclerView?.run {
            if (adapter == null) {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = AsteroidsAdapter { id ->
                    val action =
                        AsteroidsFragmentDirections.actionAsteroidsFragmentToAsteroidPageFragment(id)
                    findNavController().navigate(action)
                }
            }
            (adapter as? AsteroidsAdapter)?.submitList(list)
        }
    }
}