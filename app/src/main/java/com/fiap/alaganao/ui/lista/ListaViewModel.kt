package com.fiap.alaganao.ui.lista

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiap.alaganao.data.Relato
import com.fiap.alaganao.data.RelatoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListaViewModel @Inject constructor(
    private val repository: RelatoRepository
) : ViewModel() {

    val relatos: StateFlow<List<Relato>> = repository.listarTodos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun alternarStatus(relato: Relato) {
        viewModelScope.launch {
            repository.atualizarStatus(relato.id, !relato.aguaBaixou)
        }
    }

    fun excluir(relato: Relato) {
        viewModelScope.launch {
            repository.excluir(relato.id)
        }
    }
}
