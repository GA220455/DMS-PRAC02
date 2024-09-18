package com.example.desafio2

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PurchaseHistoryActivity : AppCompatActivity() {
    private lateinit var recyclerViewHistory: RecyclerView
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase_history)

        recyclerViewHistory = findViewById(R.id.recyclerViewHistory)
        dbHelper = DatabaseHelper(this)

        val purchaseHistory = loadPurchaseHistory()

        if (purchaseHistory.isEmpty()) {
            Toast.makeText(this, "No hay historial de compras", Toast.LENGTH_SHORT).show()
        } else {
            val adapter = PurchaseHistoryAdapter(purchaseHistory)
            recyclerViewHistory.adapter = adapter
            recyclerViewHistory.layoutManager = LinearLayoutManager(this)
        }
    }

    private fun loadPurchaseHistory(): List<PurchaseRecord> {
        val usuarioId = getCurrentUserId()
        val history = dbHelper.getPurchaseHistory(usuarioId)
        Log.d("PurchaseHistory", "Loaded ${history.size} records for user $usuarioId")
        return history
    }

    private fun getCurrentUserId(): Int {
        // TODO: Implement proper user ID retrieval
        return 1 // Using a default value for now
    }


}