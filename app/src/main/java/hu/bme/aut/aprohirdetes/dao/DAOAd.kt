package hu.bme.aut.aprohirdetes.dao

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import hu.bme.aut.aprohirdetes.models.Ad
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

        getUserData(user?.uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val map = dataSnapshot.getValue() as Map<String, Any?>
                val currentDate: String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
                val ad = Ad(user?.uid, title, description, price, city, currentDate, user?.email, map["phone"].toString(), category)
                dbRef.child("ads").push().setValue(ad).addOnCompleteListener {
                        task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Az új hirdetés mentésre került!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Az új hirdetés létrehozása sikertelen!", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("TAG", databaseError.message)
            }
        })
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
        val map = mapOf<String, Any?>("uid" to user?.uid, "title" to title, "description" to description,
        "price" to price, "city" to city, "createdAt" to currentDate, "email" to user?.email, "category" to category)
        dbRef.child("ads").child(key).updateChildren(map).addOnCompleteListener {
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

    /**
     * Új felhasználó hozzáadása.
     */
    fun addNewUser(userId: String?, name: String, phoneNumber: String) {
        val map = mapOf<String, Any?>("name" to name, "phone" to phoneNumber)
        dbRef.child("users").child(userId ?: "").updateChildren(map)
    }

    /**
     * A felhasználó telefonszáma, ill. teljes neve.
     */
    fun getUserData(userId: String?): Query {
        return dbRef.child("users").child(userId ?: "")
    }

    /**
     * Kedvenc hirdetés hozzáadása.
     */
    fun addFavouriteAd(key: String?) {
        dbRef.child("ads").child(key ?: "").child("favouriteAds").child(user?.uid ?: "").setValue(user?.email).addOnCompleteListener {
                task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Hozzáadva a kedvencekhez", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Nem sikerült hozzáadni a kedvencekhez", Toast.LENGTH_SHORT).show()
                Log.w("TAG", task.exception.toString())
            }
        }
    }

    /**
     * Kedvenc hirdetés törlése.
     */
    fun deleteFavouriteAd(adKey: String) {
        dbRef.child("ads").child(adKey).child("favouriteAds").child(user?.uid ?: "").removeValue().addOnCompleteListener {
                task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Törölve a kedvencek közül", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Nem sikerült a törlés", Toast.LENGTH_SHORT).show()
                Log.w("TAG", task.exception.toString())
            }
        }
    }
}