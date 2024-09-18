package com.example.desafio2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class OrderDetailsAdapter(private val context: Context, private val items: List<MenuItem>) : BaseAdapter() {

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): MenuItem = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_order_details, parent, false)

        val itemName = view.findViewById<TextView>(R.id.tvOrderItemName)
        val itemPrice = view.findViewById<TextView>(R.id.tvOrderItemPrice)

        val item = getItem(position)
        itemName.text = item.name
        itemPrice.text = "$${item.price}"

        return view
    }
}
