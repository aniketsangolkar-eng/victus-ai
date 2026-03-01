package com.victus.ai

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var speechRecognizer: VictusSpeechRecognizer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        speechRecognizer = VictusSpeechRecognizer(this).apply {
            initialize(object : VictusSpeechRecognizer.OnSpeechResultListener {
                override fun onResult(result: String) {
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "You said: $result", Toast.LENGTH_LONG).show()
                        processCommand(result)
                    }
                }

                override fun onError(error: String) {
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "Error: $error", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }

        findViewById<Button>(R.id.btnSpeak).setOnClickListener {
            speechRecognizer.startListening()
        }
    }

    private fun processCommand(command: String) {
        println("Processing command: $command")
        // TODO: Send command to backend or process locally
    }

    override fun onDestroy() {
        speechRecognizer.destroy()
        super.onDestroy()
    }
}