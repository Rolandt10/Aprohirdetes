package hu.bme.aut.aprohirdetes.ViewHolder

import android.view.View
import android.widget.Button
import hu.bme.aut.aprohirdetes.R

class MyAdViewHolder(itemView: View) : AdViewHolder(itemView) {
    var buttonDelete: Button

    init {
        buttonDelete = itemView.findViewById(R.id.deleteButton)
    }
}