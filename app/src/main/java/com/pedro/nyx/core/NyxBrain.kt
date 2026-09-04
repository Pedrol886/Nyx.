package com.pedro.nyx.core

class NyxBrain {

    // Palavras-chave que ativam os comandos de controle do celular
    private val acoesSistema = listOf("abrir", "desbloquear", "ligar", "tocar", "fechar")

    fun processarEntrada(texto: String): String {
        val textoFormatado = texto.lowercase().trim()

        // Verifica se é um comando de controle do aparelho
        if (acoesSistema.any { textoFormatado.contains(it) }) {
            return executarComandoSistema(textoFormatado)
        }

        // Se não for comando, trata como uma pergunta/pesquisa offline
        return consultarBaseOffline(textoFormatado)
    }

    private fun executarComandoSistema(comando: String): String {
        // Aqui entrará a lógica de integração com o Shizuku ou Acessibilidade
        return "Nyx Executando Ação: Identifiquei o comando de sistema -> '$comando'"
    }

    private fun consultarBaseOffline(pergunta: String): String {
        // Aqui é onde o modelo de IA leve (LLM local) processará a resposta sem internet.
        // Por enquanto, simulamos a resposta offline:
        return "Nyx (Offline): Processando resposta local para '$pergunta'..."
    }
}
