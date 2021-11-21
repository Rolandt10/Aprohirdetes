package hu.bme.aut.aprohirdetes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import hu.bme.aut.aprohirdetes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var drawer : DrawerLayout

    private lateinit var binding : ActivityMainBinding
    private lateinit var firebaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var toolbar : Toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        drawer = binding.drawerLayout

        val abdt = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(abdt)
        abdt.syncState()


        firebaseAuth = FirebaseAuth.getInstance()

        var user : FirebaseUser? = firebaseAuth.currentUser

        /*if (user == null || user!!.isEmailVerified) {
            binding.emailVerificationText.visibility = View.INVISIBLE
        } else if (!user!!.isEmailVerified) {
            binding.emailVerificationText.visibility = View.VISIBLE
        }*/
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}