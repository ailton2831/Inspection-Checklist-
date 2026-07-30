package com.example.inspectionchecklist.DashBoard

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.inspectionchecklist.Carros.Carro
import com.example.inspectionchecklist.DataManager
import com.example.inspectionchecklist.Inspeccao.Periodica.Inspecao
import com.example.inspectionchecklist.databinding.DashboardBinding

class DashboardActivity : AppCompatActivity() {
    private lateinit var menu: DashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        menu = DashboardBinding.inflate(layoutInflater)

        setContentView(menu.root)

        //a lista pode ser nula

        val listaVeiculos = DataManager.listaCarros ?: listOf<Carro>()
        val listaInspeçao = DataManager.allInspecoes ?: listOf<Inspecao>()


        if(listaInspeçao.isEmpty()){
            menu.totalinspeOes.text = ""
            menu.totalaprovado.text = ""
            menu.totalreprovado.text = ""
            menu.total2026.text = ""
            menu.totalperiodica.text = ""
            menu.totalreinspeAo.text = ""
        }

        if(listaVeiculos.isEmpty()){
            menu.totalveiculo.text = ""
            menu.totalligeirop.text =""
            menu.totalligeirotp.text = ""
            menu.totalpesado.text = ""
        }

        var totalInspeçao = 0
        var inspeçao2026 = listaInspeçao.filter{it.data.contains("2026")}.size
        var periodico = 0
        var reinspecao = 0
        var totalveiculo = 0
        var ligeiroP = 0
        var ligeiroTP = 0
        var pesado = 0


        //iterar na lista de veiculo para incrementar total de veiculos, e tipos de veiculos
        for (i in listaVeiculos){
            totalveiculo++
            if(i.tipo == "Ligeiro Particular"){
                ligeiroP++
            }
            if(i.tipo == "Ligeiro Transporte Público"){
                ligeiroTP++
            }
            if(i.tipo == "Pesado"){
                pesado++
            }
        }




        // iterar na lista de inspeçao e incrimentar total de inspeçao,aprovados,reprovados, periodico e reinspeçao

        var totalA = 0
        var totalR = 0

        for (i in listaInspeçao){
            totalInspeçao++
            if(i.resultado == "Aprovado"){
                totalA++
            }
            if(i.resultado == "Reprovado"){
                totalR++
            }
            if(i.tipo == "Periodico"){
                periodico++
            }
            if(i.tipo == "Reinspeção"){
                reinspecao++
            }
        }

        //calculo de % de aprovaçao e reprovaçao

        var aprovado = 0.0
        var reprovado = 0.0

        if (totalInspeçao > 0) {
            aprovado = (totalA.toDouble() / totalInspeçao) * 100
            reprovado = (totalR.toDouble() / totalInspeçao) * 100
        }


        menu.totalinspeOes.text = totalInspeçao.toString()
        menu.totalaprovado.text = String.format("%.1f%%", aprovado)
        menu.totalreprovado.text = String.format("%.1f%%", reprovado)
        menu.total2026.text = inspeçao2026.toString()
        menu.totalperiodica.text = periodico.toString()
        menu.totalreinspeAo.text = reinspecao.toString()
        menu.totalveiculo.text = totalveiculo.toString()
        menu.totalligeirop.text = ligeiroP.toString()
        menu.totalligeirotp.text = ligeiroTP.toString()
        menu.totalpesado.text = pesado.toString()

    }
}