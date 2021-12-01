package hu.bme.aut.aprohirdetes.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.aprohirdetes.DetailsActivity
import hu.bme.aut.aprohirdetes.models.Ad
import hu.bme.aut.aprohirdetes.ViewHolder.AdViewHolder
import hu.bme.aut.aprohirdetes.R


class AdAdapter(private val ads: MutableList<Ad?>, private val keys: MutableList<String>) : RecyclerView.Adapter<AdViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ad_item, parent, false)
        context = parent.context
        return AdViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdViewHolder, position: Int) {
        val title = ads.get(position)?.title.toString()
        val category = ads.get(position)?.category.toString()
        val price = ads.get(position)?.price.toString()

        holder.textViewTitle.text = title
        holder.textViewCategory.text = category
        holder.textViewPrice.text = context.getString(R.string.price_in_HUF, price)

        when (category) {
            "Álláskeresés, állásajánlat" -> {
                holder.adItem.setBackgroundColor(Color.rgb(253, 253, 155))
            }
            "Otthon, háztartás" -> {
                holder.adItem.setBackgroundColor(Color.rgb(249, 181, 139))
            }
            "Műszaki cikkek" -> {
                holder.adItem.setBackgroundColor(Color.rgb(220, 229, 228))
            }
            "Sport, szabadidő" -> {
                holder.adItem.setBackgroundColor(Color.rgb(155, 119, 253))
            }
            "Ruházat" -> {
                holder.adItem.setBackgroundColor(Color.rgb(211, 206, 222))
            }
            "Üzlet, szolgáltatás" -> {
                holder.adItem.setBackgroundColor(Color.rgb(156, 231, 252))
            }
        }

        holder.adItem.setOnClickListener {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("key", keys.get(position))
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return ads.size
    }
}