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
import hu.bme.aut.aprohirdetes.adapter.AdAdapter
import hu.bme.aut.aprohirdetes.dao.DAOAd
import hu.bme.aut.aprohirdetes.databinding.FragmentAdsBinding

class AdsFragment : Fragment(R.layout.fragment_ads) {

    /**
     * SwipeRefreshLayout az hirdetések frissítéséhez.
     */
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: FragmentAdsBinding
    private lateinit var dao: DAOAd

    /**
     * Itt tároljuk el a lekérdezett hirdetéseket, ill. azok kulcsait.
     */
    private lateinit var ads: MutableList<Ad?>
    private lateinit var keys: MutableList<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdsBinding.inflate(inflater, container, false)

        /**
         * Ha gördítünk egyet a képernyőn, akkor frissülnek a hirdetések.
         * Ezt követően le kell állítani a frissítést. (Eltűnik a forgó nyíl animáció.)
         */
        swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            loadData()
            swipeRefreshLayout.isRefreshing = false
        }

        recyclerView = binding.recyclerViewAds
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)

        ads = mutableListOf()
        keys = mutableListOf()
        recyclerView.adapter = AdAdapter(ads, keys)

        dao = DAOAd(context)
        loadData()

        return binding.root
    }

    /**
     * Minden frissítéskor töröljük a hirdetéseket, és azok kulcsait.
     * (Annak érdekében, hogy ne jelenjenek meg a már meglévő hirdetések többször.)
     * A lekérdezett adatokat hozzáadja a listákhoz.
     */
    fun loadData() {
        dao.getAllAds().addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                ads.clear()
                keys.clear()
                for (ad in dataSnapshot.children) {
                    val newAd: Ad? = ad.getValue(Ad::class.java)
                    Log.w("TAG", newAd.toString())
                    ads.add(newAd)
                    keys.add(ad.key ?: "")
                }
                recyclerView.adapter?.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

}