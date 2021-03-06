package hu.bme.aut.aprohirdetes.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.aprohirdetes.DetailsActivity
import hu.bme.aut.aprohirdetes.models.Ad
import hu.bme.aut.aprohirdetes.ViewHolder.AdViewHolder
import hu.bme.aut.aprohirdetes.R
import hu.bme.aut.aprohirdetes.dao.DAOAd

/**
 * Konstruktorában meg kell adni a hirdetéseket, ill. azok kulcsait egyaránt, mivel így
 * nem kell külön az Ad modellben eltárolni a hirdetés azonosítóját.
 * (Új hirdetés létrehozásakor lenne egy azonosító tulajdonsága a hirdetésünknek, ami
 * a Firebase backenden null-ként kerülne tárolásra a JSON struktúra miatt.)
 */
class FavAdAdapter(private val ads: MutableList<Ad?>, private val keys: MutableList<String>) : RecyclerView.Adapter<AdViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ad_item, parent, false)
        context = parent.context
        return AdViewHolder(view)
    }

    /**
     * Minden lista elem rendelkezik a hirdetés címével, kategóriájával, ill. árával.
     * Minden kategória külön színnel rendelkezik. Ha magára a lista elemre kattinktunk,
     * akkor létrejön egy új Activity, ahol megnézhetjük a hirdetéssel kapcsolatos további
     * részeleteket.
     */
    override fun onBindViewHolder(holder: AdViewHolder, position: Int) {
        val title = ads[position]?.title.toString()
        val category = ads[position]?.category.toString()
        val price = ads[position]?.price.toString()

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

        /**
         * A hirdetés részleteinek megtekintése. Átadásra kerül a details activity-nek
         * a hirdetés kulcsa, így az le tudja kérdezni annak részleteit.
         */
        holder.adItem.setOnClickListener {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("key", keys[position])
            context.startActivity(intent)
        }

        /**
         * Egy kedvenc hirdetés törlése.
         */
        holder.imageButtonFavourite.setOnClickListener {
            val dao = DAOAd(context)
            dao.deleteFavouriteAd(keys[position])
        }
    }

    override fun getItemCount(): Int {
        return ads.size
    }
}