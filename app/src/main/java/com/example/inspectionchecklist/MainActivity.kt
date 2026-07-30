package com.example.inspectionchecklist

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.inspectionchecklist.Carros.ListacarrosActivity
import com.example.inspectionchecklist.Carros.NewvehicleActivity
import com.example.inspectionchecklist.DashBoard.DashboardActivity
import com.example.inspectionchecklist.Inspeccao.TipoinspecaoActivity
import com.example.inspectionchecklist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var menu: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        menu = ActivityMainBinding.inflate(layoutInflater)

        setContentView(menu.root)

        menu.novoveiculo.setOnClickListener {
            val intent = Intent(this, NewvehicleActivity::class.java)
            startActivity(intent)
        }

        menu.inspecao.setOnClickListener {
            val intent = Intent(this, TipoinspecaoActivity::class.java)
            startActivity(intent)
        }

        menu.veiculos.setOnClickListener {
            val intent = Intent(this, ListacarrosActivity::class.java)
            startActivity(intent)
        }

        menu.dashboard.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }

    }
}