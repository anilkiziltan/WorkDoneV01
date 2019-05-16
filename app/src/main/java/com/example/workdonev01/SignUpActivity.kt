package com.example.workdonev01

import android.content.Intent
import android.media.Image
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private var mRef :  DatabaseReference ?= null
    private var gender : String = ""
    private var durum : String = ""
    private lateinit var database: DatabaseReference

    data class User(
        var user_email : String = "",
        var user_password : String = "",
        var user_job : String = "",
        var user_name : String = "",
        var user_lastname : String = "",
        var user_age : String = "",
        var user_phonenumber : String = "",
        var user_gender : String = "",
        var user_durum : String = "",
        var user_id : String? = "",
        var profil_resmi : String? = ""
    )



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        signup_confirm_button.setOnClickListener {

            sendLogin(it)


            if(signup_female_radiobutton.isActivated){
                gender = "Female"
            }
            else{
                gender = "Male"
            }

            if(signup_employer_radiobutton.isActivated){
                durum = "Employer"
            }
            else{
                durum = "Employee"
            }




        }




        var boolEmail = isEmail(signup_email_text)
        checkData()

        if (boolEmail) {

        } else {


        }

        signup_cancel_button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }



    fun sendLogin(view: View) {

        mAuth!!.signInWithEmailAndPassword(signup_email_text.text.toString(), signup_password_text.text.toString())
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {

                    //eğer user varsa sistemde direk login
                    var intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)


                } else {
                    //eğer kullanıcı yoksa yeni kayıt oluşturuyoruz
                    newUser(signup_email_text.text.toString(), signup_password_text.text.toString())

                }
            }

    }





    fun isEmail(text: EditText): Boolean {

        var email = text.getText().toString()
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches())

    }

    fun isEmpty(text: EditText): Boolean {
        var str = text.getText().toString()
        return TextUtils.isEmpty(str)
    }

    fun checkData() {

        if (isEmpty(signup_password_text)) {
            signup_password_text.setError("Enter valid Password")
        }
        if (isEmpty(signup_passwordagain_text)) {
            signup_passwordagain_text.setError("Enter the again Password")
        }

        if (isEmpty(signup_name_text)) {
            signup_name_text.setError("First Name is required")
        }
        if (isEmpty(signup_lastname_text)) {
            signup_lastname_text.setError("Last Name is required")

        }



        if (isEmail(signup_email_text) == false) {
            signup_email_text.setError("Enter valid email!")
        }

        if (isEmpty(signup_password_text)) {
            signup_password_text.setError("Enter valid Password")

        }
        if (signup_password_text != signup_passwordagain_text){
            signup_passwordagain_text.setError("Şifre eşleşmedi!!!!")

        }


    }

    fun newUser(mail: String, password: String){

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(mail, password)
            .addOnCompleteListener(object : OnCompleteListener<AuthResult> {
                override fun onComplete(p0: Task<AuthResult>) {
                    if (p0.isSuccessful) {
                        var veritabaninaEklenecekKullanici = User()
                        veritabaninaEklenecekKullanici.user_id = FirebaseAuth.getInstance().currentUser?.uid
                        veritabaninaEklenecekKullanici.user_name = signup_name_text.text.toString()
                        veritabaninaEklenecekKullanici.user_lastname = signup_lastname_text.text.toString()
                        veritabaninaEklenecekKullanici.user_email = signup_email_text.text.toString()
                        veritabaninaEklenecekKullanici.user_password = signup_password_text.text.toString()
                        veritabaninaEklenecekKullanici.user_age = signup_age_text.text.toString()
                        veritabaninaEklenecekKullanici.user_phonenumber = signup_phonenumber_text.text.toString()
                        veritabaninaEklenecekKullanici.user_job = signup_profession_text.text.toString()
                        veritabaninaEklenecekKullanici.user_durum = durum
                        veritabaninaEklenecekKullanici.user_gender = gender
                        veritabaninaEklenecekKullanici.profil_resmi = ""


                        FirebaseDatabase.getInstance().reference
                            .child("kullanici")
                            .child(FirebaseAuth.getInstance().currentUser?.uid!!)
                            .setValue(veritabaninaEklenecekKullanici).addOnCompleteListener { task ->

                                if (task.isSuccessful) {
                                    Toast.makeText(
                                        this@SignUpActivity,
                                        "Üye kaydedildi:" + FirebaseAuth.getInstance().currentUser?.uid,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    FirebaseAuth.getInstance().signOut()
                                    loginSayfasinaYonlendir()


                                }
                                else {
                                    //progressBarGizle()
                                    Toast.makeText(this@SignUpActivity, "Üye kaydedilirken sorun olustu:" + p0.exception?.message, Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                }
            })

    }

    fun loginSayfasinaYonlendir(){
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


}
