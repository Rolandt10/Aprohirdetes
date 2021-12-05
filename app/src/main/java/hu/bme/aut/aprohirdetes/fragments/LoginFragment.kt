package hu.bme.aut.aprohirdetes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import hu.bme.aut.aprohirdetes.MainActivity
import hu.bme.aut.aprohirdetes.R
import hu.bme.aut.aprohirdetes.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding : FragmentLoginBinding
    private lateinit var firebaseAuth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.registrationTextView.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment_container, RegistrationFragment())?.commitNow()
        }

        binding.loginButton.setOnClickListener {
            val email = binding.emailAddress.text.toString()
            val password = binding.password.text.toString()

            when {
                email.isEmpty() -> {
                    binding.emailAddress.requestFocus()
                    binding.emailAddress.error = "Az e-mail cím megadása kötelező!"
                }
                password.isEmpty() -> {
                    binding.password.requestFocus()
                    binding.password.error = "A jelszó megadása kötelező!"
                }
                else -> {
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(activity, "Sikeres bejelentkezés!", Toast.LENGTH_SHORT).show()
                            (activity as MainActivity?)?.updateNav()
                            (activity as MainActivity?)?.hideKeyboard()
                            (activity as MainActivity?)?.supportFragmentManager?.beginTransaction()?.replace(
                                R.id.fragment_container, AdsFragment())?.commitNow()
                        } else {
                            Toast.makeText(activity, "Sikertelen bejelentkezés!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        return binding.root
    }
}