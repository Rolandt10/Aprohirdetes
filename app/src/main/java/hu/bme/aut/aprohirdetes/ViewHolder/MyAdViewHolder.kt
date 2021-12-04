package hu.bme.aut.aprohirdetes.ViewHolder

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.aprohirdetes.R

open class MyAdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var buttonDelete: Button
    var modifyButton: Button
    var textViewTitle: TextView
    var textViewCategory: TextView
    var textViewPrice: TextView
    var adItem: ViewGroup

    init {
        buttonDelete = itemView.findViewById(R.id.deleteButton)
        modifyButton = itemView.findViewById(R.id.modifyButton)
        textViewTitle = itemView.findViewById(R.id.textTitle)
        textViewCategory = itemView.findViewById(R.id.textCategory)
        textViewPrice = itemView.findViewById(R.id.textPrice)
        adItem = itemView.findViewById(R.id.adItem)
    }
}