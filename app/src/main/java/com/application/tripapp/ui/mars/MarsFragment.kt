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
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect{state ->
                    when (state) {
                        is MarsRoverState.PicturesLoaded -> {
                            state.pictures.let {
                                setList(it)
                            }
                        }

                        is MarsRoverState.PicturesError -> {

                        }

                        else -> {

                        }
                    }

                }
            }
        }
        viewModel.processAction(MarsRoverAction.LoadPicture,"2015-6-3")

        binding?.calendar?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val c: Calendar = Calendar.getInstance()
                val mYear: Int = c.get(Calendar.YEAR)
                val mMonth: Int = c.get(Calendar.MONTH)
                val mDay: Int = c.get(Calendar.DAY_OF_MONTH)
                val datePickerDialog = DatePickerDialog(requireContext(),
                    OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        binding?.inputSearch?.setText(
                            "${year}-${monthOfYear + 1}-${dayOfMonth}"
                        )
                    }, mYear, mMonth, mDay
                )
                datePickerDialog.show()
            }
        })

        binding?.button?.setOnClickListener {
            val inputDate = binding?.inputSearch?.text.toString()
            if (inputDate.isNotEmpty()) {
                viewModel.processAction(MarsRoverAction.LoadPicture, inputDate)
            } else {

            }
        }
    }

    private fun setList(list: List<MarsImage>) {
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

