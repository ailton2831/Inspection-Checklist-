package com.example.inspectionchecklist.Carros

import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.inspectionchecklist.DataManager
import com.example.inspectionchecklist.databinding.NewvehicleBinding
import java.io.Serializable
import java.util.Calendar


//classe carro
data class Carro(val matricula:String, val marca:String, val modelo:String
                 , val proprietario:String, val ano:String, val tipo:String): Serializable

class NewvehicleActivity : AppCompatActivity() {

    private lateinit var menu: NewvehicleBinding

    private var posicaoEdicao= -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        menu = NewvehicleBinding.inflate(layoutInflater)

        setContentView(menu.root)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                confirmarSaida()
            }
        })

        //esta tela serve tanto para adicionar um veiculo e editar

        posicaoEdicao = intent.getIntExtra("EDITAR_POSICAO", -1) //se caso receber um extra quer dizer q tela vai ser de ediçao

        if (posicaoEdicao != -1) { //se for ediçao
            val carro = intent.getSerializableExtra("CARRO_OBJETO") as Carro

            val resto = carro.matricula.substring(2)
            menu.restomatricula.setText(resto)
            menu.inputMarca.setText(carro.marca)
            menu.inputModelo.setText(carro.modelo)
            menu.inputProp.setText(carro.proprietario)
            menu.inputAno.setText(carro.ano)
            when(carro.tipo) {
                "Ligeiro Particular" -> menu.radioLP.isChecked = true
                "Ligeiro Transporte Público" -> menu.radioLTP.isChecked = true
                "Pesado" -> menu.radioP.isChecked = true
            }
            menu.buttonAdicionarCar.text = "Atualizar Veículo"
        }

        //----------------------------------------------------------------------------------------------------------------

        //adicionar placas das ilhas no spinner
        val itens = mutableListOf("CVS","CVB","FA","SA","SV","SN","SL","BV","ST","FG","BR")
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item,itens)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        menu.spinnerIlha.adapter = adapter

        //-----------------------------------------------------------------------------------------------------------------

        //validacao das matriculas

        fun validarMatricula(): Boolean {
            val ilha = menu.spinnerIlha.selectedItem.toString()
            val entrada = menu.restomatricula.text.toString().trim().uppercase()

            if(!entrada.isEmpty()){
                val regex = when (ilha) {
                    "CVS" -> "^\\d{4}$".toRegex()                               //valida formato CVS0000
                    "CVB" -> "^\\d{4}$".toRegex()                              //valida formato CVB0000
                    "FA" -> "^\\d{4}$".toRegex()                              //valida o formato FA0000
                    else -> "^\\d{2}[A-Z]{2}|[A-Z]{2}\\d{2}$".toRegex()     //valida formato ST00AA ou STAA00

                }

                if (entrada.matches(regex)) {
                    return true
                } else {
                    menu.restomatricula.error = "Formato inválido para $ilha"
                    return false
                }

            } else {
                Toast.makeText(this, "Digite a Matricula!", Toast.LENGTH_SHORT).show()
                return false
            }


        }

        //------------------------------------------------------------------------------------------------------------------


        //validacao de Marca Modelo e Propriertario

        fun validarInputs(): Boolean {
            if (menu.inputMarca.text.toString().isEmpty() || menu.inputModelo.toString().isEmpty() || menu.inputProp.text.toString().isEmpty()){
                Toast.makeText(this, "Complete os campos (Marca/Modelo/Proprietário)!", Toast.LENGTH_SHORT).show()
                return false
            } else {
                return true
            }
        }

        //------------------------------------------------------------------------------------------------------------------




        //validacao de ano de matricula

        fun validarAno(): Boolean {
            val ano = menu.inputAno.text.toString().trim()

            // ano atual do sistema
            val anoAtual = Calendar.getInstance().get(Calendar.YEAR)

            when {
                ano.isEmpty() -> {
                    menu.inputAno.error = "Por favor, digite o ano de matricula"
                    return false
                }
                ano.length < 4 -> {
                    menu.inputAno.error = "O ano deve ter 4 dígitos"
                    return false
                }
                ano.toInt() > anoAtual -> {
                    menu.inputAno.error = "O ano não pode ser maior que $anoAtual"
                    return false
                }
                ano.toInt() < 1975 -> {
                    menu.inputAno.error = "Ano antigo (mínimo 1975)"
                    return false
                }
                else -> {
                    menu.inputAno.error = null
                    return true
                }
            }
        }

        //------------------------------------------------------------------------------------------------------------------

        //caso nao clicar o tipo

        fun validarTipo(): Boolean{
            if (menu.RadioG.checkedRadioButtonId == -1){
                Toast.makeText(this, "Por favor, selecione o tipo!", Toast.LENGTH_SHORT).show()
                return false
            } else {
                return true
            }
        }

        //------------------------------------------------------------------------------------------------------------------
        //funcao para retornar o tipo de carro

        fun selecionarTipo(): String {
            val idSelecionado = menu.RadioG.checkedRadioButtonId

            return when (idSelecionado) {
                com.example.inspectionchecklist.R.id.radioLP -> "Ligeiro Particular"
                com.example.inspectionchecklist.R.id.radioLTP -> "Ligeiro Transporte Público"
                com.example.inspectionchecklist.R.id.radioP -> "Pesado"
                else -> ""
            }
        }

        //------------------------------------------------------------------------------------------------------------------


        fun limparCampos() {

            //resetar todos os campos
            menu.spinnerIlha.setSelection(0)
            menu.restomatricula.text.clear()
            menu.inputMarca.text.clear()
            menu.inputModelo.text.clear()
            menu.inputProp.text.clear()
            menu.inputAno.text.clear()
            menu.RadioG.clearCheck()

            //foco para o primeiro campo
            menu.restomatricula.requestFocus()
        }

        //------------------------------------------------------------------------------------------------------------------





        //adiciona carro


        menu.buttonAdicionarCar.setOnClickListener {

            validarMatricula()
            validarInputs()
            validarAno()
            validarTipo()

            if (validarMatricula() && validarInputs() && validarAno() && validarTipo()){ //se tiver td validado

                val matricula = menu.spinnerIlha.selectedItem.toString()+menu.restomatricula.text.toString().trim().uppercase()
                val marca = menu.inputMarca.text.toString()
                val modelo = menu.inputModelo.text.toString()
                val prop = menu.inputProp.text.toString()
                val ano = menu.inputAno.text.toString()
                val tipo = selecionarTipo()
                val carro = Carro(matricula,marca,modelo,prop,ano,tipo)

                if (posicaoEdicao != -1) { //se a posicao for diferente de -1 quer dizer q recebeu extra ou seja é ediçao de carro

                    //se a matricula editada existe e nao for o mesmo do carro editado
                    if(DataManager.listaCarros.indices.any { i -> DataManager.listaCarros[i].matricula == matricula && i != posicaoEdicao }){

                        Toast.makeText(this, "Matricula Existente!", Toast.LENGTH_SHORT).show()

                    } else { //entao ta tudo otimo atualiza os dados do carro

                        DataManager.listaCarros[posicaoEdicao] = carro
                        Toast.makeText(this, "Dados atualizados!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                //caso no for ediçao
                } else {

                    if(DataManager.listaCarros.any { it.matricula == matricula }){
                        Toast.makeText(this, "Matricula Existente!", Toast.LENGTH_SHORT).show()
                    } else {

                        DataManager.listaCarros.add(carro)
                        DataManager.listaCarroPeriodico.add(carro)
                        DataManager.salvarDados(this)
                        Toast.makeText(this, "Veiculo Adicionado!", Toast.LENGTH_SHORT).show()
                        limparCampos()
                    }

                }

            }

        }


        }

    //confirma saida
    private fun confirmarSaida() {
        // Verificacao se algum campo foi preenchido
        val temDadosPreenchidos = menu.restomatricula.text.isNotEmpty() ||
                menu.inputMarca.text.isNotEmpty() ||
                menu.inputModelo.text.isNotEmpty() ||
                menu.inputProp.text.isNotEmpty() ||
                menu.inputAno.text.isNotEmpty()

        if (temDadosPreenchidos) {
            AlertDialog.Builder(this)
                .setTitle("Descartar alterações?")
                .setMessage("Tem a certeza que deseja sair? Os dados preenchidos serão perdidos.")
                .setPositiveButton("Sair") { _, _ -> finish() }
                .setNegativeButton("Continuar a editar", null)
                .show()
        } else {
            finish()
        }

    }
}