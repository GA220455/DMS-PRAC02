package com.example.desafio2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class MenuAdapter(
    private val menuItems: ArrayList<MenuItem>,
    private val onItemClick: (MenuItem) -> Unit
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_menu, parent, false)
        return MenuViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val currentItem = menuItems[position]
        holder.nameTextView.text = currentItem.name
        holder.priceTextView.text = "$${currentItem.price}"
        holder.addButton.setOnClickListener { onItemClick(currentItem) }
    }

    override fun getItemCount(): Int = menuItems.size

    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.tvItemName)
        val priceTextView: TextView = itemView.findViewById(R.id.tvItemPrice)
        val addButton: Button = itemView.findViewById(R.id.btnAddItem)
    }
}