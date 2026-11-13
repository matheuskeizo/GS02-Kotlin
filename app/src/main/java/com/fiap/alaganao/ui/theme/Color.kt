package com.fiap.alaganao.ui.theme

import androidx.compose.ui.graphics.Color
import com.fiap.alaganao.data.RISCO_ALTO
import com.fiap.alaganao.data.RISCO_ATENCAO
import com.fiap.alaganao.data.Niveis

val AzulAgua = Color(0xFF0B5C8F)
val AzulAguaClaro = Color(0xFF8CC8EE)
val AzulFundo = Color(0xFFD6ECFA)
val AzulEscuro = Color(0xFF063A5C)
val LaranjaAlerta = Color(0xFFE8710A)
val FundoClaro = Color(0xFFF4F9FC)
val TextoEscuro = Color(0xFF14202B)
val CinzaTexto = Color(0xFF4B5B6A)
val FundoEscuro = Color(0xFF0F1A24)
val SuperficieEscura = Color(0xFF1A2733)

// cores do "semáforo" do mapa de risco
val Verde = Color(0xFF2E7D32)
val Amarelo = Color(0xFFF2A900)
val Vermelho = Color(0xFFC62828)

fun corDoRisco(nota: Int): Color = when {
    nota >= RISCO_ALTO -> Vermelho
    nota >= RISCO_ATENCAO -> Amarelo
    else -> Verde
}

fun corDoNivel(nivel: String): Color = when (nivel) {
    Niveis.GRAVE -> Vermelho
    Niveis.MODERADO -> Amarelo
    else -> Verde
}
