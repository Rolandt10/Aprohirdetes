package hu.bme.aut.aprohirdetes.dao

import android.security.keystore.KeyNotYetValidException
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import hu.bme.aut.aprohirdetes.models.Ad
import java.text.FieldPosition
import java.text.SimpleDateFormat
import java.util.*
import java.util.Locale

class DAOAd {

    private var dbRef: DatabaseReference
    private var auth: FirebaseAuth
    private var user: FirebaseUser?

    init {
        dbRef = FirebaseDatabase.getInstance("https://aprohirdetes-2a67e-default-rtdb.europe-west1.firebasedatabase.app/").reference
        auth = FirebaseAuth.getInstance()
        user = auth.currentUser
    }

    fun addNewAd(title: String, description: String, price: String, city: String, category: String) {
        val currentDate: String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        val ad = Ad(user?.uid, title, description, price, city, currentDate, user?.email, category)
        dbRef.child("ads").push().setValue(ad).addOnCompleteListener() {
            task ->
                if (task.isSuccessful) {

                } else {
                    Log.w("TAG", task.exception.toString())
                }
        }
    }

    fun getAd(key: String): Query {
        return dbRef.child("ads").child(key)
    }

    fun getAllAds(): Query {
        return dbRef.child("ads")
    }

    fun deleteAd(key: String) {
        dbRef.child("ads").child(user?.uid ?: "").child(key).removeValue()
    }
}