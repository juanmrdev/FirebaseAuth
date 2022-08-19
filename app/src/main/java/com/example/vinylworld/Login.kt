package com.example.vinylworld

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.vinylworld.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
    }

    private fun setupView() {
        //Boton iniciar sesion 2
        binding.bottonIniarSesion.setOnClickListener {
            btnLogin()
        }

        //Boton de registro 1
        binding.buttonRegistro.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Texto olvidaste la contrase単a? = ForgotPassword
        binding.olvidastecontra.setOnClickListener {
            val intent = Intent(this, ForgotPassword::class.java)
            startActivity(intent)
        }
    }

    private fun btnLogin() {
        with(binding) {
            if (login(TextMail.text.toString(), TextPasswordLogin.text.toString())) {
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(
                        TextMail.text.toString(),
                        TextPasswordLogin.text.toString()
                    ).addOnCompleteListener {

                        if (it.isSuccessful) {
                            showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                        } else {
                            showAlert()
                        }
                    }
            }
        }
    }

    private fun login(email: String?, pass: String?): Boolean {
        val emailValido = validateData(email)
        val passValida = validatePassword(pass)
        return emailValido && passValida
    }

    // Para validar el email
    private fun validateData(email: String?): Boolean {
        //validate data
        if (email != null) {
            if (email.isEmpty()) {
                Toast.makeText(this, "Por favor ingrese un email", Toast.LENGTH_SHORT).show()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Por favor ingrese un email valido", Toast.LENGTH_SHORT).show()
            } else {
                return true
            }
        }
        return false
    }

    // Para validar contrase単a
    private fun validatePassword(pass: String?): Boolean {
        //validate data
        if (pass != null) {
            if (pass.isEmpty()) {
                Toast.makeText(this, "Por favor ingrese una contrase単a", Toast.LENGTH_SHORT).show()
            } else if (!Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}\$")
                    .matcher(pass).matches()
            ) {
                Toast.makeText(this, "Por favor ingrese una contrase単a valida", Toast.LENGTH_SHORT)
                    .show()
            } else {
                return true
            }
        }
        return false
    }

    private fun showAlert() {

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Se ha producido un error auntenticando al usuario")
        builder.setPositiveButton("Acectar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(email: String, provider: ProviderType) {

        val homeIntent = Intent(this, Menu::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }

        startActivity(homeIntent)

    }
}