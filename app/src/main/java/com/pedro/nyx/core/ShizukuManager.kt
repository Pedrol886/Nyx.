package com.pedro.nyx.core

import android.content.pm.PackageManager
import rikka.shizuku.Shizuku
import java.io.BufferedReader
import java.io.InputStreamReader

object ShizukuManager {

    // Verifica se o Shizuku está instalado e rodando no celular
    fun estaAtivo(): Boolean {
        return try {
            Shizuku.pingBinder()
        } catch (e: Exception) {
            false
        }
    }

    // Solicita permissão do Shizuku ao usuário (aparece um pop-up na primeira vez)
    fun requisitarPermissao() {
        if (estaAtivo() && Shizuku.checkSelfPermission() != PackageManager.PERMISSION_GRANTED) {
            try {
                Shizuku.requestPermission(0)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Executa um comando ADB via Shizuku sem precisar de PC
    fun executarComandoAdb(comando: String): String {
        if (!estaAtivo()) {
            return "Erro: Shizuku não está ativo ou não foi iniciado via Depuração por Wi-Fi."
        }

        if (Shizuku.checkSelfPermission() != PackageManager.PERMISSION_GRANTED) {
            return "Erro: Permissão do Shizuku negada pelo usuário."
        }

        return try {
            // Roda o comando no shell do sistema com privilégios ADB
            val processo = Shizuku.newProcess(arrayOf("sh", "-c", comando), null, null)
            val leitor = BufferedReader(InputStreamReader(processo.inputStream))
            val saida = StringBuilder()
            var linha: String?
            
            while (leitor.readLine().also { linha = it } != null) {
                saida.append(linha).append("\n")
            }
            processo.waitFor()
            saida.toString().ifEmpty { "Comando executado com sucesso." }
        } catch (e: Exception) {
            "Erro ao executar comando: ${e.message}"
        }
    }
}
