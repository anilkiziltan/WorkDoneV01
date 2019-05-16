package com.example.workdonev01

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.fragment_upload.*


class UploadFragment : Fragment() {

    private var mAuth: FirebaseAuth? = null
    private var mRef :  DatabaseReference ?= null
    private var count : Int = 1

    private lateinit var database: DatabaseReference




    data class PostData(


        var acıklama: String = "",
        var adres: String = "",
        var user_id1 : String? = "",
        var spinner : String? = "",
        var user_id2 : String? = ""

    )




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_upload, container, false)




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference








        confirm_upload.setOnClickListener {


            if(checkData()){
                newPost()
            }
            else{
                Toast.makeText(this.context,"Eksik veri girdiniz",Toast.LENGTH_LONG).show()
            }


        }



    }




            companion object {

        fun newInstance(): UploadFragment = UploadFragment()


    }

    fun isEmpty(text: EditText): Boolean {
        var str = text.getText().toString()
        return TextUtils.isEmpty(str)
    }


    fun checkData() : Boolean {

        var enterence = true

        if (isEmpty(acıklama_text)) {
            acıklama_text.setError("Lütfen açıklama giriniz.")
            enterence = false

        }
        if (isEmpty(Adres_text)) {
            Adres_text.setError("Lütfen adres giriniz")
            enterence = false
        }

        return enterence


        }



    

    fun newPost(){



                        var veritabanıPost = PostData()
                        veritabanıPost.user_id1 = FirebaseAuth.getInstance().currentUser?.uid
                        veritabanıPost.spinner = spinner.selectedItem.toString()

                        veritabanıPost.acıklama = acıklama_text.text.toString()
                        veritabanıPost.adres = Adres_text.text.toString()






                        FirebaseDatabase.getInstance().reference
                            .child("post")
                            .child(FirebaseAuth.getInstance().currentUser?.uid!! + " " + count)
                            .setValue(veritabanıPost).addOnCompleteListener { task ->

                                if (task.isSuccessful) {
                                    Toast.makeText(
                                        this@UploadFragment.context,
                                        "Post Kaydedildi" + FirebaseAuth.getInstance().currentUser?.uid,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    count = count + 1
                                    FirebaseAuth.getInstance().signOut()



                                }

                              /*  else {

                                    Toast.makeText(this@UploadFragment.context, "Post kaydedilirken sorun olustu:" + p0.exception?.message, Toast.LENGTH_SHORT).show()
                                } */
                            }
                    }
                /*}
            }) */

    }





