package hu.bme.aut.aprohirdetes.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.aprohirdetes.R
import hu.bme.aut.aprohirdetes.ViewHolder.MyAdViewHolder
import hu.bme.aut.aprohirdetes.dao.DAOAd
import hu.bme.aut.aprohirdetes.models.Ad

class MyAdAdapter(private val myAds: MutableList<Ad?>) : RecyclerView.Adapter<MyAdViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_ad_item, parent, false)
        context = parent.context
        return MyAdViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyAdViewHolder, position: Int) {
        val dao: DAOAd = DAOAd()

        val title = myAds.get(position)?.title.toString()
        val category = myAds.get(position)?.category.toString()
        val price = myAds.get(position)?.price.toString()

        holder.textViewTitle.text = title
        holder.textViewCategory.text = category
        holder.textViewPrice.text = context.getString(R.string.price_in_HUF, price)

        holder.buttonDelete.setOnClickListener {
            dao.deleteAd(myAds.get(position)?.adId.toString())
        }

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
    }

    override fun getItemCount(): Int {
        return myAds.size
    }
}