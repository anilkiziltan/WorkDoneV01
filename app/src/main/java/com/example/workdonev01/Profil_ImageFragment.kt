package com.example.workdonev01


import android.app.Activity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.fragment_profil.*


class Profil_ImageFragment : DialogFragment() {



    interface onProfilImageListener{
        fun getImageWay(ımagePath:Uri?)
        fun getImageBitmap(bitmap: Bitmap)

    }
    lateinit var mProfilImageListener : onProfilImageListener

    lateinit var chooseGalleryphoto : TextView
    lateinit var newPhoto : TextView

    lateinit var mStorageReference: StorageReference
    val firebaseStorage = FirebaseStorage.getInstance()
    val firebaseStorageRef = firebaseStorage.reference

    private var imagePath: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v = inflater.inflate(R.layout.fragment_profil__image, container, false)

        chooseGalleryphoto = v.findViewById(R.id.profil_fragment_choose_gallery)
        newPhoto = v.findViewById(R.id.profil_fragment_new_photo)


        chooseGalleryphoto.setOnClickListener {
            var intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent,100)

        }

        newPhoto.setOnClickListener {
            var intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent,200)

        }
        return v
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //galeriden
        if (requestCode == 100 && resultCode == Activity.RESULT_OK && data != null) {

            var galeriden = data.data
            Log.e("WORK", "galeriden resim")
            mProfilImageListener.getImageWay(galeriden)

            mStorageReference = FirebaseStorage.getInstance().reference


            var ref =
                mStorageReference.child("images/users" + FirebaseAuth.getInstance().currentUser?.uid + "/profil_resmi")
            var uploadTask = ref.putFile(galeriden)


            val urlTask = uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                return@Continuation ref.downloadUrl
            }).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result

                    FirebaseDatabase.getInstance().reference
                        .child("kullanici")
                        .child(FirebaseAuth.getInstance().currentUser?.uid!!)
                        .child("profil_resmi")
                        .setValue(downloadUri.toString())

                    Toast.makeText(this@Profil_ImageFragment.context, "Değişiklikler Yapıldı", Toast.LENGTH_SHORT).show()
                    //progressGizle()
                } else {
                    Toast.makeText(this@Profil_ImageFragment.context, "Resim yüklenirken hata oluştu", Toast.LENGTH_SHORT).show()
                    // ...
                }

            }





            dismiss()

        }
        //kameradan
        else if (requestCode == 200 && resultCode == Activity.RESULT_OK && data != null) {
            var kameradanÇekilen: Bitmap
            kameradanÇekilen = data.extras.get("data") as Bitmap
            mProfilImageListener.getImageBitmap(kameradanÇekilen)

            dismiss()


        }


    }










    override fun onAttach(context: Context?) {

        mProfilImageListener = activity as onProfilImageListener

        super.onAttach(context)
    }


}

