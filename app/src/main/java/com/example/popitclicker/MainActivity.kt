package com.example.popitclicker

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import java.net.URL
import kotlinx.coroutines.*
import org.jetbrains.anko.uiThread


class MainActivity : AppCompatActivity() {
    private var city_button: Button? = null
    private var result_info: TextView? = null
    @SuppressLint("SetTextI18n")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        city_button = findViewById(R.id.weather_button)
        result_info = findViewById(R.id.result)
    }


    @SuppressLint("SetTextI18n")
    private fun getWeather() {
        val city = "Пенза"
        val key = "56756dbbccacde32db6015b664c3df49"
        val url = "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$key&units=metric&lang=ru"
        doAsync {
            val apiResponse = URL(url).readText()
            val weather = JSONObject(apiResponse).getJSONArray("weather")
            val desc = weather.getJSONObject(0).getString("description")
            val main = JSONObject(apiResponse).getJSONObject("main")
            val temp = main.getString("temp") + "°С"
            uiThread {
                result_info?.text = "$temp\n$desc"
            }
            }
        }

    override fun onStart() {
        super.onStart()
        getWeather()

    }

    override fun onResume() {
        super.onResume()
        city_button?.setOnClickListener{
            getWeather()
            Toast.makeText(this, "Данные обновлены!", Toast.LENGTH_LONG).show()
        }
    }

}
