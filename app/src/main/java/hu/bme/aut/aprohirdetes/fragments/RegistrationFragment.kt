package hu.bme.aut.aprohirdetes.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import hu.bme.aut.aprohirdetes.R
import hu.bme.aut.aprohirdetes.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment(R.layout.fragment_registration) {

    private lateinit var binding : FragmentRegistrationBinding
    private lateinit var firebaseAuth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegistrationBinding.inflate(inflater, container, false)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.textView3.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment_container, LoginFragment())?.commitNow()
        }

        binding.registerButton.setOnClickListener {

            val email = binding.emailAddress.text.toString()
            val password = binding.password.text.toString()
            val phoneNumber = binding.phoneNumber.text.toString()

            if (email.isEmpty()) {
                Toast.makeText(activity, "Az e-mail cím megadása kötelező!", Toast.LENGTH_LONG).show()
            } else if (password.isEmpty()) {
                Toast.makeText(activity, "A jelszó megadása kötelező!", Toast.LENGTH_LONG).show()
            } else if (phoneNumber.isEmpty()) {
                Toast.makeText(activity, "A telefonszám megadása kötelező!", Toast.LENGTH_LONG).show()
            } else {
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        task ->
                    if (task.isSuccessful) {

                        val user : FirebaseUser? = firebaseAuth.currentUser
                        user?.sendEmailVerification()

                        Toast.makeText(activity, "Sikeres regisztráció!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(activity, "A regisztráció sikertelen!", Toast.LENGTH_SHORT).show()
                        Log.w("Reg", task.exception.toString())
                    }
                }
            }
        }

        return binding.root
    }
}