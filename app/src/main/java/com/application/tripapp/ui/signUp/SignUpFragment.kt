package com.application.tripapp.ui.signUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.application.tripapp.R
import com.application.tripapp.databinding.FragmentLoginBinding
import com.application.tripapp.databinding.FragmentSignUpBinding
import com.application.tripapp.ui.login.LoginAction
import com.application.tripapp.ui.login.LoginState
import com.application.tripapp.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private var binding: FragmentSignUpBinding? = null
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SignUpState.SignUpSuccess -> {
                    findNavController().navigate(R.id.action_signUpFragment_to_menuFragment)
                }

                is SignUpState.Error -> {
                    Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding?.run {
            button?.setOnClickListener {
                viewModel.processAction(
                    SignUpAction.SignUp(
                        inputLogin.text.toString(),
                        inputPass.text.toString()
                    )
                )
            }

            signInTitle.setOnClickListener {
                findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
            }
        }
    }
}
