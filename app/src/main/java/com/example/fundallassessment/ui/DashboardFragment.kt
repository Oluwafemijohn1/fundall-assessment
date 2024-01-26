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
import coil.load
import coil.transform.CircleCropTransformation
import com.example.fundallassessment.R
import com.example.fundallassessment.databinding.FragmentDashboardBinding
import com.example.fundallassessment.remote.model.LoginResponse
import com.example.fundallassessment.remote.model.Success
import com.example.fundallassessment.remote.model.User
import com.example.fundallassessment.remote.model.UserProfile
import com.example.fundallassessment.utils.Constants
import com.example.fundallassessment.utils.Constants.TOKEN
import com.example.fundallassessment.utils.Resource
import com.example.fundallassessment.utils.isVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by activityViewModels()
    private var token = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel.getUser()

        viewLifecycleOwner.lifecycleScope.launch {
            authViewModel.userUiState.collect{ reponse ->
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
                        reponse.data?.success?.data?.let {
                            bindData(it)
                        }
                    }

                    else -> {}
                }
            }
        }
        authViewModel.getString(TOKEN)
        viewLifecycleOwner.lifecycleScope.launch {
            authViewModel.userTokenUiState.collect{
                token = it
            }
        }

        binding.cardRequest.setOnClickListener {
            findNavController().navigate(R.id.requestCardFragment)
        }
    }

    private fun bindData(data: UserProfile){
        Log.d("DashboardFragment", "DashboardFragment bindData: $data")
        binding.apply {
            imageView2.load(data.avatar){
                Log.d("Token", "bindData: $token")
                placeholder(R.drawable.avatar2)
                crossfade(true)
                transformations(CircleCropTransformation())
                addHeader("Authorization", "Bearer $token")
            }
            totalBalance.text = "# " + data.total_balance.toString()
            incomeBalance.text = "# " + data.income.toString()
            totalSpent.text ="# " +  data.spent.toString()
        }
    }
}