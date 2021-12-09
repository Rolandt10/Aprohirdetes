package hu.bme.aut.aprohirdetes

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import hu.bme.aut.aprohirdetes.dao.DAOAd
import hu.bme.aut.aprohirdetes.databinding.ActivityDetailsBinding
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

    private fun loadAndSetData() {
        val dao = DAOAd(applicationContext)
        val extras = intent.extras
        val key = extras?.getString("key") ?: ""

        dao.getAd(key).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val ad = dataSnapshot.getValue(Ad::class.java)
                binding.detailsAdTitle.text = ad?.title.toString()
                binding.detailsAdPrice.text = applicationContext.getString(R.string.price_in_HUF, ad?.price.toString())
                binding.detailsAdDesc.text = ad?.description.toString()
                binding.detailsCreatedAt.text = ad?.createdAt.toString()
                binding.detailsAdCity.text = ad?.city.toString()
                binding.detailsEmail.text = ad?.email.toString()
                binding.detailsAdPhone.text = ad?.phoneNumber.toString()
                binding.detailsCategory.text = ad?.category.toString()
                Log.w("TAG", ad.toString())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("TAG", databaseError.message)
            }
        })
    }
}