package com.application.tripapp.ui.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
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
import com.application.tripapp.R
import com.application.tripapp.databinding.FragmentLoginBinding
import com.application.tripapp.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var binding: FragmentLoginBinding? = null
    private val viewModel: LoginViewModel by viewModels()

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

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is LoginState.LoginSuccess -> {
                            findNavController().navigate(R.id.action_loginFragment_to_menuFragment)
                            Log.d("MyTag", " ЗАШЕЛ И СЮДАА")
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
            Log.d("MyTag", " ЗАШЕЛ СЮДА")
            button?.setOnClickListener {
                viewModel.processAction(
                    LoginAction.SignIn(
                        inputLogin.text.toString(),
                        inputPass.text.toString()
                    )
                )
            }

            signUpTitle.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
            }
        }
    }
}