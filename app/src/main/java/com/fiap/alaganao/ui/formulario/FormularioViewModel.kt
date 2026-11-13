package com.fiap.alaganao.ui.formulario

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiap.alaganao.data.Niveis
import com.fiap.alaganao.data.Relato
import com.fiap.alaganao.data.RelatoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FormularioUiState(
    val rua: String = "",
    val bairro: String = "",
    val nivel: String = Niveis.MODERADO,
    val descricao: String = "",
    val erroRua: String? = null,
    val erroBairro: String? = null,
    val editando: Boolean = false
)

@HiltViewModel
class FormularioViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: RelatoRepository
) : ViewModel() {

    private val idRelato: Long = savedStateHandle.get<Long>("id") ?: -1L

    private val _estado = MutableStateFlow(FormularioUiState(editando = idRelato != -1L))
    val estado: StateFlow<FormularioUiState> = _estado.asStateFlow()

    // guarda o relato que está sendo editado para manter data e status
    private var relatoOriginal: Relato? = null

    init {
        if (idRelato != -1L) {
            viewModelScope.launch {
                val relato = repository.buscarPorId(idRelato)
                if (relato != null) {
                    relatoOriginal = relato
                    _estado.value = _estado.value.copy(
                        rua = relato.rua,
                        bairro = relato.bairro,
                        nivel = relato.nivel,
                        descricao = relato.descricao
                    )
                }
            }
        }
    }

    fun onRuaChange(valor: String) {
        _estado.value = _estado.value.copy(rua = valor, erroRua = null)
    }

    fun onBairroChange(valor: String) {
        _estado.value = _estado.value.copy(bairro = valor, erroBairro = null)
    }

    fun onNivelChange(valor: String) {
        _estado.value = _estado.value.copy(nivel = valor)
    }

    fun onDescricaoChange(valor: String) {
        _estado.value = _estado.value.copy(descricao = valor)
    }

    // onSalvo é chamado só depois que o relato foi gravado no banco
    fun salvar(onSalvo: () -> Unit) {
        val atual = _estado.value

        val erroRua = when {
            atual.rua.isBlank() -> "Informe o nome da rua"
            atual.rua.trim().length < 3 -> "O nome da rua precisa ter pelo menos 3 letras"
            else -> null
        }
        val erroBairro = if (atual.bairro.isBlank()) "Informe o bairro" else null

        if (erroRua != null || erroBairro != null) {
            _estado.value = atual.copy(erroRua = erroRua, erroBairro = erroBairro)
            return
        }

        val original = relatoOriginal
        if (atual.editando && original == null) {
            // o relato ainda não terminou de carregar
            return
        }

        viewModelScope.launch {
            val relato = Relato(
                id = original?.id ?: 0,
                rua = atual.rua.trim(),
                bairro = atual.bairro.trim(),
                nivel = atual.nivel,
                descricao = atual.descricao.trim(),
                dataHora = original?.dataHora ?: System.currentTimeMillis(),
                aguaBaixou = original?.aguaBaixou ?: false
            )
            repository.salvar(relato)
            onSalvo()
        }
    }
}
