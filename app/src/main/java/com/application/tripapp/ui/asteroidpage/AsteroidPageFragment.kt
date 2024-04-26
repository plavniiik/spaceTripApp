package com.application.tripapp.ui.asteroidpage

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
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
import com.application.tripapp.databinding.FragmentAsteroidsBinding
import com.application.tripapp.databinding.FragmentPageAsteroidBinding
import com.application.tripapp.model.Asteroid
import com.application.tripapp.network.asteroid.CloseApproachData
import com.application.tripapp.ui.asteroidpage.adapter.ApproachDateAdapter
import com.application.tripapp.ui.asteroids.AsteroidAction
import com.application.tripapp.ui.asteroids.AsteroidState
import com.application.tripapp.ui.asteroids.AsteroidViewModel
import com.application.tripapp.ui.mars.adapter.AsteroidsAdapter
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar

@AndroidEntryPoint
class AsteroidPageFragment : Fragment() {
    private var binding: FragmentPageAsteroidBinding? = null
    private val viewModel: AsteroidPageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPageAsteroidBinding.inflate(layoutInflater)
        return binding?.root
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getString("id")
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is AsteroidPageState.AsteroidLoaded -> {
                            binding?.run {
                                url.setOnClickListener {
                                    val openURL = Intent(Intent.ACTION_VIEW)
                                    openURL.data = Uri.parse(state.asteroid?.nasa_jpl_url)
                                    startActivity(openURL)
                                }
                                if(state.asteroid?.is_potentially_hazardous_asteroid==false){
                                   pottentHazardous.setImageResource(R.drawable.white_asteroid)
                                }
                                if(state.asteroid?.is_potentially_hazardous_asteroid==true){
                                    pottentHazardous.setImageResource(R.drawable.red_asteroid)
                                }
                                title?.text = state.asteroid?.name
                                toHome.setOnClickListener {
                                    findNavController().navigate(R.id.action_asteroidPageFragment_to_asteroidsFragment)
                                }
                                absoluteMagnitudeH.text =
                                   root.context.getString(R.string.magnitude) + state.asteroid?.absolute_magnitude_h.toString()
                                estimatedDiameter.text =
                                    root.context.getString(R.string.diameter) + state.asteroid?.estimated_diameter_max.toString() + "m"

                            }
                            state.asteroid?.close_approach_date.let {
                                it?.let { it1 -> setList(it1) }
                            }
                        }

                        is AsteroidPageState.AsteroidError -> {
                            Toast.makeText(requireContext(), state.str, Toast.LENGTH_LONG)
                                .show()
                        }

                        else -> {

                        }
                    }
                }
            }
        }

        id?.let { viewModel.processAction(AsteroidPageAction.LoadAsteroid, it) }

    }


    private fun setList(list: List<CloseApproachData>) {
        binding?.recyclerView?.run {
            if (adapter == null) {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = ApproachDateAdapter { idPic ->
                }
            }
            (adapter as? ApproachDateAdapter)?.submitList(list)
        }
    }
}
