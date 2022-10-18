package com.example.trab_sd

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.*
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.trab_sd.databinding.ActivityMainBinding
import kotlinx.coroutines.Job
import org.json.JSONObject
import java.net.URL
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val CHANNEL_ID = "exemplo" //Renomear
    private val notificationId = 101

    val CITY: String = "seropédica,br"
    val API: String = "6e4ca54b59b3892d5409cc23e5a900ee"
    val LANG: String = "pt_br"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        WeatherTask().execute()
        createNotificationChannel()
    }

    inner class WeatherTask : CoroutineAsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            binding.loader.visibility = View.VISIBLE
            binding.mainContainer.visibility = View.GONE
            binding.errorText.visibility = View.GONE
        }

        //Requisicao para API
        override fun doInBackground(vararg p0: String?): String? {
            var response = " "
            //var response2 = " "

            try {
                response = URL("https://api.openweathermap.org/data/2.5/forecast?q=$CITY&units=metric&appid=$API&lang=$LANG").readText(Charsets.UTF_8)
                //response2 = URL("http://192.168.1.3:3000/ws/mqtt").readText(Charsets.UTF_8)

            } catch (e: Exception) {
                null
            }

            return response
        }

        //Faz a leitura do dados e modifica os textos da HomeScreen
        override fun onPostExecute(result: String?) {

            try {
                val jsonObj = JSONObject(result)
                println(result)

                val main = jsonObj.getJSONArray("list").getJSONObject(0).getJSONObject("main")
                val weather = jsonObj.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0)
                val city = jsonObj.getJSONObject("city")

                //DetailsContainer - Isso aqui será alterado depois para os dados do sensor
                //val tempSensor = jsonObj.getString("temperatura").substring(0,2)
                //val humidity = jsonObj.getJSONObject("").getString("umidade").substring(0,2)
                val pressure = main.getString("pressure")

                //OverviewContainer
                val status = weather.getString("main")
                val temp = main.getString("temp").substring(0,2)
                val temp_min = main.getString("temp_min").substring(0,2)
                val temp_max = main.getString("temp_max").substring(0,2)
                val description = weather.getString("description").replaceFirstChar { it.uppercase() }
                val address = city.getString("name")+", "+ city.getString("country")

                //ForecastContainer
                //Dia 1
                val day1 = jsonObj.getJSONArray("list").getJSONObject(6)
                val mainDay1 = day1.getJSONObject("main")
                val weatherDay1 = day1.getJSONArray("weather").getJSONObject(0)
                val statusDay1 = weatherDay1.getString("main")
                val tempDay1 = mainDay1.getString("temp").substring(0,2)
                val dateText1 = day1.getString("dt_txt").substring(5, 10).replace('-', '/')
                    .split('/').reversed().joinToString(separator = "/")

                //Dia 2
                val day2 = jsonObj.getJSONArray("list").getJSONObject(14)
                val mainDay2 = day2.getJSONObject("main")
                val weatherDay2 = day2.getJSONArray("weather").getJSONObject(0)
                val statusDay2 = weatherDay2.getString("main")
                val tempDay2 = mainDay2.getString("temp").substring(0,2)
                val dateText2 = day2.getString("dt_txt").substring(5, 10).replace('-', '/')
                    .split('/').reversed().joinToString(separator = "/")

                //Dia 3
                val day3 = jsonObj.getJSONArray("list").getJSONObject(22)
                val mainDay3 = day3.getJSONObject("main")
                val weatherDay3 = day3.getJSONArray("weather").getJSONObject(0)
                val status3 = weatherDay3.getString("main")
                val tempDay3 = mainDay3.getString("temp").substring(0,2)
                val dateText3 = day3.getString("dt_txt").substring(5, 10).replace('-', '/')
                    .split('/').reversed().joinToString(separator = "/")

                //Dia 4
                val day4 = jsonObj.getJSONArray("list").getJSONObject(30)
                val mainDay4 = day4.getJSONObject("main")
                val weatherDay4 = day4.getJSONArray("weather").getJSONObject(0)
                val status4 = weatherDay4.getString("main")
                val tempDay4 = mainDay4.getString("temp").substring(0,2)
                val dateText4 = day4.getString("dt_txt").substring(5, 10).replace('-', '/')
                    .split('/').reversed().joinToString(separator = "/")

                //Dia 5
                val day5 = jsonObj.getJSONArray("list").getJSONObject(38)
                val mainDay5 = day5.getJSONObject("main")
                val weatherDay5 = day5.getJSONArray("weather").getJSONObject(0)
                val status5 = weatherDay5.getString("main")
                val tempDay5 = mainDay5.getString("temp").substring(0,2)
                val dateText5 = day5.getString("dt_txt").substring(5, 10).replace('-', '/')
                    .split('/').reversed().joinToString(separator = "/")

                changeIcon(status, R.id.status)
                changeBackground()

                //Exibindo os dados em tela
                //DetailsContainer
                //binding.tempSensor.text = tempSensor + " km/h"
                //binding.humidity.text = humidity + "%"
                binding.pressure.text = pressure + " mb"

                //OverviewContainer
                binding.desc.text = description
                binding.temp.text = temp
                binding.tempMin.text = temp_min + "°"
                binding.tempMax.text = temp_max + "°"
                binding.address.text = address

                //ForecastContainer
                binding.dateDay1.text = dateText1
                changeIcon(statusDay1, R.id.statusDay1)
                binding.tempDay1.text = tempDay1 + "°"

                binding.dateDay2.text = dateText2
                changeIcon(statusDay2, R.id.statusDay2)
                binding.tempDay2.text = tempDay2 + "°"

                binding.dateDay3.text = dateText3
                changeIcon(status3, R.id.statusDay3)
                binding.tempDay3.text = tempDay3 + "°"

                binding.dateDay4.text = dateText4
                changeIcon(status4, R.id.statusDay4)
                binding.tempDay4.text = tempDay4 + "°"

                binding.dateDay5.text = dateText5
                changeIcon(status5, R.id.statusDay5)
                binding.tempDay5.text = tempDay5 + "°"

                /*if (Integer.parseInt(tempSensor) > 31) {
                    sendNotification()
                }*/

                binding.loader.visibility = View.GONE
                binding.mainContainer.visibility = View.VISIBLE

            } catch (e: Exception) {
                binding.loader.visibility = View.GONE
                binding.errorText.visibility = View.VISIBLE
            }

        }

        //Muda o ícone conforme o tempo
        private fun changeIcon(status:String, id:Int) {
            if (status == "Clear") { findViewById<ImageView>(id).setImageResource(R.drawable.limpo) }
            if (status == "Cloud") { findViewById<ImageView>(id).setImageResource(R.drawable.nublado) }
            if (status == "Drizzle") { findViewById<ImageView>(id).setImageResource(R.drawable.chuvisco) }
            if (status == "Rain") { findViewById<ImageView>(id).setImageResource(R.drawable.chuva) }
            if (status == "Thunderstorm") { findViewById<ImageView>(id).setImageResource(R.drawable.tempestade) }
        }

        //Muda o wallpaper conforme a hora do dia
        private fun changeBackground() {
            val timeNow = Calendar.getInstance().get(Calendar.HOUR_OF_DAY).minus(3)
            if (timeNow <= 6 || timeNow >= 18) {
                binding.status.setImageResource(R.drawable.noite)
                binding.main.setBackgroundResource(R.drawable.bg_night_bmp)
            }
        }

    }

    //Notificações

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification Title" //Renomear
            val descriptionText = "Notification Description" //Renomear
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification() {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.sun)
            .setContentTitle("Teste")
            .setContentText("Ta pegando fogo bicho")
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, builder.build())
        }
    }

}