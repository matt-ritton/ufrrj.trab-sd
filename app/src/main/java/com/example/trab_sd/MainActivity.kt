package com.example.trab_sd

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import java.nio.channels.AsynchronousByteChannel

class MainActivity : AppCompatActivity() {

    val city: String = "Seropédica, RJ";
    val api: String = " ";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //weatherTask().execute()
    }

    /*inner class weatherTask() : AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
            findViewById<ProgressBar>(R.id.loader).visibility = View.VISIBLE;
            findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.GONE;
            findViewById<TextView>(R.id.errorText).visibility = View.GONE;
        }

        //WIP: Chamada para API
        override fun inBackground(vararg p0: String?): String {
            return "";
        }

    }*/

}