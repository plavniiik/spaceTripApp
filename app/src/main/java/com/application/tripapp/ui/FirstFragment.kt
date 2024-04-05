package com.application.tripapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.application.tripapp.R
import com.application.tripapp.repository.FireBaseRepository
import com.application.tripapp.repository.SharedPreferencesRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FirstFragment : Fragment() {

    @Inject
    lateinit var sharedPreferencesRepository: SharedPreferencesRepository

    @Inject
    lateinit var fireBaseRepository: FireBaseRepository
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return View(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when {
            sharedPreferencesRepository.isFirstLaunch() -> {
                findNavController().navigate(R.id.action_firstFragment_to_startFragment)
                sharedPreferencesRepository.setIsFirstLaunch()
            }

            fireBaseRepository.isUserLoggedIn() -> {
                findNavController().navigate(R.id.action_firstFragment_to_menuFragment)
            }

            else -> {
                findNavController().navigate(R.id.action_firstFragment_to_loginFragment)
            }
        }
    }
}