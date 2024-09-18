package com.example.desafio2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class OrderActivity : AppCompatActivity() {
    private lateinit var tvTotalPrice: TextView
    private lateinit var btnSeeFullDetails: Button
    private lateinit var btnCompleteOrder: Button
    private lateinit var dbHelper: DatabaseHelper
    private var totalPrice: Double = 0.0
    private val orderList: MutableList<MenuItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        tvTotalPrice = findViewById(R.id.tvTotalPrice)
        btnSeeFullDetails = findViewById(R.id.btnSeeFullDetails)
        btnCompleteOrder = findViewById(R.id.btnCompleteOrder)
        dbHelper = DatabaseHelper(this)

        // Retrieve the total price and order list from the intent
        totalPrice = intent.getDoubleExtra("total_price", 0.0)
        val items: ArrayList<MenuItem>? = intent.getParcelableArrayListExtra("order_list")
        if (items != null) {
            orderList.addAll(items)
        }

        // Update total price in UI
        tvTotalPrice.text = "Total: $${String.format("%.2f", totalPrice)}"

        // Go to the full details screen
        btnSeeFullDetails.setOnClickListener {
            val intent = Intent(this, OrderDetailsActivity::class.java)
            intent.putParcelableArrayListExtra("order_list", ArrayList(orderList))
            startActivity(intent)
        }

        // Complete the order
        btnCompleteOrder.setOnClickListener {
            completePurchase()
        }
    }

    private fun completePurchase() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val currentDate = dateFormat.format(Date())
        val usuarioId = getCurrentUserId()

        dbHelper.savePurchase(usuarioId, currentDate, totalPrice)

        Toast.makeText(this, "Compra guardada con éxito", Toast.LENGTH_SHORT).show()

        // Clear the current order
        orderList.clear()
        totalPrice = 0.0
        tvTotalPrice.text = "Total: $0.00"

        // Navigate to PurchaseHistoryActivity
        val intent = Intent(this, PurchaseHistoryActivity::class.java)
        startActivity(intent)
        finish() // This will close the OrderActivity
    }

    private fun getCurrentUserId(): Int {
        // TODO: Implement proper user ID retrieval
        return 1 // Using a default value for now
    }

    // Update order based on items selected in MenuActivity (you'll call this from MenuActivity)
    fun updateOrder(selectedItem: MenuItem) {
        orderList.add(selectedItem)
        totalPrice += selectedItem.price
        tvTotalPrice.text = "Total: $${String.format("%.2f", totalPrice)}"
    }
}