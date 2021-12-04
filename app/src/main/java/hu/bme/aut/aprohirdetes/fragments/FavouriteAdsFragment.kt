package hu.bme.aut.aprohirdetes.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import hu.bme.aut.aprohirdetes.R
import hu.bme.aut.aprohirdetes.adapter.AdAdapter
import hu.bme.aut.aprohirdetes.adapter.FavAdAdapter
import hu.bme.aut.aprohirdetes.dao.DAOAd
import hu.bme.aut.aprohirdetes.databinding.FragmentFavAdsBinding
import hu.bme.aut.aprohirdetes.models.Ad

class FavouriteAdsFragment : Fragment(R.layout.fragment_fav_ads) {
    private lateinit var binding: FragmentFavAdsBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var dao: DAOAd
    private lateinit var auth : FirebaseAuth

    private lateinit var ads: MutableList<Ad?>
    private lateinit var keys: MutableList<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavAdsBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()

        dao = DAOAd(context)

        recyclerView = binding.recyclerViewFavAds
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)

        ads = mutableListOf()
        keys = mutableListOf()
        recyclerView.adapter = FavAdAdapter(ads, keys)

        loadAds()

        return binding.root
    }

    fun loadAds() {
        val user = auth.currentUser
        dao.getAllAds().addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                ads.clear()
                keys.clear()
                for (ad in dataSnapshot.children) {
                    var map: Map<String, Any?> = ad.getValue() as Map<String, Any?>
                    if (map.containsKey("favouriteAds")) {
                        var users: Map<String, Any?> = map?.get("favouriteAds") as Map<String, Any?>
                        if (users.containsKey(user?.uid ?: "")) {
                            val favAd = Ad(map.get("uid").toString(), map.get("title").toString(),
                                map.get("description").toString(), map.get("price").toString(),
                                map.get("city").toString(), map.get("createdAt").toString(),
                                map.get("email").toString(), map.get("phoneNumber").toString(),
                                map.get("category").toString())
                            ads.add(favAd)
                            keys.add(ad.key ?: "")
                        }
                    }
                }
                recyclerView.adapter?.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }
}