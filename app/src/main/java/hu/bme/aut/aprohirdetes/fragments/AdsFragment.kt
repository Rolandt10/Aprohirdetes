package hu.bme.aut.aprohirdetes.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import hu.bme.aut.aprohirdetes.models.Ad
import hu.bme.aut.aprohirdetes.R
import hu.bme.aut.aprohirdetes.adapter.RecyclerViewAdapter
import hu.bme.aut.aprohirdetes.dao.DAOAd
import hu.bme.aut.aprohirdetes.databinding.FragmentAdsBinding

class AdsFragment : Fragment(R.layout.fragment_ads) {

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: FragmentAdsBinding
    private lateinit var dao: DAOAd
    private lateinit var ads: MutableList<Ad?>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdsBinding.inflate(inflater, container, false)

        swipeRefreshLayout = binding.swipeRefreshLayout

        swipeRefreshLayout.setOnRefreshListener {
            loadData()
            swipeRefreshLayout.isRefreshing = false
        }

        recyclerView = binding.recyclerViewAds
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)

        ads = mutableListOf()
        recyclerView.adapter = RecyclerViewAdapter(ads)

        dao = DAOAd()
        loadData()

        return binding.root
    }

    fun loadData() {
        dao.getAds().addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                ads.clear()
                for (ad in dataSnapshot.children) {
                    for (data in ad.children) {
                        val ad: Ad? = data.getValue(Ad::class.java)
                        ads.add(ad)
                    }
                }
                recyclerView.adapter?.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

}