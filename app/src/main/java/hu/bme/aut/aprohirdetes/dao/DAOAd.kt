package hu.bme.aut.aprohirdetes.dao

import android.content.Context
import android.security.keystore.KeyNotYetValidException
import android.util.Log
import android.widget.Toast
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

/**
 * Egy Data Access Object, amivel elérjük a backend tárolt adatokat.
 */
class DAOAd(var context: Context?) {
    private var dbRef: DatabaseReference
    private var auth: FirebaseAuth
    private var user: FirebaseUser?

    init {
        dbRef = FirebaseDatabase.getInstance("https://aprohirdetes-2a67e-default-rtdb.europe-west1.firebasedatabase.app/").reference
        auth = FirebaseAuth.getInstance()
        user = auth.currentUser
    }

    /**
     * Új hirdetés hozzáadása.
     * Egy Toast üzenetet mutat sikertelen, ill. sikeres végrehajtás esetén egyaránt.
     */
    fun addNewAd(title: String, description: String, price: String, city: String, category: String) {
        val currentDate: String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        val ad = Ad(user?.uid, title, description, price, city, currentDate, user?.email, category)
        dbRef.child("ads").push().setValue(ad).addOnCompleteListener() {
            task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Az új hirdetés mentésre került!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Az új hirdetés létrehozása sikertelen!", Toast.LENGTH_SHORT).show()
                }
        }
    }

    /**
     * Egy konkrét hirdetés lekérdezése.
     */
    fun getAd(key: String): Query {
        return dbRef.child("ads").child(key)
    }

    /**
     * Az összes hirdetés lekérdezése.
     */
    fun getAllAds(): Query {
        return dbRef.child("ads")
    }

    /**
     * Egy hirdetés frissítése, módosítása.
     * Egy Toast üzenetet mutat sikertelen, ill. sikeres végrehajtás esetén egyaránt.
     */
    fun modifyAd(key: String, title: String, description: String, price: String, city: String, category: String) {
        val currentDate: String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        dbRef.child("ads").child(key).setValue(Ad(user?.uid, title, description, price, city, currentDate, user?.email, category)).addOnCompleteListener() {
            task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Az hirdetés módosult!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Az hirdetés módosítésa sikertelen!", Toast.LENGTH_SHORT).show()
            }

        }
    }

    /**
     * Egy hirdetés törlése.
     */
    fun deleteAd(key: String) {
        dbRef.child("ads").child(key).removeValue()
    }
}