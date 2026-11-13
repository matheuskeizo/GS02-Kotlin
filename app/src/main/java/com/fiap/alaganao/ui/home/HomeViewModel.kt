package com.fiap.alaganao.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiap.alaganao.data.Relato
import com.fiap.alaganao.data.RelatoRepository
import com.fiap.alaganao.data.calcularRisco
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class RuaEmRisco(
    val nome: String,
    val bairro: String,
    val risco: Int,
    val relatosAtivos: Int
)

data class HomeUiState(
    val ruasEmRisco: List<RuaEmRisco> = emptyList(),
    val recentes: List<Relato> = emptyList(),
    val alagando: Int = 0,
    val aguaBaixou: Int = 0
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: RelatoRepository
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> = repository.listarTodos()
        .map { relatos ->
            val ativos = relatos.filter { !it.aguaBaixou }

            // agrupa os relatos ativos por rua e calcula o risco de cada uma
            val ruas = ativos
                .groupBy { it.rua.trim().lowercase() }
                .map { (_, doTrecho) ->
                    RuaEmRisco(
                        nome = doTrecho.first().rua,
                        bairro = doTrecho.first().bairro,
                        risco = calcularRisco(doTrecho.map { it.nivel }),
                        relatosAtivos = doTrecho.size
                    )
                }
                .sortedByDescending { it.risco }

            HomeUiState(
                ruasEmRisco = ruas,
                recentes = relatos.take(3),
                alagando = ativos.size,
                aguaBaixou = relatos.size - ativos.size
            )
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), HomeUiState())
}
