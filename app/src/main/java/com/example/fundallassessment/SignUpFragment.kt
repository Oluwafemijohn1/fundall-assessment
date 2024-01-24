package com.example.fundallassessment

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.fundallassessment.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)

        binding.apply {

            // Your original string
            val originalString = "By clicking on Sign up, you agree to our terms & conditions and privacy policy."

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
            spannableString.setSpan(ForegroundColorSpan(colorTerms), startTerms, endTerms, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            // Set the color for "privacy policy"
            spannableString.setSpan(ForegroundColorSpan(colorPrivacy), startPrivacy, endPrivacy, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            // Set the modified text to the TextView
            termsAndCondition.text = spannableString
        }

        return binding.root
    }
}