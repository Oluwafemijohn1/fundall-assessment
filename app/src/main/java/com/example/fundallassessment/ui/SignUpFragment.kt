package com.example.fundallassessment.ui

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.fundallassessment.R
import com.example.fundallassessment.databinding.FragmentSignUpBinding
import com.example.fundallassessment.remote.model.SignUpPayload
import com.example.fundallassessment.utils.Constants.TOKEN
import com.example.fundallassessment.utils.Constants.USER_EMAIL
import com.example.fundallassessment.utils.isVisible
import com.example.fundallassessment.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)

        binding.apply {

            // Your original string
            val originalString =
                "By clicking on Sign up, you agree to our terms & conditions and privacy policy."

            // Create a SpannableString
            val spannableString = SpannableString(originalString)

            // Find the start and end indices of "terms & conditions"
            val startTerms = originalString.indexOf("terms & conditions")
            val endTerms = startTerms + "terms & conditions".length

            // Find the start and end indices of "privacy policy"
            val startPrivacy = originalString.indexOf("privacy policy")
            val endPrivacy = startPrivacy + "privacy policy".length

            // Get the colors from color resources
            val colorTerms = ContextCompat.getColor(requireContext(), R.color.primary_color_1)
            val colorPrivacy = ContextCompat.getColor(requireContext(), R.color.primary_color_1)

            // Set the color for "terms & conditions"
            spannableString.setSpan(
                ForegroundColorSpan(colorTerms),
                startTerms,
                endTerms,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            // Set the color for "privacy policy"
            spannableString.setSpan(
                ForegroundColorSpan(colorPrivacy),
                startPrivacy,
                endPrivacy,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            // Set the modified text to the TextView
            termsAndCondition.text = spannableString

            signUpButton.setOnClickListener {
                findNavController().navigate(R.id.dashboardFragment)
            }
            loginTv.setOnClickListener {
                findNavController().navigate(R.id.loginFragment)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        viewLifecycleOwner.lifecycleScope.launch {
            authViewModel.signUpUiState.collect {
                when (it) {
                    is Resource.Loading -> {
                        binding.loaderIndicator.isVisible(true)
                    }

                    is Resource.Empty -> {
                        binding.loaderIndicator.isVisible(false)
                    }

                    is Resource.Error -> {
                        binding.loaderIndicator.isVisible(false)
                        Log.d("Resource.Error", "Resource.Error : ${it.message}")
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }

                    is Resource.Success -> {
                        binding.loaderIndicator.isVisible(false)
                        findNavController().navigate(R.id.loginFragment)
                    }
                }
            }
        }
        binding.apply {
            signUpButton.setOnClickListener {
                signUp()
            }

            cancelBtn.setOnClickListener {
                findNavController().popBackStack()
            }
        }

    }

    private fun signUp() {
        binding.apply {

            val firstName = firstName.text.toString()
            val lastName = lastName.text.toString()
            val email = emailInput.text.toString()
            val phone = phoneInput.text.toString()
            val password = passwordInput.text.toString()
            if (password.length < 6) {
                Toast.makeText(
                    requireContext(),
                    "Password Must be 6 characters",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (firstName.isNotEmpty() &&
                lastName.isNotEmpty() &&
                email.isNotEmpty() &&
                password.isNotEmpty()
            ) {
                val payload = SignUpPayload(
                    firstname = firstName,
                    lastname = lastName,
                    email = email,
                    password = password,
                    password_confirmation = password,
                )
                Log.d("TAG", "signUp: $payload")
                authViewModel.signUp(payload)
                authViewModel.putString(USER_EMAIL, email)
                Toast.makeText(requireContext(), "Making call", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please ensure all inputs are filled",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}