package com.fiap.alaganao.data

// Níveis que o morador pode escolher ao relatar um alagamento
object Niveis {
    const val LEVE = "Leve"
    const val MODERADO = "Moderado"
    const val GRAVE = "Grave"

    val todos = listOf(LEVE, MODERADO, GRAVE)

    fun explicacao(nivel: String): String = when (nivel) {
        LEVE -> "Água na sarjeta, dá para passar a pé."
        MODERADO -> "Carros passam devagar, pedestres têm dificuldade."
        GRAVE -> "Rua intransitável, risco para pessoas e veículos."
        else -> ""
    }

    // quanto cada relato ativo pesa no risco da rua
    fun peso(nivel: String): Int = when (nivel) {
        LEVE -> 20
        MODERADO -> 40
        GRAVE -> 70
        else -> 0
    }
}
