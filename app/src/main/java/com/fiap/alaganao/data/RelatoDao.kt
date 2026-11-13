package com.fiap.alaganao.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface RelatoDao {

    @Query("SELECT * FROM relatos ORDER BY dataHora DESC")
    fun listarTodos(): Flow<List<Relato>>

    @Query("SELECT * FROM relatos WHERE id = :id")
    suspend fun buscarPorId(id: Long): Relato?

    @Upsert
    suspend fun salvar(relato: Relato)

    @Query("UPDATE relatos SET aguaBaixou = :aguaBaixou WHERE id = :id")
    suspend fun atualizarStatus(id: Long, aguaBaixou: Boolean)

    @Query("DELETE FROM relatos WHERE id = :id")
    suspend fun excluir(id: Long)
}
