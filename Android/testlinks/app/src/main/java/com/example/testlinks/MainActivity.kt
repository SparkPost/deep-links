package com.example.testlinks

// Example deep linking app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley


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
            val location = makeRequest(appLinkData)

            // Show this on our simple example app
            val res : TextView = findViewById(R.id.result)
            res.text = appLinkData.toString()
            val originalUrl : TextView = findViewById(R.id.originalURL)
            originalUrl.text = location.toString()
        }
    }

    // Basic URL GET request.
    // TODO: More efficient click-tracking with HTTP GET to obtain the "302" response, and return the Location: header result, but not follow the redirect through to the Location.
    // See https://developer.android.com/training/volley
    private fun makeRequest(url : Uri?): Uri {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.GET, url.toString(),
            Response.Listener<String> { response ->
                // Display the first 500 characters of the response string.
                Log.i("makeRequest", "Response is: ${response.substring(0, 500)}")
            },
            Response.ErrorListener { error ->
                Log.i("makeRequest", "Error response: ${error.toString()}")
                })
            // Add the request to the RequestQueue.
            queue.add(stringRequest)
            return Uri.parse( "https://example.com") // placeholder
    }
}

