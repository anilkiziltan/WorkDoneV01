package com.example.workdonev01

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_change_profile.*

class Change_profile : AppCompatActivity() {
    var mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_profile)

        change_cancel_button.setOnClickListener {

           finish()

        }


        fun backLoginPage() {
            mAuth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            Toast.makeText(this, "Lütfen yeni şifrenizle tekrar giriş yapın", Toast.LENGTH_LONG).show()

        }




        confirm_change.setOnClickListener {


            var kullanici = FirebaseAuth.getInstance().currentUser!!

            if (enter_name.text.toString().isNotEmpty()) run {

                var bilgileriGuncelle = UserProfileChangeRequest.Builder()

                    .setDisplayName(enter_name.text.toString())
                    .build()
                kullanici.updateProfile(bilgileriGuncelle).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        FirebaseDatabase.getInstance().reference
                            .child("kullanici")
                            .child(FirebaseAuth.getInstance().currentUser?.uid!!)
                            .child("user_name")
                            .setValue(enter_name.text.toString())
                        finish()

                    }
                }

            }


            if (enter_last_name.text.toString().isNotEmpty()) run {
                var bilgileriGuncelle2 = UserProfileChangeRequest.Builder()
                    .setDisplayName(enter_last_name.text.toString())
                    .build()
                kullanici.updateProfile(bilgileriGuncelle2).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        FirebaseDatabase.getInstance().reference
                            .child("kullanici")
                            .child(FirebaseAuth.getInstance().currentUser?.uid!!)
                            .child("user_lastname")
                            .setValue(enter_last_name.text.toString())
                        finish()

                    }
                }
            }

            if (enter_job.text.toString().isNotEmpty()) run {
                var bilgileriGuncelle3 = UserProfileChangeRequest.Builder()
                    .setDisplayName(enter_job.text.toString())
                    .build()
                kullanici.updateProfile(bilgileriGuncelle3).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        FirebaseDatabase.getInstance().reference
                            .child("kullanici")
                            .child(FirebaseAuth.getInstance().currentUser?.uid!!)
                            .child("user_job")
                            .setValue(enter_job.text.toString())
                        finish()

                    }
                }
            }
            if (enter_number.text.toString().isNotEmpty()) run {
                var bilgileriGuncelle4 = UserProfileChangeRequest.Builder()
                    .setDisplayName(enter_number.text.toString())
                    .build()
                kullanici.updateProfile(bilgileriGuncelle4).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        FirebaseDatabase.getInstance().reference
                            .child("kullanici")
                            .child(FirebaseAuth.getInstance().currentUser?.uid!!)
                            .child("user_phonenumber")
                            .setValue(enter_number.text.toString())
                        finish()


                    }
                }
            }

            if (enter_password.text.toString().isNotEmpty()) run {
                var bilgileriGuncelle5 = UserProfileChangeRequest.Builder()
                    .setDisplayName(enter_password.text.toString())
                    .build()
                kullanici.updateProfile(bilgileriGuncelle5).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        FirebaseDatabase.getInstance().reference
                            .child("kullanici")
                            .child(FirebaseAuth.getInstance().currentUser?.uid!!)
                            .child("user_password")
                            .setValue(enter_password.text.toString())

                        finish()


                    }
                }


            }

            if (enter_password.text.toString().isNotEmpty()) {
                backLoginPage()
            }

        }
    }
}








