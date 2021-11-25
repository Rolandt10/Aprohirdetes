package hu.bme.aut.aprohirdetes

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import hu.bme.aut.aprohirdetes.databinding.ActivityMainBinding
import hu.bme.aut.aprohirdetes.fragments.AdsFragment
import hu.bme.aut.aprohirdetes.fragments.LoginFragment
import hu.bme.aut.aprohirdetes.fragments.MyAdsFragment

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer : DrawerLayout
    private lateinit var binding : ActivityMainBinding
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var toolbar : Toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        drawer = binding.drawerLayout

        updateNav()

        val abdt = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(abdt)
        abdt.syncState()

        /*if (user == null || user!!.isEmailVerified) {
            binding.emailVerificationText.visibility = View.INVISIBLE
        } else if (!user!!.isEmailVerified) {
            binding.emailVerificationText.visibility = View.VISIBLE
        }*/
    }

    fun updateNav() {
        navigationView = binding.navView
        firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser

        if (firebaseUser != null) {
            navigationView.menu.findItem(R.id.nav_login).setVisible(false)
            navigationView.menu.findItem(R.id.nav_my_ads).setVisible(true)
            navigationView.menu.findItem(R.id.nav_logout).setVisible(true)
            navigationView.invalidate()
        } else {
            navigationView.menu.findItem(R.id.nav_logout).setVisible(false)
            navigationView.menu.findItem(R.id.nav_my_ads).setVisible(false)
            navigationView.menu.findItem(R.id.nav_login).setVisible(true)
            navigationView.invalidate()
        }
        navigationView.setNavigationItemSelectedListener(this)
    }

    fun hideKeyboard() {
        this.currentFocus?.let { view ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun openNewAd() {
        val intent = Intent(applicationContext, NewAdActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_login -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, LoginFragment()).commit()
            }
            R.id.nav_logout -> {
                firebaseAuth.signOut()
                updateNav()
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, LoginFragment()).commit()
            }
            R.id.nav_ads -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, AdsFragment()).commit()
            }

            R.id.nav_my_ads -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, MyAdsFragment()).commit()
            }
        }
        return true
    }
}