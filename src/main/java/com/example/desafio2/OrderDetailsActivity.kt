package com.example.desafio2

import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class OrderDetailsActivity : AppCompatActivity() {
    private lateinit var lvOrderDetails: ListView
    private lateinit var tvTotalPriceDetails: TextView
    private var totalPrice: Double = 0.0
    private val orderList: MutableList<MenuItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)

        lvOrderDetails = findViewById(R.id.lvOrderDetails)
        tvTotalPriceDetails = findViewById(R.id.tvTotalPriceDetails)

        // Recupera la lista de la orden desde el intent
        val items: ArrayList<MenuItem>? = intent.getParcelableArrayListExtra("order_list")
        if (items != null) {
            orderList.addAll(items)
        }

        // Calcula el precio total
        totalPrice = orderList.sumOf { it.price }

        // Actualiza la interfaz
        tvTotalPriceDetails.text = "Total: $$totalPrice"

        // Configura el adaptador para mostrar los detalles de la orden
        val adapter = OrderDetailsAdapter(this, orderList)
        lvOrderDetails.adapter = adapter
    }
}
