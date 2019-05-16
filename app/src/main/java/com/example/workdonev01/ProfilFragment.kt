package com.example.workdonev01

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.fragment_profil.*
import java.io.File
import java.util.*

class ProfilFragment : Fragment() {

    val firebaseStorage = FirebaseStorage.getInstance()
    val firebaseStorageRef = firebaseStorage.reference

    lateinit var mStorageReference: StorageReference
    private var imagePath: Uri? = null


    lateinit var filePath: String



    private lateinit var database: DatabaseReference
    var izinlerVerildi = false
    private var mRef: DatabaseReference? = null
    var user: User? = null




    var selectedPhotoUri: Uri? = null


    data class User(
        var user_email: String = "",
        var user_password: String = "",
        var user_job: String = "",
        var user_name: String = "",
        var user_lastname: String = "",
        var user_age: String = "",
        var user_phonenumber: String = "",
        var user_gender: String = "",
        var user_durum: String = "",
        var profil_resmi: String = ""

    )


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_profil, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupUI()

        //getUserValues()
        kullaniciBilgileriniOku()

        val fragment = Profil_ImageFragment()




        profile_settings_button.setOnClickListener {
            val intent = Intent(this.context, Change_profile::class.java)
            startActivity(intent)
        }

        profile_changes_button.setOnClickListener {




        }







        uplodad_profil_image.setOnClickListener {


            if (izinlerVerildi) {
                var dialog = Profil_ImageFragment()
                dialog.show(fragmentManager, "Foto Seç")


            } else
                izinleriIste()

        }


    }


    private fun setupUI() {
        sign_out_button.setOnClickListener {
            signOut()
        }
    }

    private fun signOut() {
        startActivity(MainActivity.getLaunchIntent(this.context!!))
        FirebaseAuth.getInstance().signOut()
    }


    companion object {

        fun newInstance(): ProfilFragment = ProfilFragment()
    }


    private fun izinleriIste() {

        var izinler = arrayOf(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
        )
        if (ContextCompat.checkSelfPermission(
                this.requireActivity(),
                izinler[0]
            ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                this.requireActivity(),
                izinler[1]
            ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this.requireActivity(), izinler[2]) == PackageManager.PERMISSION_GRANTED
        ) {

            izinlerVerildi = true
        } else {
            ActivityCompat.requestPermissions(this.requireActivity(), izinler, 150)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 150) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                var dialog = Profil_ImageFragment()
                dialog.show(fragmentManager, "fotosec")
            } else {
                Toast.makeText(this.context, "Tüm izinleri vermelisiniz", Toast.LENGTH_SHORT).show()
            }


        }
    }

    private fun kullaniciBilgileriniOku() {

        var referans = FirebaseDatabase.getInstance().reference

        var kullanici = FirebaseAuth.getInstance().currentUser


        //query 1
        var sorgu = referans.child("kullanici")
            .orderByKey()
            .equalTo(kullanici?.uid)
        //.limitToLast(2)
        sorgu.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                for (singleSnapshot in p0!!.children) {


                    var okunanKullanici = singleSnapshot.getValue(com.example.workdonev01.User::class.java)

                    if (okunanKullanici?.user_name.toString()== ""){

                        profile_name_text.text.toString()
                    }
                    else{
                        profile_name_text.setText(okunanKullanici?.user_name.toString())
                    }
                    if (okunanKullanici?.user_lastname.toString()== ""){

                        profile_surname_text.text.toString()
                    }
                    else{
                        profile_surname_text.setText(okunanKullanici?.user_lastname.toString())
                    }

                    if (okunanKullanici?.user_job.toString()== ""){

                        profile_job_text.text.toString()
                    }
                    else{
                        profile_job_text.setText(okunanKullanici?.user_job.toString())
                    }

                    if(okunanKullanici?.user_phonenumber.toString() == ""){

                        profile_phonenumber_text.text.toString()

                    }
                    else{
                        profile_phonenumber_text.setText(okunanKullanici?.user_phonenumber.toString())
                    }

                    Picasso.with(this@ProfilFragment.context).load(okunanKullanici?.profil_resmi).resize(100,100).into(uplodad_profil_image)





                }
            }

        })


    }


}








