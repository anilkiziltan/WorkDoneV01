package com.example.workdonev01

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_profil.*
import kotlinx.android.synthetic.main.fragment_profil__image.*



class HomeActivity : AppCompatActivity(),Profil_ImageFragment.onProfilImageListener {


    var galeridenGelenURI: Uri? = null
    var kameradanGelenBitmap: Bitmap? = null
    override fun getImageWay(ımagePath: Uri?) {
        galeridenGelenURI = ımagePath
        Picasso.with(this).load(galeridenGelenURI).resize(100, 100).into(uplodad_profil_image)
    }

    override fun getImageBitmap(bitmap: Bitmap) {
        kameradanGelenBitmap = bitmap
        uplodad_profil_image.setImageBitmap(kameradanGelenBitmap)
    }


    lateinit var toolbar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        toolbar = supportActionBar!!
        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)



        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)



    }



    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        when (item.itemId) {
            R.id.navigation_home -> {
                toolbar.title = "Home"
                val homeFragment = HomeFragment.newInstance()
                openFragment(homeFragment)

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profil -> {
                toolbar.title = "Profil"
                val profilFragment = ProfilFragment.newInstance()
                openFragment(profilFragment)

                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_upload -> {
                toolbar.title ="Post"
                val uploadFragment = UploadFragment.newInstance()
                openFragment(uploadFragment)

                return@OnNavigationItemSelectedListener true


            }

            R.id.navigation_notifications -> {
                toolbar.title = "Notifications"
                val notfragment = NotificationsFragment.newInstance()
                openFragment(notfragment)

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_searchbar -> {
                toolbar.title = "Search"
                val searchfragment = SearchFragment.newInstance()
                openFragment(searchfragment)

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        val context: Companion = this

        fun getLaunchIntent(from: Context) = Intent(from, HomeActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }









}