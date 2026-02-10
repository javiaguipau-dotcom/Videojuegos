package com.example.videojuegos.presentation.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.videojuegos.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etUser = findViewById<EditText>(R.id.etUsuario)
        val etPass = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegister = findViewById<Button>(R.id.btnRegistro)
        val btnForgot = findViewById<Button>(R.id.btnRecuperar)

        btnLogin.setOnClickListener {
            val user = etUser.text.toString().trim()
            val pass = etPass.text.toString().trim()

            if (user == "admin" && pass == "1234") {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("USER_NAME", user)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Credenciales: admin / 1234", Toast.LENGTH_SHORT).show()
            }
        }

        // Botones de registro y recuperación (sin implementación)
        btnRegister.setOnClickListener {
            Toast.makeText(this, "Función de registro no implementada", Toast.LENGTH_SHORT).show()
        }

        btnForgot.setOnClickListener {
            Toast.makeText(this, "Función de recuperación no implementada", Toast.LENGTH_SHORT).show()
        }
    }
}
