package com.application.tripapp.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.application.tripapp.R
import com.application.tripapp.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var binding: FragmentProfileBinding? = null
    private val viewModel: ProfileViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.run {

            logoutTitle?.setOnClickListener {
                requireActivity().findNavController(R.id.main_nav).apply {
                    navigate(R.id.action_menuFragment_to_loginFragment)

                }
                viewModel.logout()
            }

            myTitle.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_savedPicturesFragment)
            }
        }
    }
}