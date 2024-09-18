package com.example.desafio2

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CurrentOrderActivity : AppCompatActivity() {
    private lateinit var recyclerViewOrder: RecyclerView
    private lateinit var tvOrderTotal: TextView
    private lateinit var btnConfirmOrder: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_order)

        recyclerViewOrder = findViewById(R.id.recyclerViewOrder)
        tvOrderTotal = findViewById(R.id.tvOrderTotal)
        btnConfirmOrder = findViewById(R.id.btnConfirmOrder)

        val selectedItems = intent.getParcelableArrayListExtra<MenuItem>("selected_items") ?: arrayListOf()
        val total = intent.getDoubleExtra("total", 0.0)

        val adapter = CurrentOrderAdapter(selectedItems)
        recyclerViewOrder.adapter = adapter
        recyclerViewOrder.layoutManager = LinearLayoutManager(this)

        tvOrderTotal.text = String.format("Total: $%.2f", total)

        btnConfirmOrder.setOnClickListener {

            saveOrder(selectedItems, total)


            Toast.makeText(this, "Compra confirmada", Toast.LENGTH_SHORT).show()


            showNotification("Compra Confirmada", "Tu orden por $${String.format("%.2f", total)} ha sido confirmada")

            finish()
        }
    }

    private fun saveOrder(items: List<MenuItem>, total: Double) {
        // Aquí implementaremos la lógica para guardar la orden en la base de datos
        // Por ahora, lo dejaremos como un TODO
        // TODO: Implementar guardado de orden en la base de datos
    }

    private fun showNotification(title: String, content: String) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("order_channel", "Order Notifications", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(this, "order_channel")
            .setSmallIcon(R.drawable.ic_notification) // Asegúrate de tener este icono en tus recursos
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        notificationManager.notify(1, builder.build())
    }
}