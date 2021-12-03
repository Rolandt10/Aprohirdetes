package hu.bme.aut.aprohirdetes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import hu.bme.aut.aprohirdetes.dao.DAOAd
import hu.bme.aut.aprohirdetes.databinding.ActivityNewAdBinding

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

        val dao = DAOAd(applicationContext)

        binding.saveNewAdButton.setOnClickListener {
            val title = binding.titleText.text.toString()
            val description = binding.descriptionText.text.toString()
            val price = binding.priceNumber.text.toString()
            val city = binding.cityText.text.toString()
            val category = binding.spinner.selectedItem.toString()

            when {
                title.length < 3 && title.length > 100 -> {
                    binding.titleText.error = "A cím hossza min. 3 és max. 100 karakter!"
                }
                description.length < 10 && description.length > 500 -> {
                    binding.descriptionText.error = "A leírás hossza min. 10 és max. 400 karakter!"
                }
                price.isEmpty() -> {
                    binding.priceNumber.error = "Adja meg az árat!"
                }
                city.isEmpty() -> {
                    binding.cityText.error = "Adja meg a települést!"
                }
                else -> {
                    dao.addNewAd(title, description, price, city, category)
                }
            }
        }
    }
}