package hu.bme.aut.aprohirdetes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.aprohirdetes.models.Ad
import hu.bme.aut.aprohirdetes.R
import hu.bme.aut.aprohirdetes.ViewHolder.AdViewHolder

class RecyclerViewAdapter(private val ads: MutableList<Ad?>) : RecyclerView.Adapter<AdViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ad_item, parent, false)
        return AdViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdViewHolder, position: Int) {
        holder.textViewTitle.text = ads.get(position)?.title.toString()
        holder.textViewCategory.text = ads.get(position)?.category.toString()
    }

    override fun getItemCount(): Int {
        return ads.size
    }

    fun addAds(ads: MutableList<Ad?>) {
        ads.addAll(ads)
    }
}