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
                                it?.let { it1 -> setList(it1) }
                            }
                        }

                        is AsteroidState.AsteroidsError -> {

                        }

                        else -> {

                        }
                    }

                }
            }
        }
        viewModel.processAction(AsteroidAction.LoadAsteroid, "2015-6-3", "2015-6-4")

        binding?.calendar?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val c: Calendar = Calendar.getInstance()
                val mYear: Int = c.get(Calendar.YEAR)
                val mMonth: Int = c.get(Calendar.MONTH)
                val mDay: Int = c.get(Calendar.DAY_OF_MONTH)
                val datePickerDialog = DatePickerDialog(
                    requireContext(),
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        binding?.inputSearch?.setText(
                            "${year}-${monthOfYear + 1}-${dayOfMonth}"
                        )
                    }, mYear, mMonth, mDay
                )
                datePickerDialog.show()
            }
        })

        binding?.calendarEnd?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val c: Calendar = Calendar.getInstance()
                val mYear: Int = c.get(Calendar.YEAR)
                val mMonth: Int = c.get(Calendar.MONTH)
                val mDay: Int = c.get(Calendar.DAY_OF_MONTH)
                val datePickerDialog = DatePickerDialog(
                    requireContext(),
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        binding?.inputSearchEnd?.setText(
                            "${year}-${monthOfYear + 1}-${dayOfMonth}"
                        )
                    }, mYear, mMonth, mDay
                )
                datePickerDialog.show()
            }
        })

        binding?.button?.setOnClickListener {
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
                    val action = AsteroidsFragmentDirections.actionAsteroidsFragmentToAsteroidPageFragment(id)
                    findNavController().navigate(action)
                }
            }
            (adapter as? AsteroidsAdapter)?.submitList(list)
        }
    }
}