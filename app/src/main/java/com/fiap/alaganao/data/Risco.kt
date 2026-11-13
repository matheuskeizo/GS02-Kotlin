package com.fiap.alaganao.data

const val RISCO_ATENCAO = 20
const val RISCO_ALTO = 60

// Nota de 0 a 100 de uma rua, somando o peso dos relatos ativos dela
fun calcularRisco(niveisDosRelatos: List<String>): Int {
    val soma = niveisDosRelatos.sumOf { Niveis.peso(it) }
    return soma.coerceAtMost(100)
}

fun nomeDoRisco(nota: Int): String = when {
    nota >= RISCO_ALTO -> "Risco alto"
    nota >= RISCO_ATENCAO -> "Atenção"
    else -> "Tranquilo"
}
