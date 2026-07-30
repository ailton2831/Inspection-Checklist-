package com.example.inspectionchecklist.Inspeccao.Reinspeccao

import android.os.Bundle
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.inspectionchecklist.DataManager
import com.example.inspectionchecklist.Inspeccao.Periodica.Inspecao
import com.example.inspectionchecklist.R
import com.example.inspectionchecklist.databinding.ReinspeccaoBinding

class ReinspeccaoActivity : AppCompatActivity() {
    private lateinit var menu: ReinspeccaoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        menu = ReinspeccaoBinding.inflate(layoutInflater)

        setContentView(menu.root)

        //receber a inspeçao e posicao na lista

        val inspecao = intent.getSerializableExtra("REINSPECÇAO") as Inspecao
        val posicao = intent.getIntExtra("EDITAR_POSICAO", -1)

        val itensFalhos = inspecao.categoriasFalhadas

        //----------------------------tela reinspeçao de acordo com categoria falha---(ajuda de Gemini)--------------------

        val view = menu.main

        for (i in itensFalhos){

            val viewItem = layoutInflater.inflate(R.layout.checklist,null)

            val txtTitulo = viewItem.findViewById<TextView>(R.id.categoria)
            txtTitulo.text = i

            view.addView(viewItem,0)

        }



        menu.buttonInspeccao.setOnClickListener {
            val container = menu.main
            val novasFalhas = mutableListOf<String>()
            var algumNaoMarcado = false


            //Percorre todas as views infladas dinamicamente

            for (i in 0 until container.childCount) {
                val viewItem = container.getChildAt(i)
                val rg = viewItem.findViewById<RadioGroup>(R.id.group)
                val txtTitulo = viewItem.findViewById<TextView>(R.id.categoria)

                if (rg != null) {
                    val checkId = rg.checkedRadioButtonId

                    if (checkId == -1) {
                        algumNaoMarcado = true //se tiver item nao marcado
                    } else if (checkId == R.id.radioNC) {
                        // Se marcou nao Conforme, a categoria continua na lista de falhas
                        novasFalhas.add(txtTitulo.text.toString())
                    }
                }
            }


            //aviso se caso nao tiver algum radio vazio
            if (algumNaoMarcado) {
                Toast.makeText(this, "Por favor, marque todos os campos", Toast.LENGTH_SHORT).show()
            }


            if (novasFalhas.isEmpty()) {

                //se tiver tudo aprovado
                val inspecaoFinal = Inspecao(
                    Matricula = inspecao.Matricula,
                    tipo = "Reinspeção",
                    resultado = "Aprovado",
                    obs = menu.obs.text.toString(),
                    categoriasFalhadas = mutableListOf()
                )
                DataManager.allInspecoes.add(inspecaoFinal)
                DataManager.reinspecoes.removeAt(posicao)
                DataManager.salvarDados(this)
                Toast.makeText(this, "Veículo Aprovado!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                // ainda tem items nao conformes
                //Atualiza a nova lista reduzida de falhas
                DataManager.reinspecoes[posicao].categoriasFalhadas = novasFalhas
                DataManager.salvarDados(this)
                Toast.makeText(this, "O veículo continua em reinspeção.", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }
}