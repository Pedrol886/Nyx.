package com.pedro.nyx

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.pedro.nyx.core.NyxBrain

class MainActivity : AppCompatActivity() {

    private lateinit var nyxBrain: NyxBrain
    private val PERMISSION_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Cria uma interface visual simples gerada por código para testes iniciais
        val layout = android.widget.LinearLayout(this).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding(40, 40, 40, 40)
        }

        val titulo = TextView(this).apply {
            text = "Nyx - Assistente Pessoal Offline"
            textSize = 20f
            setPadding(0, 0, 0, 20)
        }

        val inputComando = EditText(this).apply {
            hint = "Digite um comando ou pergunta..."
        }

        val btnEnviar = Button(this).apply {
            text = "Enviar para o Nyx"
        }

        val txtResposta = TextView(this).apply {
            text = "Resposta aparecerá aqui..."
            setPadding(0, 20, 0, 0)
            textSize = 16f
        }

        layout.addView(titulo)
        layout.addView(inputComando)
        layout.addView(btnEnviar)
        layout.addView(txtResposta)

        setContentView(layout)

        // Inicializa o cérebro
        nyxBrain = NyxBrain()

        // Ação do botão
        btnEnviar.setOnClickListener {
            val textoUsuario = inputComando.text.toString()
            if (textoUsuario.isNotEmpty()) {
                val resposta = nyxBrain.processarEntrada(textoUsuario)
                txtResposta.text = resposta
            }
        }

        // Verifica permissão de microfone para futuras etapas
        verificarPermissoes()
    }

    private fun verificarPermissoes() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) 
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this, 
                arrayOf(Manifest.permission.RECORD_AUDIO), 
                PERMISSION_REQUEST_CODE
            )
        }
    }
}