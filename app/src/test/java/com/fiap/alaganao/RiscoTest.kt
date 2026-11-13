package com.fiap.alaganao

import com.fiap.alaganao.data.Niveis
import com.fiap.alaganao.data.calcularRisco
import com.fiap.alaganao.data.nomeDoRisco
import org.junit.Assert.assertEquals
import org.junit.Test

class RiscoTest {

    @Test
    fun semRelatos_riscoZero() {
        assertEquals(0, calcularRisco(emptyList()))
    }

    @Test
    fun relatoGrave_somaSetenta() {
        assertEquals(70, calcularRisco(listOf(Niveis.GRAVE)))
    }

    @Test
    fun varios_relatos_somamMasNaoPassamDeCem() {
        val niveis = listOf(Niveis.GRAVE, Niveis.GRAVE, Niveis.MODERADO)
        assertEquals(100, calcularRisco(niveis))
    }

    @Test
    fun nomeDoRisco_respeitaAsFaixas() {
        assertEquals("Tranquilo", nomeDoRisco(0))
        assertEquals("Atenção", nomeDoRisco(40))
        assertEquals("Risco alto", nomeDoRisco(70))
    }
}
