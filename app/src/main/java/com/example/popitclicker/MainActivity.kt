package com.example.popitclicker

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import java.net.URI
import java.net.URL

class MainActivity : AppCompatActivity() {
    private var user_city: EditText? = null
    private var city_button: Button? = null
    private var result_info: TextView? = null
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        user_city = findViewById(R.id.user_city)
        city_button = findViewById(R.id.weather_button)
        result_info = findViewById(R.id.result)

        city_button?.setOnClickListener{
            if(user_city?.text?.toString()?.trim()?.equals("")!!)
                Toast.makeText(this, "Введите город!", Toast.LENGTH_LONG).show()
            else
            {
                val city: String = user_city?.text.toString()
                val key: String = "56756dbbccacde32db6015b664c3df49"
                val url: String = "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$key&units=metric&lang=ru"

                doAsync {
                    val apiResponse = URL(url).readText()
                    val weather = JSONObject(apiResponse).getJSONArray("weather")
                    val desc = weather.getJSONObject(0).getString("description")
                    val main = JSONObject(apiResponse).getJSONObject("main")
                    val temp = main.getString("temp")
                    result_info?.text = "Температура: $temp\n$desc"
                }


            }
        }

    }
}