package com.application.tripapp.ui.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.application.tripapp.R
import com.application.tripapp.databinding.FragmentLoginBinding
import com.application.tripapp.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var binding: FragmentLoginBinding? = null
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var callback: OnBackPressedCallback
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding?.root
    }


    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is LoginState.LoginSuccess -> {
                            findNavController().navigate(R.id.action_loginFragment_to_menuFragment)
                        }

                        is LoginState.Error -> {
                            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
                        }

                        else -> {}
                    }
                }
            }
        }
        binding?.run {
            inputLogin.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(s)
                            .matches()
                    ) {
                        inputLogin.setBackgroundResource(R.drawable.input)
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            })

            inputPass.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty() && s.length >= 8) {
                        inputPass.setBackgroundResource(R.drawable.input)
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            })
            button?.setOnClickListener {
                val username = inputLogin.text.toString()
                val password = inputPass.text.toString()

                if (username.isEmpty()) {
                    inputLogin.setBackgroundResource(R.drawable.input_eror)
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
                    inputLogin.setBackgroundResource(R.drawable.input_eror)
                } else if (password.isEmpty()) {
                    inputPass.setBackgroundResource(R.drawable.input_eror)
                } else {
                    viewModel.processAction(
                        LoginAction.SignIn(
                            inputLogin.text.toString(),
                            inputPass.text.toString()
                        )
                    )
                }
            }

            signUpTitle.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        callback.remove()
    }

}