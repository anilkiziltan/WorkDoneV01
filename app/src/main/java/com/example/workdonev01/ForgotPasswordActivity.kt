package com.example.workdonev01

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_password.*


class ForgotPasswordActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        mAuth = FirebaseAuth.getInstance()

        forgotpassword_cancel_button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        forgotpassword_sendEmail_button.setOnClickListener {
            val email = forgotpassword_email_text.text.toString().trim()

            if(TextUtils.isEmpty(email)){
                Toast.makeText(applicationContext, "Enter your email!", Toast.LENGTH_SHORT).show()

            }
            else {

                mAuth!!.sendPasswordResetEmail(email).addOnCompleteListener{
                    task ->
                    if (task.isSuccessful){

                        Toast.makeText(this@ForgotPasswordActivity, "Check email to reset your password!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@ForgotPasswordActivity, "Fail to send reset password email!", Toast.LENGTH_SHORT).show()
                    }

                    }
                }

            }

        }
    }


