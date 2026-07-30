package com.example.inspectionchecklist.Inspeccao.Periodica

import android.graphics.Color
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.inspectionchecklist.Carros.Carro
import com.example.inspectionchecklist.DataManager
import com.example.inspectionchecklist.databinding.InspecaoBinding
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


//declarar classe inspeçao
data class Inspecao(val Matricula: String,
                    val tipo:String,
                    val resultado: String,
                    var obs:String,
                    var categoriasFalhadas: List<String>,
                    val data: String = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())): Serializable


//declarar objeto inspeçoes com as listad de todas inspeçoes e reinspeçoes


class InspecaoActivity : AppCompatActivity() {

    private lateinit var menu: InspecaoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        menu = InspecaoBinding.inflate(layoutInflater)

        setContentView(menu.root)
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                confirmarSaida()
            }
        })


        //receber o carro e posicao na lista escolhido
        val carro = intent.getSerializableExtra("CARRO_OBJETO") as Carro
        val posicao = intent.getIntExtra("EDITAR_POSICAO", -1)

        val radiosCertas = listOf<RadioButton>(
            menu.radioC, menu.radioC1, menu.radioC2, menu.radioC3,
            menu.radioC4, menu.radioC5, menu.radioC6, menu.radioC7
        )

        val radiosNaoCerto = listOf<RadioButton>(
            menu.radioNC,menu.radioNC1,menu.radioNC2,menu.radioNC3,menu.radioNC4,
            menu.radioNC5,menu.radioNC6,menu.radioNC7)

        val radiosGroup = listOf<RadioGroup>(menu.group,menu.group1,menu.group2,menu.group3,
            menu.group4,menu.group5,menu.group6,menu.group7)

        //funçao resultado de aprovado

        fun atualizarResultadoAprovado(lista: List<RadioButton>) {

            if (lista.all { it.isChecked }) {

                menu.resultado.text = "Aprovado"
                menu.resultado.setTextColor(Color.parseColor("#16A34A"))
            }
        }

        //funçao para resultado de reprovado

        fun atualizarResultadoReprovado(lista: List<RadioButton>) {

            if (lista.any(){it.isChecked}){
                menu.resultado.text = "Reprovado"
                menu.resultado.setTextColor(Color.parseColor("#DC2626"))

            }

        }

        //verificar se todos radiosbutton estao marcados

        fun verificarTodosEstaoMarcados() : Boolean {

            if (radiosGroup.any { it.checkedRadioButtonId == -1 }){
                Toast.makeText(this, "Por favor, marque todos os campos!", Toast.LENGTH_SHORT).show()
                return false
            }

            return true


        }

        //chamar funçao resultado aprovado para os cliques em radios "conforme"
        for (i in radiosCertas) {
            i.setOnClickListener{
                atualizarResultadoAprovado(radiosCertas)
            }
        }

        //chamar funçao resultado reprovado para os cliques em radios " n conforme"
        for (i in radiosNaoCerto){
            i.setOnClickListener {
                atualizarResultadoReprovado(radiosNaoCerto)
            }
        }


        //funçao de resultado da inspeçao

        fun resultado(): String
        {
            if (menu.resultado.text.toString() == "Aprovado"){
                return "Aprovado"
            }
            if(menu.resultado.text.toString() == "Reprovado"){
                return "Reprovado"
            }
            return ""

        }

        //limpar os radios
        fun limparCampos(){
            for ( i in radiosGroup){
                i.clearCheck()
            }

            menu.resultado.setText("")
            menu.obs.text.clear()

        }

        //adicionar inspeçao

        menu.buttonInspeccao.setOnClickListener {
            //se todos estao marcados
            if(verificarTodosEstaoMarcados()){

                //declarar uma lista de strings para guardar se caso existerem categorias nao conformes

                val listaFalhas = mutableListOf<String>()
                if (menu.radioNC.isChecked) listaFalhas.add("Travões de serviço")
                if (menu.radioNC1.isChecked) listaFalhas.add("Travões de estacionamento")
                if (menu.radioNC2.isChecked) listaFalhas.add("Farois dianteiros")
                if (menu.radioNC3.isChecked) listaFalhas.add("Farois traseiros e luzes stop")
                if (menu.radioNC4.isChecked) listaFalhas.add("Pneus - estado e pressão")
                if (menu.radioNC5.isChecked) listaFalhas.add("Direção - funcionamento e folgas")
                if (menu.radioNC6.isChecked) listaFalhas.add("Emissões - nivel de gases poluentes")
                if (menu.radioNC7.isChecked) listaFalhas.add("Cintos de segurança")
                val observacao = menu.obs.text.toString()

                //criar nova inspecao
                val inspeccao = Inspecao(carro.matricula.toString(), "Periodico",resultado(),observacao,listaFalhas)


                //se for aprovado
                if (inspeccao.resultado == "Aprovado"){
                    DataManager.listaCarroPeriodico.removeAt(posicao) //remove o carro da lista para inspeçao periodica
                    DataManager.allInspecoes.add(inspeccao) //adicionar a inspeçao na lista de inspeçoes
                    DataManager.salvarDados(this)
                    limparCampos()
                    finish()
                }
                //se reprovar
                if (inspeccao.resultado == "Reprovado"){
                    DataManager.listaCarroPeriodico.removeAt(posicao)
                    DataManager.allInspecoes.add(inspeccao)
                    DataManager.reinspecoes.add(inspeccao) //adicionar na lista de reinspeçoes
                    DataManager.salvarDados(this)
                    limparCampos()
                    finish()

                }
            }
        }







    }
    private fun confirmarSaida() {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Cancelar Inspeção?")
            .setMessage("A inspeção ainda não foi finalizada. Deseja realmente sair?")
            .setPositiveButton("Sair") { _, _ -> finish() }
            .setNegativeButton("Continuar", null)
            .show()
    }
}