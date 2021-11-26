package hu.bme.aut.aprohirdetes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.aprohirdetes.Ad
import hu.bme.aut.aprohirdetes.R
import hu.bme.aut.aprohirdetes.ViewHolder.AdViewHolder

class RecyclerViewAdapter(private val ads: Array<Ad>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ad_item, parent, false)
        return AdViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val adViewHolder = holder as? AdViewHolder
        adViewHolder?.textViewTitle?.text = ads[position].title
        adViewHolder?.textViewCategory?.text = ads[position].category
    }

    override fun getItemCount(): Int {
        return ads.size
    }
}