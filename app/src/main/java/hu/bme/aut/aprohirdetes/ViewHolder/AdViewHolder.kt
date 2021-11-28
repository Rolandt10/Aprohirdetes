package hu.bme.aut.aprohirdetes.ViewHolder

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.aprohirdetes.R

open class AdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var textViewTitle: TextView
    var textViewCategory: TextView
    var textViewPrice: TextView
    var adItem: ViewGroup

    init {
        textViewTitle = itemView.findViewById(R.id.textTitle)
        textViewCategory = itemView.findViewById(R.id.textCategory)
        textViewPrice = itemView.findViewById(R.id.textPrice)
        adItem = itemView.findViewById(R.id.adItem)
    }
}