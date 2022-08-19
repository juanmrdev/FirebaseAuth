package com.example.vinylworld

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ForgotPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgotpassword)

        //flecha atras
        val flechaAtras = findViewById<ImageView>(R.id.flechaAtras)
        flechaAtras.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }

    // Para validar el email ponerlo bien
    private fun validateData(email:String):Boolean{
        if(email.isEmpty()){
            Toast.makeText(this, "Por favor ingrese un email", Toast.LENGTH_SHORT).show()
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Por favor ingrese un email valido", Toast.LENGTH_SHORT).show()
        }else{
            return true
        }
        return false
    }

    //firebase auth para enviar email de confirmación
}

