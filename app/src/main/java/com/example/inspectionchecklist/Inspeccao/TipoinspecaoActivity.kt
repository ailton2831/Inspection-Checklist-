package com.example.inspectionchecklist.Inspeccao

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.inspectionchecklist.Inspeccao.Periodica.PeriodiccarsActivity
import com.example.inspectionchecklist.Inspeccao.Reinspeccao.ReinspeccaocarsActivity
import com.example.inspectionchecklist.databinding.TipoinspecaoBinding

class TipoinspecaoActivity : AppCompatActivity() {

    private lateinit var menu: TipoinspecaoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        menu = TipoinspecaoBinding.inflate(layoutInflater)

        setContentView(menu.root)

        menu.periodicaCard.setOnClickListener {
            val intent = Intent(this, PeriodiccarsActivity::class.java)
            startActivity(intent)
        }

        menu.reinspecaoCard.setOnClickListener {
            val intent = Intent(this, ReinspeccaocarsActivity::class.java)
            startActivity(intent)
        }

        menu.allInspecao.setOnClickListener {
            val intent = Intent(this, TodasinspeccoesActivity::class.java)
            startActivity(intent)
        }


    }
}