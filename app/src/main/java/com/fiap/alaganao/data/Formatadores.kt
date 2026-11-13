package com.fiap.alaganao.data

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatarData(milissegundos: Long): String {
    val formato = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.forLanguageTag("pt-BR"))
    return formato.format(Date(milissegundos))
}
