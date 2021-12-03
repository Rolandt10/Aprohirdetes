package hu.bme.aut.aprohirdetes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import hu.bme.aut.aprohirdetes.dao.DAOAd
import hu.bme.aut.aprohirdetes.databinding.ActivityModifyAdBinding
import hu.bme.aut.aprohirdetes.databinding.ActivityNewAdBinding
import hu.bme.aut.aprohirdetes.models.Ad

class ModifyAdActivity : AppCompatActivity() {
    private lateinit var binding: ActivityModifyAdBinding
    private lateinit var adapter: ArrayAdapter<CharSequence>
    private lateinit var dao: DAOAd
    private lateinit var key: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModifyAdBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        dao = DAOAd(applicationContext)

        val spinner = binding.modifyAdSpinner
        adapter = ArrayAdapter.createFromResource(applicationContext, R.array.categories, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        loadAndSetData()

        binding.saveModificationButton.setOnClickListener {
            modifyAd()
        }
    }

    fun loadAndSetData() {
        val extras = intent.extras
        key = extras?.getString("key") ?: ""
        Log.w("TAG", key)

        dao.getAd(key).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val ad = dataSnapshot.getValue(Ad::class.java)
                binding.modifyAdTitle.setText(ad?.title.toString())
                binding.modifyAdPrice.setText(ad?.price.toString())
                binding.modifyAdDesc.setText(ad?.description.toString())
                binding.modifyAdCity.setText(ad?.city.toString())
                binding.modifyAdSpinner.setSelection(adapter.getPosition(ad?.category.toString()))
                Log.w("TAG", ad.toString())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("TAG", databaseError.message.toString())
            }
        })
    }

    fun modifyAd() {
        val title = binding.modifyAdTitle.text.toString()
        val description = binding.modifyAdDesc.text.toString()
        val price = binding.modifyAdPrice.text.toString()
        val city = binding.modifyAdCity.text.toString()
        val category = binding.modifyAdSpinner.selectedItem.toString()
        dao.modifyAd(key, title, description, price, city, category)
    }
}