package com.example.trab_sd

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import org.json.JSONObject
import org.w3c.dom.Text
import java.net.URL
import java.util.*
import java.text.SimpleDateFormat as SimpleDateFormat1

class MainActivity : AppCompatActivity() {

    val CITY: String = "seropédica,br"
    val API: String = "6e4ca54b59b3892d5409cc23e5a900ee"
    val LANG: String = "pt_br"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
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
                URL("https://api.openweathermap.org/data/2.5/weather?q=$CITY&units=metric&appid=$API&lang=$LANG").readText(Charsets.UTF_8)

            } catch (e: Exception) {
                null
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            try {
                //Extraindo dados do JSON da API
                val jsonObj = JSONObject(result)
                val main = jsonObj.getJSONObject("main")
                val sys = jsonObj.getJSONObject("sys")
                val wind = jsonObj.getJSONObject("wind")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
                val address = jsonObj.getString("name")+", "+sys.getString("country")
                //--------------------------------------//
                val status = weather.getString("description").replaceFirstChar { it.uppercase() }
                val temp = main.getString("temp").substring(0,2)
                val temp_min = main.getString("temp_min").substring(0,2)
                val temp_max = main.getString("temp_max").substring(0,2)
                //--------------------------------------//
                val windSpeed = wind.getString("speed")
                val humidity = main.getString("humidity")
                val pressure = main.getString("pressure")

                //--------------------------------------//
                //Muda o wallpaper conforme a hora
                val timeNow = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

                if (timeNow <= 6) {
                    findViewById<ConstraintLayout>(R.id.main).setBackgroundResource(R.drawable.bg_night)
                }
                if (timeNow >= 18) {
                    findViewById<ConstraintLayout>(R.id.main).setBackgroundResource(R.drawable.bg_night)
                }
                //--------------------------------------//

                //Exibindo os dados em tela
                findViewById<TextView>(R.id.address).text = address
                findViewById<TextView>(R.id.status).text = status
                findViewById<TextView>(R.id.temp).text = temp
                findViewById<TextView>(R.id.temp_min).text = temp_min + "º"
                findViewById<TextView>(R.id.temp_max).text = temp_max + "º"
                findViewById<TextView>(R.id.wind).text = windSpeed + " km/h"
                findViewById<TextView>(R.id.humidity).text = humidity + "%"
                findViewById<TextView>(R.id.pressure).text = pressure + " mb"

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