package hu.bme.aut.aprohirdetes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import hu.bme.aut.aprohirdetes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var firebaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        firebaseAuth = FirebaseAuth.getInstance()

        var user : FirebaseUser? = firebaseAuth.currentUser

        if (user == null || user!!.isEmailVerified) {
            binding.emailVerificationText.visibility = View.INVISIBLE
        } else if (!user!!.isEmailVerified) {
            binding.emailVerificationText.visibility = View.VISIBLE
        }
    }
}