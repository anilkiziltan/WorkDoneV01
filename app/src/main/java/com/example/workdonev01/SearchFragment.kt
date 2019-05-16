package com.example.workdonev01

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment() {





    class PostData {

        var iş : String = ""
        var acıklama : String = ""
        var adres : String = ""
        var user_id1 : String? = ""
        var spinner : String? = ""
        var user_id2 : String? = ""

    }




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_search, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        row1.setOnClickListener{
            var intent = Intent(this.context,Listeilan::class.java)
            startActivity(intent)
        }
        row2.setOnClickListener{
            var intent = Intent(this.context,Listeilan::class.java)
            startActivity(intent)
        }
        row3.setOnClickListener{
            var intent = Intent(this.context,Listeilan::class.java)
            startActivity(intent)
        }
        row4.setOnClickListener{
            var intent = Intent(this.context,Listeilan::class.java)
            startActivity(intent)
        }
        row5.setOnClickListener{
            var intent = Intent(this.context,Listeilan::class.java)
            startActivity(intent)
        }
        row6.setOnClickListener{
            var intent = Intent(this.context,Listeilan::class.java)
            startActivity(intent)
        }
        row7.setOnClickListener{
            var intent = Intent(this.context,Listeilan::class.java)
            startActivity(intent)
        }
        row8.setOnClickListener{
            var intent = Intent(this.context,Listeilan::class.java)
            startActivity(intent)
        }
        row9.setOnClickListener{
            var intent = Intent(this.context,Listeilan::class.java)
            startActivity(intent)
        }





    }






    companion object {

        fun newInstance(): SearchFragment = SearchFragment()
    }
}
