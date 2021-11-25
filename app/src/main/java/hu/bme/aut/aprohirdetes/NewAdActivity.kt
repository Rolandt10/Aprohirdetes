package hu.bme.aut.aprohirdetes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
    }
}