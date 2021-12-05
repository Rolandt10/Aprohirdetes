package hu.bme.aut.aprohirdetes

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import hu.bme.aut.aprohirdetes.dao.DAOAd
import hu.bme.aut.aprohirdetes.databinding.ActivityMainBinding
import hu.bme.aut.aprohirdetes.fragments.AdsFragment
import hu.bme.aut.aprohirdetes.fragments.FavouriteAdsFragment
import hu.bme.aut.aprohirdetes.fragments.LoginFragment
import hu.bme.aut.aprohirdetes.fragments.MyAdsFragment

@Suppress("UNCHECKED_CAST")
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawer : DrawerLayout
    private lateinit var binding : ActivityMainBinding
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var navigationView: NavigationView
    private lateinit var dao: DAOAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val toolbar : Toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        drawer = binding.drawerLayout

        dao = DAOAd(applicationContext)
        updateNav()

        val abdt = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(abdt)
        abdt.syncState()

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, AdsFragment()).commit()
    }

    fun updateNav() {
        navigationView = binding.navView
        firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser
        val header: View = binding.navView.getHeaderView(0)
        val tvEmail: TextView = header.findViewById(R.id.email)
        val tvName: TextView = header.findViewById(R.id.name)
        val tvTitle: TextView = header.findViewById(R.id.header_title)

        if (firebaseUser != null) {
            tvEmail.isVisible = true
            tvName.isVisible = true
            tvTitle.isVisible = false
            tvEmail.text = firebaseUser.email
            dao.getUserData(firebaseUser.uid).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val map: Map<String, Any?>? = dataSnapshot.value as Map<String, Any?>?
                    tvName.text = map?.get("name").toString()
                    Log.w("TAG", map?.get("name").toString())
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.w("TAG", databaseError.message)
                }
            })
            navigationView.menu.findItem(R.id.nav_login).isVisible = false
            navigationView.menu.findItem(R.id.nav_favourite_ads).isVisible = true
            navigationView.menu.findItem(R.id.nav_my_ads).isVisible = true
            navigationView.menu.findItem(R.id.nav_logout).isVisible = true
            navigationView.invalidate()
        } else {
            tvEmail.isVisible = false
            tvName.isVisible = false
            tvTitle.isVisible = true
            navigationView.menu.findItem(R.id.nav_logout).isVisible = false
            navigationView.menu.findItem(R.id.nav_favourite_ads).isVisible = false
            navigationView.menu.findItem(R.id.nav_my_ads).isVisible = false
            navigationView.menu.findItem(R.id.nav_login).isVisible = true
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
            R.id.nav_favourite_ads -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, FavouriteAdsFragment()).commit()
            }
        }
        return true
    }
}