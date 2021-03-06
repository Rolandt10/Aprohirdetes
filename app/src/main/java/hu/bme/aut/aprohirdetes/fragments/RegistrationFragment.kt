package hu.bme.aut.aprohirdetes.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import hu.bme.aut.aprohirdetes.R
import hu.bme.aut.aprohirdetes.dao.DAOAd
import hu.bme.aut.aprohirdetes.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment(R.layout.fragment_registration) {

    private lateinit var binding : FragmentRegistrationBinding
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var dao: DAOAd

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegistrationBinding.inflate(inflater, container, false)

        firebaseAuth = FirebaseAuth.getInstance()

        /**
         * Login activity-re navigálás.
         */
        binding.textView3.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment_container, LoginFragment())?.commitNow()
        }

        dao = DAOAd(context)

        /**
         * A felhasználó beregisztrálása.
         */
        binding.registerButton.setOnClickListener {

            val fullName = binding.registrationName.text.toString()
            val email = binding.emailAddress.text.toString()
            val password = binding.password.text.toString()
            val phoneNumber = binding.phoneNumber.text.toString()

            when {
                email.isEmpty() -> {
                    binding.emailAddress.requestFocus()
                    binding.emailAddress.error = "Az e-mail cím megadása kötelező!"
                }
                password.isEmpty() -> {
                    binding.password.requestFocus()
                    binding.password.error = "A jelszó megadása kötelező!"
                }
                phoneNumber.isEmpty() -> {
                    binding.phoneNumber.requestFocus()
                    binding.phoneNumber.error = "A telefonszám megadása kötelező!"
                }
                else -> {
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                        if (task.isSuccessful) {

                            val user : FirebaseUser? = firebaseAuth.currentUser
                            user?.sendEmailVerification()

                            dao.addNewUser(firebaseAuth.uid, fullName, phoneNumber)

                            Toast.makeText(activity, "Sikeres regisztráció!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(activity, "A regisztráció sikertelen!", Toast.LENGTH_SHORT).show()
                            Log.w("Reg", task.exception.toString())
                        }
                    }
                }
            }
        }

        return binding.root
    }
}