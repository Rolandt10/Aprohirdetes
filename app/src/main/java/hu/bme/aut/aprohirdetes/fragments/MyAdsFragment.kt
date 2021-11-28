package hu.bme.aut.aprohirdetes.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import hu.bme.aut.aprohirdetes.MainActivity
import hu.bme.aut.aprohirdetes.R
import hu.bme.aut.aprohirdetes.adapter.RecyclerViewAdapter
import hu.bme.aut.aprohirdetes.dao.DAOAd
import hu.bme.aut.aprohirdetes.databinding.FragmentMyAdsBinding
import hu.bme.aut.aprohirdetes.models.Ad

class MyAdsFragment : Fragment(R.layout.fragment_my_ads) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var binding : FragmentMyAdsBinding
    private lateinit var dao: DAOAd
    private lateinit var ads: MutableList<Ad?>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMyAdsBinding.inflate(inflater, container, false)

        binding.newAdButton.setOnClickListener {
            (activity as MainActivity?)?.openNewAd()
        }

        recyclerView = binding.recyclerViewMyAds
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)

        ads = mutableListOf()
        recyclerView.adapter = RecyclerViewAdapter(ads)

        dao = DAOAd()
        loadData()

        return binding.root
    }

    fun loadData() {
        dao.getUserAds().addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                ads.clear()
                for (ad in dataSnapshot.children) {
                    val ad: Ad? = ad.getValue(Ad::class.java)
                    ads.add(ad)
                    Log.w("TAG", ad.toString())
                }
                recyclerView.adapter?.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }
}