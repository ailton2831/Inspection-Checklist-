package com.example.inspectionchecklist.Inspeccao.Periodica

import android.R
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.inspectionchecklist.DataManager
import com.example.inspectionchecklist.databinding.PeriodiccarsBinding

class PeriodiccarsActivity : AppCompatActivity() {

    override fun onResume() {
        super.onResume()

        val lista = DataManager.listaCarroPeriodico

        if (lista != null) {

            val dadosExibicao = lista.map { "${it.matricula} - ${it.marca} ${it.modelo}\nTipo: ${it.tipo} " }

            val adapter = ArrayAdapter(this, R.layout.simple_list_item_1, dadosExibicao)
            menu.listacarrosPeriodica.adapter = adapter
        }


    }


    private lateinit var menu: PeriodiccarsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        menu = PeriodiccarsBinding.inflate(layoutInflater)

        setContentView(menu.root)

        //lista de carros para inspeçao periodica

        val lista = DataManager.listaCarroPeriodico

        if (lista != null) {

            val dadosExibicao = lista.map { "${it.matricula} - ${it.marca} ${it.modelo}\nTipo: ${it.tipo} " }

            val adapter = ArrayAdapter(this, R.layout.simple_list_item_1, dadosExibicao)
            menu.listacarrosPeriodica.adapter = adapter
        }

        //abrir a tela de inspeçao de acordo com o tipo de carro
        fun fazerInspeçao(posicao: Int){

            val carro = DataManager.listaCarroPeriodico[posicao]

            if(carro.tipo == "Ligeiro Particular"){
                val intent = Intent(this, InspecaoActivity::class.java)
                intent.putExtra("CARRO_OBJETO", carro)
                intent.putExtra("EDITAR_POSICAO", posicao)
                startActivity(intent)
            }

            if(carro.tipo == "Ligeiro Transporte Público"){
                val intent = Intent(this, InspecaoTranportePesadoActivity::class.java)
                intent.putExtra("CARRO_OBJETO", carro)
                intent.putExtra("EDITAR_POSICAO", posicao)
                startActivity(intent)
            }

            if (carro.tipo == "Pesado"){
                val intent = Intent(this, InspecaoPesadoActivity::class.java)
                intent.putExtra("CARRO_OBJETO", carro)
                intent.putExtra("EDITAR_POSICAO", posicao)
                startActivity(intent)
            }

        }

        menu.listacarrosPeriodica.setOnItemClickListener { _, _, position, _ ->
            fazerInspeçao(position)
        }




    }
}