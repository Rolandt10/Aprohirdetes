package hu.bme.aut.aprohirdetes.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.aprohirdetes.DetailsActivity
import hu.bme.aut.aprohirdetes.ModifyAdActivity
import hu.bme.aut.aprohirdetes.R
import hu.bme.aut.aprohirdetes.ViewHolder.MyAdViewHolder
import hu.bme.aut.aprohirdetes.dao.DAOAd
import hu.bme.aut.aprohirdetes.models.Ad

/**
 * Konstruktorában meg kell adni a hirdetéseket, ill. azok kulcsait egyaránt, mivel így
 * nem kell külön az Ad modellben eltárolni a hirdetés azonosítóját.
 * (Új hirdetés létrehozásakor lenne egy azonosító tulajdonsága a hirdetésünknek, ami
 * a Firebase backenden null-ként kerülne tárolásra a JSON struktúra miatt.)
 */
class MyAdAdapter(private val myAds: MutableList<Ad?>, private val keys: MutableList<String>) : RecyclerView.Adapter<MyAdViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_ad_item, parent, false)
        context = parent.context
        return MyAdViewHolder(view)
    }

    /**
     * Minden elem rendelkezik egy módosító illetve, törlő gombbal, valamint
     * látható egy hirdetés kategóriája, ára, ill. a hirdetésnek megadott cím.
     * Minden kategória más háttérszínnel rendelkezik. A törlés gombra kattintás
     * esetén megjelenik egy alert dialog, majd ha megbizonyosodtunk arról, hogy törölni
     * szeretnénk a hirdetést, a backenden törlésre kerül a hirdetés annak azonosítója
     * alapján. A módosítás gombra való kattintás esetén pedig egy új Actitvity jön létre, ahol
     * módosíthatjuk a hirdetésünket.
     */
    override fun onBindViewHolder(holder: MyAdViewHolder, position: Int) {
        val dao = DAOAd(context)

        val title = myAds.get(position)?.title.toString()
        val category = myAds.get(position)?.category.toString()
        val price = myAds.get(position)?.price.toString()

        holder.textViewTitle.text = title
        holder.textViewCategory.text = category
        holder.textViewPrice.text = context.getString(R.string.price_in_HUF, price)

        holder.buttonDelete.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Hirdetés törlése")
            builder.setMessage("Biztosan törölni szeretné a hirdetését?")

            builder.setPositiveButton("IGEN") { dialog, which ->
                dao.deleteAd(keys.get(position))
                Toast.makeText(context,
                    "A hirdetés törlésre került!", Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("MÉGSE") { dialog, which ->

            }
            builder.show()
        }

        holder.modifyButton.setOnClickListener {
            val intent = Intent(context, ModifyAdActivity::class.java)
            intent.putExtra("key", keys.get(position))
            context.startActivity(intent)
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