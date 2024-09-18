package com.example.desafio2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CurrentOrderAdapter(private val items: List<MenuItem>) :
    RecyclerView.Adapter<CurrentOrderAdapter.OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val currentItem = items[position]
        holder.nameTextView.text = currentItem.name
        holder.priceTextView.text = "$${currentItem.price}"
    }

    override fun getItemCount() = items.size

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.tvOrderItemName)
        val priceTextView: TextView = itemView.findViewById(R.id.tvOrderItemPrice)
    }
}