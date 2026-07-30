package com.example.inspectionchecklist.Inspeccao.Reinspeccao

import android.R
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.inspectionchecklist.DataManager
import com.example.inspectionchecklist.databinding.ReinspeccaocarsBinding

class ReinspeccaocarsActivity : AppCompatActivity() {

    private lateinit var menu: ReinspeccaocarsBinding

    override fun onResume() {
        super.onResume()

        val lista = DataManager.reinspecoes

        if (lista != null) {

            val dadosExibicao = lista.map { "Matricula :${it.Matricula} | Tipo: ${it.tipo}\n${it.resultado} \nOBS : ${it.obs} \nData : ${it.data}" }

            val adapter = ArrayAdapter(this, R.layout.simple_list_item_1, dadosExibicao)
            menu.listacarros.adapter = adapter

            adapter.notifyDataSetChanged()

        }


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        menu= ReinspeccaocarsBinding.inflate(layoutInflater)

        setContentView(menu.root)

        //lista de reinspeçoes

        val lista = DataManager.reinspecoes

        if (lista != null) {

            val dadosExibicao = lista.map { "Matricula : ${it.Matricula}\n${it.resultado} \nOBS : ${it.obs} \nData : ${it.data}" }

            val adapter = ArrayAdapter(this, R.layout.simple_list_item_1, dadosExibicao)
            menu.listacarros.adapter = adapter

            adapter.notifyDataSetChanged()

        }

        //fazer reinspeçoes
        fun fazerReinspecao(posicao : Int){

            val reinspecao = DataManager.reinspecoes[posicao]

            val intent = Intent(this, ReinspeccaoActivity::class.java)
            intent.putExtra("REINSPECÇAO", reinspecao)
            intent.putExtra("EDITAR_POSICAO", posicao)
            startActivity(intent)

        }

        menu.listacarros.setOnItemClickListener { _, _, position, _ ->
            fazerReinspecao(position)
        }
    }
}