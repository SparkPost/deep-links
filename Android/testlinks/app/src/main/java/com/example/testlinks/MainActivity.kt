package com.example.testlinks

// Example deep linking app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.i("MainActivity", "onNewIntent called")
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        Log.i("MainActivity", "handleIntent called")

        val appLinkAction = intent.action
        val appLinkData: Uri? = intent.data
        if (Intent.ACTION_VIEW == appLinkAction) {
            // handle URL
            val res : TextView = findViewById(R.id.result)
            res.text = appLinkData.toString()

            // Show this on our simple example app. The function updates the TextView itself
            makeRequest(appLinkData)
        }
    }

    // Basic URL GET request.
    // See https://guides.codepath.com/android/Using-OkHttp
    //     https://square.github.io/okhttp/recipes/

    private fun makeRequest(url: Uri?) {
        // More efficient click-tracking with HTTP GET to obtain the "302" response, but not follow the redirect through to the Location.
        val client = OkHttpClient.Builder()
            .followRedirects(false)
            .build()

        val request = Request.Builder()
            .url(url.toString())
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val locationURL = response.headers["Location"]
                if (locationURL != null) {
                    Log.i("locationURL", locationURL)
                    // Show this on our simple example app
                    val originalUrl : TextView = findViewById(R.id.originalURL)
                    originalUrl.text = locationURL
                }
            }
        })
    }
}


