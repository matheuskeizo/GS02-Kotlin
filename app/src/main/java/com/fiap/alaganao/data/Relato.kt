package com.fiap.alaganao.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "relatos")
data class Relato(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val rua: String,
    val bairro: String,
    val nivel: String,
    val descricao: String,
    val dataHora: Long,
    val aguaBaixou: Boolean = false
)
