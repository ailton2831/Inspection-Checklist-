package com.example.inspectionchecklist.Carros

import android.R
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.inspectionchecklist.DataManager
import com.example.inspectionchecklist.databinding.ListacarrosBinding

class ListacarrosActivity : AppCompatActivity() {
    private lateinit var menu: ListacarrosBinding

    override fun onResume() {
        super.onResume()

        val dadosExibicao = DataManager.listaCarros.map { "${it.matricula} - ${it.marca} ${it.modelo}\nDono : ${it.proprietario} \nTipo: ${it.tipo}" }

        val adapter = ArrayAdapter(this, R.layout.simple_list_item_1, dadosExibicao)
        menu.listacarros.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        menu = ListacarrosBinding.inflate(layoutInflater)

        setContentView(menu.root)

        //---------------------------------------------------------------------------------

        //filtro de lista de carro por tipo

        val itens = mutableListOf("Todos","Ligeiro Particular","Ligeiro Transporte Público","Pesado")
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item,itens)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        menu.spinnerTipo.adapter = adapter


        menu.buttonFiltro.setOnClickListener {
            val tipoSelecionado = menu.spinnerTipo.selectedItem.toString()


            val listaFiltrada = if (tipoSelecionado == "Todos") {
                DataManager.listaCarros
            } else {
                DataManager.listaCarros.filter { it.tipo == tipoSelecionado }
            }

            // Mapeia e atualiza o Adapter
            val dadosExibicao = listaFiltrada.map {
                "${it.matricula} - ${it.marca} ${it.modelo}\nDono :${it.proprietario} \nTipo: ${it.tipo}\nAno de Matricula:${it.ano}"
            }

            val adapter = ArrayAdapter(this, R.layout.simple_list_item_1, dadosExibicao)
            menu.listacarros.adapter = adapter
        }

        //---------------------------------------------------------------------------------------

        //listar todos os carros

        val lista = DataManager.listaCarros

        if (lista != null) {

            val dadosExibicao = lista.map { "${it.matricula} - ${it.marca} ${it.modelo}\nDono :${it.proprietario} \nTipo: ${it.tipo}\nAno de Matricula:${it.ano} " }

            val adapter = ArrayAdapter(this, R.layout.simple_list_item_1, dadosExibicao)
            menu.listacarros.adapter = adapter
        }

        //---------------------------------------------------------------------------------------

        //funçao de selecionar opcao de remover ou editar carro

        fun mostrarOpcoes(posicao: Int) {

            val carro = DataManager.listaCarros[posicao]


            val tela = AlertDialog.Builder(this)
            tela.setTitle("Seleciona uma opção")
            tela.setMessage("Veículo: ${carro.matricula}")



            tela.setPositiveButton("Editar") { _, _ ->
                val intent = Intent(this, NewvehicleActivity::class.java)
                intent.putExtra("EDITAR_POSICAO", posicao)
                intent.putExtra("CARRO_OBJETO", carro)
                startActivity(intent)
            }


            tela.setNegativeButton("Remover") { _, _ ->

                val carro = DataManager.listaCarros[posicao]
                val temInspecao = DataManager.allInspecoes.any { it.Matricula == carro.matricula }

                if (temInspecao) { //se caso o veiculo tiver uma inspeçao associado nao pode ser removido
                    Toast.makeText(
                        this,
                        "Veículo inspecionado, não pode ser removido!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val tela2 = AlertDialog.Builder(this)
                    tela2.setTitle("Remover")
                    tela2.setMessage("Tem certeza que deseja remover o veículo ${carro.matricula}?")

                    tela2.setPositiveButton("Remover") { _, _ ->
                        DataManager.listaCarros.removeAt(posicao)
                        DataManager.listaCarroPeriodico.removeAt(posicao)
                        DataManager.salvarDados(this)
                        Toast.makeText(this, "Removido com sucesso!", Toast.LENGTH_SHORT).show()
                        recreate()
                    }
                    tela2.setNeutralButton("Cancelar", null)
                    tela2.show()
                }
            }



            tela.setNeutralButton("Cancelar", null)
            tela.show()
        }

        //---------------------------------------------------------------------------------------

        //ao clicar mostra as opçoes

        menu.listacarros.setOnItemClickListener { _, _, position, _ ->
            mostrarOpcoes(position)
        }

        //---------------------------------------------------------------------------------------

    }

}
