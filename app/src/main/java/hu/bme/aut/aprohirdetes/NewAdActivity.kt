package hu.bme.aut.aprohirdetes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import hu.bme.aut.aprohirdetes.databinding.ActivityMainBinding
import hu.bme.aut.aprohirdetes.databinding.ActivityNewAdBinding
import hu.bme.aut.aprohirdetes.databinding.FragmentLoginBinding

class NewAdActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNewAdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewAdBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val spinner = binding.spinner
        val adapter = ArrayAdapter.createFromResource(applicationContext, R.array.categories, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val dao = DAOAd()

        binding.saveNewAdButton.setOnClickListener {
            val title = binding.titleText.text.toString()
            val description = binding.descriptionText.text.toString()
            val price = binding.priceNumber.text.toString()
            val city = binding.cityText.text.toString()
            val category = binding.spinner.selectedItem.toString()

            if (title.length < 3 && title.length > 100) {
                Toast.makeText(applicationContext, "A cím hossza min. 3 és max. 100 karakter!", Toast.LENGTH_LONG)
                    .show()
            } else if (description.length < 10 && description.length > 500) {
                Toast.makeText(applicationContext, "A leírás hossza min. 10 és max. 400 karakter!", Toast.LENGTH_LONG)
            } else if (price.isEmpty()) {
                Toast.makeText(applicationContext, "Adja meg az árat!", Toast.LENGTH_LONG)
            } else if (city.isEmpty()) {
                Toast.makeText(applicationContext, "Adja meg a települést!", Toast.LENGTH_LONG)
            } else {
                dao.addNewAd(title, description, price, city, category)
            }
        }
    }
}