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
import com.example.inspectionchecklist.databinding.InspecaotpBinding




class InspecaoTranportePesadoActivity : AppCompatActivity() {

    private lateinit var menu: InspecaotpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        menu = InspecaotpBinding.inflate(layoutInflater)

        setContentView(menu.root)
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                confirmarSaida()
            }
        })

        val carro = intent.getSerializableExtra("CARRO_OBJETO") as Carro
        val posicao = intent.getIntExtra("EDITAR_POSICAO", -1)

        val radiosCertas = listOf<RadioButton>(
            menu.radioC, menu.radioC1, menu.radioC2, menu.radioC3,
            menu.radioC4, menu.radioC5, menu.radioC6, menu.radioC7,
            menu.radioC8, menu.radioC9,menu.radio10,menu.radio11
        )

        val radiosNaoCerto = listOf<RadioButton>(
            menu.radioNC,menu.radioNC1,menu.radioNC2,menu.radioNC3,menu.radioNC4,
            menu.radioNC5,menu.radioNC6,menu.radioNC7,
            menu.radioNC8,menu.radioNC9,menu.radioNC10,menu.radioN11)

        val radiosGroup = listOf<RadioGroup>(menu.RadioP,menu.RadioP1,menu.RadioP2,menu.RadioP3,
            menu.RadioP4,menu.RadioP5,menu.RadioP6,menu.RadioP7,menu.RadioP8,menu.RadioP9,menu.RadioP10,menu.RadioP11)

        fun atualizarResultadoAprovado(lista: List<RadioButton>) {

            if (lista.all { it.isChecked }) {

                menu.resultado.text = "Aprovado"
                menu.resultado.setTextColor(Color.parseColor("#16A34A"))
            }
        }

        fun atualizarResultadoReprovado(lista: List<RadioButton>) {

            if (lista.any(){it.isChecked}){
                menu.resultado.text = "Reprovado"
                menu.resultado.setTextColor(Color.parseColor("#DC2626"))

            }

        }

        fun verificarTodosEstaoMarcados() : Boolean {

            if (radiosGroup.any { it.checkedRadioButtonId == -1 }){
                Toast.makeText(this, "Por favor, marque todos os campos!", Toast.LENGTH_SHORT).show()
                return false
            }

            return true


        }

        for (i in radiosCertas) {
            i.setOnClickListener{
                atualizarResultadoAprovado(radiosCertas)
            }
        }

        for (i in radiosNaoCerto){
            i.setOnClickListener {
                atualizarResultadoReprovado(radiosNaoCerto)
            }
        }



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

        fun limparCampos(){
            for ( i in radiosGroup){
                i.clearCheck()
            }

            menu.resultado.setText("")
            menu.obs.text.clear()

        }





        menu.buttonInspeccao.setOnClickListener {
            if(verificarTodosEstaoMarcados()){

                val listaFalhas = mutableListOf<String>()
                if (menu.radioNC.isChecked) listaFalhas.add("Travões de serviço")
                if (menu.radioNC1.isChecked) listaFalhas.add("Travões de estacionamento")
                if (menu.radioNC2.isChecked) listaFalhas.add("Farois dianteiros")
                if (menu.radioNC3.isChecked) listaFalhas.add("Farois traseiros e luzes stop")
                if (menu.radioNC4.isChecked) listaFalhas.add("Pneus - estado e pressão")
                if (menu.radioNC5.isChecked) listaFalhas.add("Direção - funcionamento e folgas")
                if (menu.radioNC6.isChecked) listaFalhas.add("Emissões - nivel de gases poluentes")
                if (menu.radioNC7.isChecked) listaFalhas.add("Cintos de segurança")
                if (menu.radioNC8.isChecked) listaFalhas.add("Segurança - Tacógrafo")
                if (menu.radioNC9.isChecked) listaFalhas.add("Portas e mecanismos de emergência")
                if (menu.radioNC10.isChecked) listaFalhas.add("Extintor - presença e validade")
                if (menu.radioN11.isChecked) listaFalhas.add("Luzes de emergência e aviso")
                val observacao = menu.obs.text.toString()

                val inspeccao = Inspecao(
                    carro.matricula.toString(),
                    "Periodico",
                    resultado(),
                    observacao,
                    listaFalhas
                )

                if (inspeccao.resultado == "Aprovado"){
                    DataManager.listaCarroPeriodico.removeAt(posicao)
                    DataManager.allInspecoes.add(inspeccao)
                    DataManager.salvarDados(this)
                    limparCampos()
                    finish()
                }
                if (inspeccao.resultado == "Reprovado"){
                    DataManager.listaCarroPeriodico.removeAt(posicao)
                    DataManager.allInspecoes.add(inspeccao)
                    DataManager.reinspecoes.add(inspeccao)
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