package com.application.tripapp.ui.science.payload

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.application.tripapp.R
import com.application.tripapp.databinding.FragmentPayloadBinding
import com.application.tripapp.databinding.FragmentRepositoryBinding
import com.application.tripapp.print.PrintAdapter
import com.application.tripapp.ui.science.ScienceViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.logging.Level.INFO


@AndroidEntryPoint
class PayloadFragment : Fragment() {
    private var binding: FragmentPayloadBinding? = null
    private val sharedViewModel: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPayloadBinding.inflate(layoutInflater)
        return binding?.root
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_payloadFragment_to_scienceRepositoryFragment)
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.selectedPayload.collect { payload ->

                    if (payload != null) {

                        binding?.descrip?.text = payload.description
                        binding?.titleP?.text = payload.payloadName
                        binding?.id?.text = payload.identifierLowercase
                        binding?.toHome?.setOnClickListener {
                            findNavController().navigate(R.id.action_payloadFragment_to_scienceRepositoryFragment)
                        }
                        binding?.toExport?.setOnClickListener {
                            val printManager = requireContext().getSystemService(Context.PRINT_SERVICE) as PrintManager
                            val jobName = getString(R.string.app_name) + " Document"
                            printManager.print(jobName, PrintAdapter(requireContext(), binding!!.root),null )
                        }
                    }

                }
            }
        }
    }
}
