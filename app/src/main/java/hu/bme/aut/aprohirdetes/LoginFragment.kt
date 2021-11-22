package hu.bme.aut.aprohirdetes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
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

        binding.textView2.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment_container, RegistrationFragment())?.commitNow()
        }

        binding.loginButton.setOnClickListener {
            var email = binding.emailAddress.text.toString()
            var password = binding.password.text.toString()

            if (email.isEmpty()) {
                Toast.makeText(
                    activity,
                    "Az e-mail cím megadása kötelező!",
                    Toast.LENGTH_LONG
                ).show()
            } else if (password.isEmpty()) {
                Toast.makeText(activity, "A jelszó megadása kötelező!", Toast.LENGTH_LONG)
                    .show()
            } else {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                        task ->
                    if (task.isSuccessful) {
                        Toast.makeText(activity, "Sikeres bejelentkezés!", Toast.LENGTH_SHORT).show()
                        (activity as MainActivity?)?.updateNav()
                    } else {
                        Toast.makeText(activity, "Sikertelen bejelentkezés!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        return binding.root
    }
}