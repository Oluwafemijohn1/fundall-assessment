package com.example.fundallassessment.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fundallassessment.R
import com.example.fundallassessment.databinding.FragmentRequestCardBinding


class RequestCardFragment : Fragment() {
    private var _binding: FragmentRequestCardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRequestCardBinding.inflate(inflater, container, false)

         val dataList: List<TransactionModel> = listOf(
            TransactionModel(
                ContextCompat.getDrawable(requireContext(),R.drawable.ic_plane),
                "Tobilola",
                "Just now",
                "₦  130",
                context?.getColor(R.color.row1)
                ),
            TransactionModel(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_diamond),
                "Grocery",
                "12:00PM",
                "₦  100",
                context?.getColor(R.color.row2)

            ),
            TransactionModel(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_cart),
                "Shopping",
                "8:00AM",
                "₦  35",
                context?.getColor(R.color.row2)
            ),
            TransactionModel(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_plane_icon),
                "Shopping",
                "8:00AM",
                "₦  30",
                context?.getColor(R.color.row1)
            )
        )

        val recyclerView = binding.recycler
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        val adapter = CardAdapter() // Replace dataList with your data
        recyclerView.adapter = adapter

        val recyclerView2 = binding.recycler2
        val layoutManager2 = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView2.layoutManager = layoutManager2
        val adapter2 = TransactionAdapter(dataList) // Replace dataList with your data
        recyclerView2.adapter = adapter2

        return binding.root
    }
}