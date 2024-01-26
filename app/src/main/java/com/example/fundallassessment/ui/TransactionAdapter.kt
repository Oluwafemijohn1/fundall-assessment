package com.example.fundallassessment.ui

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.example.fundallassessment.R
import com.example.fundallassessment.databinding.CardItemBinding
import com.example.fundallassessment.databinding.TransactionItemBinding


class TransactionAdapter(private val dataList: List<TransactionModel> ) : RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {
    class ViewHolder(private val binding: TransactionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val time = binding.time
        val amount = binding.amount
        val icon = binding.icon
        val title = binding.title
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TransactionItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.icon.setImageDrawable(dataList[position].icon)
        holder.amount.text = dataList[position].amount
        holder.title.text = dataList[position].title
        holder.time.text = dataList[position].time
        dataList[position].color?.let { holder.amount.setTextColor(it) }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}

data class TransactionModel(
    val icon: Drawable?,
    val title: String,
    val time: String,
    val amount: String,
    @ColorInt val color: Int?
)