package com.example.desafio2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MenuActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var recyclerViewMenu: RecyclerView
    private lateinit var menuAdapter: MenuAdapter
    private lateinit var tvTotal: TextView
    private lateinit var btnVerOrden: Button
    private var total: Double = 0.0
    private val selectedItems = mutableListOf<MenuItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        dbHelper = DatabaseHelper(this)
        recyclerViewMenu = findViewById(R.id.recyclerViewMenu)
        tvTotal = findViewById(R.id.tvTotal)
        btnVerOrden = findViewById(R.id.btnVerOrden)

        // Cargar el menú desde la base de datos
        val menuItems = cargarMenuDesdeBaseDeDatos()

        // Configurar el RecyclerView
        menuAdapter = MenuAdapter(menuItems) { item ->
            // Callback para cuando se selecciona un item
            selectedItems.add(item)
            total += item.price
            actualizarTotal()
        }
        recyclerViewMenu.adapter = menuAdapter
        recyclerViewMenu.layoutManager = LinearLayoutManager(this)

        btnVerOrden.setOnClickListener {
            val intent = Intent(this, CurrentOrderActivity::class.java)
            intent.putParcelableArrayListExtra("selected_items", ArrayList(selectedItems))
            intent.putExtra("total", total)
            startActivity(intent)
        }
    }

    private fun cargarMenuDesdeBaseDeDatos(): ArrayList<MenuItem> {
        val menuItems = ArrayList<MenuItem>()
        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery("SELECT nombre, precio FROM menu", null)

        if (cursor.moveToFirst()) {
            do {
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                val precio = cursor.getDouble(cursor.getColumnIndexOrThrow("precio"))
                menuItems.add(MenuItem(nombre, precio))
            } while (cursor.moveToNext())
        }

        cursor.close()
        return menuItems
    }

    private fun actualizarTotal() {
        tvTotal.text = String.format("Total: $%.2f", total)
    }
}