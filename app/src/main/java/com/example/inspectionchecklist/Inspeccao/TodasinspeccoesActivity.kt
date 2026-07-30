package com.example.inspectionchecklist.Inspeccao

import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.inspectionchecklist.DataManager
import com.example.inspectionchecklist.databinding.AllinspeccaoBinding

class TodasinspeccoesActivity : AppCompatActivity() {

    private lateinit var menu: AllinspeccaoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        menu = AllinspeccaoBinding.inflate(layoutInflater)


        setContentView(menu.root)

        //lista de todas inspeçoes

        val lista = DataManager.allInspecoes

        if (lista != null) {

            val dadosExibicao = lista.map { "Matricula : ${it.Matricula} | Tipo:  ${it.tipo} \n| ${it.resultado} \nOBS : ${it.obs} \nData : ${it.data}" }

            val adapter = ArrayAdapter(this, R.layout.simple_list_item_1, dadosExibicao)
            menu.listacarros.adapter = adapter
            adapter.notifyDataSetChanged()
        }

        //filtro de lista de carro por tipo em spinner

        val itenstipo = mutableListOf("Todos","Periodico","Reinspecções")
        val adaptertipo = ArrayAdapter(this, R.layout.simple_spinner_item,itenstipo)
        adaptertipo.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        menu.spinnerTipo.adapter = adaptertipo

        //filtro de lista de carro por resultado em spinner

        val itensR = mutableListOf("Todos","Aprovado","Reprovado")
        val adapterR = ArrayAdapter(this, R.layout.simple_spinner_item,itensR)
        adapterR.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        menu.spinnerResultado.adapter = adapterR

        //----------------------------filtros----------------------------------

        menu.buttonFiltro.setOnClickListener {
            val tipoSelecionado = menu.spinnerTipo.selectedItem.toString()
            val resultadoSelecionado = menu.spinnerResultado.selectedItem.toString()


            val listaFiltrada = DataManager.allInspecoes.filter { inspecao ->
                val atendeTipo = when (tipoSelecionado) {
                    "Todos" -> true
                    "Reinspecções" -> inspecao.tipo == "Reinspeção"
                    else -> inspecao.tipo == tipoSelecionado
                }

                val atendeResultado = when (resultadoSelecionado) {
                    "Todos" -> true
                    else -> inspecao.resultado == resultadoSelecionado
                }

                atendeTipo && atendeResultado
            }


            val dadosExibicao = listaFiltrada.map {
                "Matricula : ${it.Matricula} | Tipo: ${it.tipo}\n${it.resultado}\nOBS : ${it.obs} \nData : ${it.data}"
            }

            val adapter = ArrayAdapter(this, R.layout.simple_list_item_1, dadosExibicao)
            menu.listacarros.adapter = adapter
        }

            //------------------------------------------------------------------







    }




}