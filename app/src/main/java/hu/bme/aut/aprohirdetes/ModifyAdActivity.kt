package hu.bme.aut.aprohirdetes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.bme.aut.aprohirdetes.databinding.ActivityModifyAdBinding
import hu.bme.aut.aprohirdetes.databinding.ActivityNewAdBinding

class ModifyAdActivity : AppCompatActivity() {
    private lateinit var binding: ActivityModifyAdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModifyAdBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}