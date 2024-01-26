package com.example.fundallassessment.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.fundallassessment.R
import com.example.fundallassessment.databinding.FragmentLoginBinding
import com.example.fundallassessment.remote.model.LoginPayload
import com.example.fundallassessment.remote.model.LoginResponse
import com.example.fundallassessment.remote.model.LoginSuccessResponse
import com.example.fundallassessment.remote.model.Success
import com.example.fundallassessment.utils.Constants.TOKEN
import com.example.fundallassessment.utils.Constants.USER_EMAIL
import com.example.fundallassessment.utils.Resource
import com.example.fundallassessment.utils.isVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by activityViewModels()

    private var email = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        authViewModel.getString(USER_EMAIL)


       viewLifecycleOwner.lifecycleScope.launch {
           authViewModel.userEmailUiState.collect{
               email = it
               binding.emailAddress.text = email
           }
       }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            cancelBtn.setOnClickListener {
                findNavController().popBackStack()
            }
            signUpButton.setOnClickListener {
                login()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            authViewModel.loginUiState.collect{ reponse ->
                when(reponse){
                    is Resource.Loading ->{
                        binding.loaderIndicator.isVisible(true)
                    }
                    is Resource.Empty -> {
                        binding.loaderIndicator.isVisible(false)
                    }
                    is Resource.Error -> {
                        binding.loaderIndicator.isVisible(false)
                        Log.d("Resource.Error", "Resource.Error : ${reponse.message}")
                        Toast.makeText(requireContext(), reponse.message, Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Success -> {
                        binding.loaderIndicator.isVisible(false)
                        val token = reponse.data?.success?.user?.access_token
                        Log.d("LoginFragment", "LoginFragment onViewCreated token: $token")
                        token?.let {
                            authViewModel.putString(TOKEN, it)
                        }

                        findNavController().navigate(R.id.dashboardFragment)
                    }
                }
            }

        }
        if (email.isBlank()){
            binding.emailAddress.text = email
        }
    }

    private fun login(){
       binding.apply {
           val password = passwordInput.text.toString()
           if (password.length < 6){
               Toast.makeText(requireContext(), "The password should be 6 digits", Toast.LENGTH_SHORT).show()
           } else {
               authViewModel.login(
                   LoginPayload(
                       email = email,
                       password = password
                   )
               )
           }
       }
    }
}