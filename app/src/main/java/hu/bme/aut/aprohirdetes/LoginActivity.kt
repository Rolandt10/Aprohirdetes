package hu.bme.aut.aprohirdetes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import hu.bme.aut.aprohirdetes.databinding.ActivityLoginBinding
import hu.bme.aut.aprohirdetes.databinding.ActivityRegistrationBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var firebaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.textView2.setOnClickListener {
            val intent = Intent(applicationContext, RegistrationActivity::class.java)
            startActivity(intent)
        }

        binding.loginButton.setOnClickListener {
            var email = binding.emailAddress.text.toString()
            var password = binding.password.text.toString()

            if (email.isEmpty()) {
                Toast.makeText(
                    applicationContext,
                    "Az e-mail cím megadása kötelező!",
                    Toast.LENGTH_LONG
                ).show()
            } else if (password.isEmpty()) {
                Toast.makeText(applicationContext, "A jelszó megadása kötelező!", Toast.LENGTH_LONG)
                    .show()
            } else {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {
                    task ->
                    if (task.isSuccessful) {
                        Toast.makeText(applicationContext, "Sikeres bejelentkezés!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(applicationContext, "Sikertelen bejelentkezés!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}