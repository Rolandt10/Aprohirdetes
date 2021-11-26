package hu.bme.aut.aprohirdetes.ViewHolder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.aprohirdetes.R

class AdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var textViewTitle : TextView
    var textViewCategory : TextView

    init {
        textViewTitle = itemView.findViewById(R.id.textViewTitle)
        textViewCategory = itemView.findViewById(R.id.textViewCategory)
    }
}