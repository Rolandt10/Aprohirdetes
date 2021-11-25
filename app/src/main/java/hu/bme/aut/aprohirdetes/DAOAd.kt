package hu.bme.aut.aprohirdetes

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DAOAd {

    private lateinit var dbRef: DatabaseReference

    private fun initDbRef() {
        dbRef = FirebaseDatabase.getInstance("https://aprohirdetes-2a67e-default-rtdb.europe-west1.firebasedatabase.app/").reference
    }

    fun addNewAd(title: String, description: String, price: String, city: String) {
        val ad = Ad(title, description, price, city)
        initDbRef()
        dbRef.child("ads").push().setValue(ad).addOnCompleteListener() {
            task ->
                if (task.isSuccessful) {

                } else {
                    Log.w("TAG", task.exception.toString())
                }
        }
    }
}