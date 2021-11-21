package hu.bme.aut.aprohirdetes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import hu.bme.aut.aprohirdetes.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {

    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var binding : ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.textView3.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.registerButton.setOnClickListener {

            var email = binding.emailAddress.text.toString()
            var password = binding.password.text.toString()
            var phoneNumber = binding.phoneNumber.text.toString()

            if (email.isEmpty()) {
                Toast.makeText(applicationContext, "Az e-mail cím megadása kötelező!", Toast.LENGTH_LONG).show()
            } else if (password.isEmpty()) {
                Toast.makeText(applicationContext, "A jelszó megadása kötelező!", Toast.LENGTH_LONG).show()
            } else if (phoneNumber.isEmpty()) {
              Toast.makeText(applicationContext, "A telefonszám megadása kötelező!", Toast.LENGTH_LONG).show()
            } else {
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) {
                    task ->
                    if (task.isSuccessful) {
                        Toast.makeText(applicationContext, "Sikeres regisztráció!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(applicationContext, "A regisztráció sikertelen!", Toast.LENGTH_SHORT).show()
                        Log.w("Reg", task.exception.toString())
                    }
                }
            }
        }
    }
}