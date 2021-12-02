package hu.bme.aut.aprohirdetes

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import hu.bme.aut.aprohirdetes.dao.DAOAd
import hu.bme.aut.aprohirdetes.databinding.ActivityDetailsBinding
import hu.bme.aut.aprohirdetes.databinding.ActivityMainBinding
import hu.bme.aut.aprohirdetes.models.Ad

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        loadAndSetData()
    }

    fun loadAndSetData() {
        val dao = DAOAd()
        val extras = intent.extras
        val key = extras?.getString("key") ?: ""
        Log.w("TAG", key)

        dao.getAd(key).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val ad = dataSnapshot.getValue(Ad::class.java)
                binding.title.text = ad?.title.toString()
                binding.price.text = ad?.price.toString()
                binding.description.text = ad?.description.toString()
                binding.createdAt.text = ad?.createdAt.toString()
                binding.city.text = ad?.city.toString()
                binding.email.text = ad?.email.toString()
                binding.category.text = ad?.category.toString()
                Log.w("TAG", ad.toString())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("TAG", databaseError.message.toString())
            }
        })
    }
}