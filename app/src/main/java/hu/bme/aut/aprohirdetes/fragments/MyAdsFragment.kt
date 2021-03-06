package hu.bme.aut.aprohirdetes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import hu.bme.aut.aprohirdetes.MainActivity
import hu.bme.aut.aprohirdetes.R
import hu.bme.aut.aprohirdetes.adapter.MyAdAdapter
import hu.bme.aut.aprohirdetes.dao.DAOAd
import hu.bme.aut.aprohirdetes.databinding.FragmentMyAdsBinding
import hu.bme.aut.aprohirdetes.models.Ad

class MyAdsFragment : Fragment(R.layout.fragment_my_ads) {
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding : FragmentMyAdsBinding
    private lateinit var dao: DAOAd
    private lateinit var myAds: MutableList<Ad?>
    private lateinit var keys: MutableList<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMyAdsBinding.inflate(inflater, container, false)

        /**
         * Új hirdetés létrehozása.
         */
        binding.newAdButton.setOnClickListener {
            (activity as MainActivity?)?.openNewAd()
        }

        recyclerView = binding.recyclerViewMyAds
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)

        myAds = mutableListOf()
        keys = mutableListOf()
        recyclerView.adapter = MyAdAdapter(myAds, keys)

        dao = DAOAd(context)
        loadData()

        return binding.root
    }

    /**
     * Lekérdezi a bejelentkezett felhasználó hirdetéseit. Ha nincs, akkor egy
     * üzenetet jelenít meg.
     */
    private fun loadData() {
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        val user: FirebaseUser? = auth.currentUser

        dao.getAllAds().addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                myAds.clear()
                keys.clear()
                for (ad in dataSnapshot.children) {
                    val newAd: Ad? = ad.getValue(Ad::class.java)
                    if (newAd?.uid.equals(user?.uid)) {
                        myAds.add(newAd)
                        keys.add(ad.key ?: "")
                    }
                }
                if (myAds.isEmpty()) {
                    binding.noAdTextView.isVisible = true
                } else {
                    binding.noAdTextView.isVisible = false
                }
                recyclerView.adapter?.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }
}