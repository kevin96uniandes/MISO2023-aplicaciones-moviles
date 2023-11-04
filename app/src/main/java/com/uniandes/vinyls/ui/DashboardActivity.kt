package com.uniandes.vinyls.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.uniandes.vinyls.R
import com.uniandes.vinyls.ui.fragments.CreateAlbumFragment
import com.uniandes.vinyls.ui.fragments.ListAlbumsFragment
import com.uniandes.vinyls.ui.fragments.visitorHomeFragment

class DashboardActivity : CrossIntentActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawer: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var userType: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        userType = intent.getStringExtra("type").toString()

        drawer = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        toggle = ActionBarDrawerToggle(
            this, drawer,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navigationView.setNavigationItemSelectedListener(this)

        if(savedInstanceState == null) {
            loadDefaultFragment()
        }


    }

    private fun loadDefaultFragment() {
        val item: MenuItem =  navigationView.menu.getItem(1)
        onNavigationItemSelected(item);
    }

    private fun changeFragment(fragment: Fragment, title: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
        drawer.closeDrawers()
        setTitle(title)
    }

    private fun goToHome() {
        nextActivity(
            MainActivity::class.java,
            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        )
    }

    // Navigation view listener methods
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        item.isChecked = true
        when(item.itemId) {
            R.id.nav_item_one -> goToHome()
            R.id.nav_item_two -> {
                if (userType == "Visitante") {
                    changeFragment(visitorHomeFragment.newInstance(), item.title.toString())
                }else{
                    //changeFragment(ListAlbumsFragment.newInstance(userType), item.title.toString())
                    changeFragment(CreateAlbumFragment.newInstance(), item.title.toString())
                }
            }

        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}