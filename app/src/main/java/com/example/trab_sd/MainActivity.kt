package com.example.trab_sd

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {

    val CITY: String = "seropédica,br"
    val API: String = "6e4ca54b59b3892d5409cc23e5a900ee"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        weatherTask().execute()
    }

    inner class weatherTask() : AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            findViewById<ProgressBar>(R.id.loader).visibility = View.VISIBLE
            findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.GONE
            findViewById<TextView>(R.id.errorText).visibility = View.GONE
        }

        //Requisicao para API
        override fun doInBackground(vararg p0: String?): String? {

            var response:String? = try {
                URL("https://api.openweathermap.org/data/2.5/weather?q=$CITY&units=metric&appid=$API").readText(Charsets.UTF_8)

            } catch (e: Exception) {
                null
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            try {
                //Extraindo dados do JSON da API
                val jsonObj = JSONObject(result)
                val main = jsonObj.getJSONObject("main");
                val sys = jsonObj.getJSONObject("sys");
                val wind = jsonObj.getJSONObject("wind");
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0);
                val address = jsonObj.getString("name")+", "+sys.getString("country")

                //Exibindo os dados em tela
                findViewById<TextView>(R.id.address).text= address

                // -------------------------------------------------- //
                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.VISIBLE

            } catch (e: Exception) {
                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<TextView>(R.id.errorText).visibility = View.VISIBLE

            }

        }

    }

}