package com.example.inspectionchecklist

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashscreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.splashscren)
        DataManager.carregarDados(this)

        lifecycleScope.launch {
            delay(2000)
            startActivity(Intent(this@SplashscreenActivity, MainActivity::class.java))
            finish()
        }

    }


}