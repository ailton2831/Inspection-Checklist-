package com.example.inspectionchecklist

import android.content.Context
import com.example.inspectionchecklist.Carros.Carro
import com.example.inspectionchecklist.Inspeccao.Periodica.Inspecao
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

object DataManager {

    private const val FICHEIRO_VEICULOS = "veiculos.json"
    private const val FICHEIRO_INSPECOES = "inspecoes.json"
    private const val FICHEIRO_REINSPECOES = "reinspecoes.json"

    private val gson = Gson()

    var listaCarros: MutableList<Carro> = mutableListOf()
    var allInspecoes: MutableList<Inspecao> = mutableListOf()
    var reinspecoes: MutableList<Inspecao> = mutableListOf()
    var listaCarroPeriodico: MutableList<Carro> = mutableListOf()

    fun carregarDados(context: Context){
        listaCarros = lerArquivoJson(context, FICHEIRO_VEICULOS)
        allInspecoes = lerArquivoJson(context, FICHEIRO_INSPECOES)
        reinspecoes = lerArquivoJson(context, FICHEIRO_REINSPECOES)

        val matriculasInspecionadas = allInspecoes.map { it.Matricula }.toSet()
        listaCarroPeriodico = listaCarros.filter { it.matricula !in matriculasInspecionadas }.toMutableList()

    }

    fun salvarDados(context: Context){
        salvarArquivoJson(context, FICHEIRO_VEICULOS, listaCarros)
        salvarArquivoJson(context, FICHEIRO_INSPECOES, allInspecoes)
        salvarArquivoJson(context, FICHEIRO_REINSPECOES, reinspecoes)
    }


    private inline fun <reified T> lerArquivoJson(context: Context, fileName: String): MutableList<T> {
        val file = File(context.filesDir, fileName)
        if (!file.exists()) return mutableListOf()

        return try {
            val json = file.readText()
            val type = object : TypeToken<MutableList<T>>() {}.type
            gson.fromJson(json, type) ?: mutableListOf()
        } catch (e: Exception) {
            e.printStackTrace()
            mutableListOf()
        }
    }

    private fun <T> salvarArquivoJson(context: Context, fileName: String, lista: List<T>) {
        try {
            val json = gson.toJson(lista)
            val file = File(context.filesDir, fileName)
            file.writeText(json)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}