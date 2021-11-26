package hu.bme.aut.aprohirdetes.ViewHolder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.aprohirdetes.R

class AdViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var textViewTitle : TextView
    var textViewCategory : TextView

    init {
        textViewTitle = view.findViewById(R.id.textViewTitle)
        textViewCategory = view.findViewById(R.id.textViewCategory)
    }
}