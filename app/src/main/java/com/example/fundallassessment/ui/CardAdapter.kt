package com.example.fundallassessment.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fundallassessment.databinding.CardItemBinding


class CardAdapter() : RecyclerView.Adapter<CardAdapter.ViewHolder>() {
    private val dataList: List<String> = listOf("Fundall Lifestyle Card", "Rave Dollar Card", "Bitcoin Wallet")
    class ViewHolder(private val binding: CardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val textView = binding.cardType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = dataList[position]
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}