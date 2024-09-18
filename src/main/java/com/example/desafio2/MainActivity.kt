package com.example.desafio2

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("logged_in", false)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val btnMenu = findViewById<Button>(R.id.btnMenu)
        val btnOrden = findViewById<Button>(R.id.btnOrden)
        val btnHistorial = findViewById<Button>(R.id.btnHistorial)

        if (isLoggedIn) {
            btnLogin.visibility = Button.GONE
            btnRegister.visibility = Button.GONE

            btnMenu.visibility = Button.VISIBLE
            btnOrden.visibility = Button.VISIBLE
            btnHistorial.visibility = Button.VISIBLE
        } else {
            btnLogin.visibility = Button.VISIBLE
            btnRegister.visibility = Button.VISIBLE

            btnMenu.visibility = Button.GONE
            btnOrden.visibility = Button.GONE
            btnHistorial.visibility = Button.GONE
        }

        btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        btnMenu.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }


        btnOrden.setOnClickListener {
            val intent = Intent(this, OrderDetailsActivity::class.java)
            startActivity(intent)
        }

        btnHistorial.setOnClickListener {
            val intent = Intent(this, PurchaseHistoryActivity::class.java)
            startActivity(intent)
        }
    }
}
