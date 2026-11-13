package com.fiap.alaganao.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RelatoRepository @Inject constructor(
    private val dao: RelatoDao
) {

    fun listarTodos(): Flow<List<Relato>> = dao.listarTodos()

    suspend fun buscarPorId(id: Long): Relato? = dao.buscarPorId(id)

    suspend fun salvar(relato: Relato) = dao.salvar(relato)

    suspend fun atualizarStatus(id: Long, aguaBaixou: Boolean) =
        dao.atualizarStatus(id, aguaBaixou)

    suspend fun excluir(id: Long) = dao.excluir(id)
}
