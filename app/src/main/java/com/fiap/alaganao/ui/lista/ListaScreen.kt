package com.fiap.alaganao.ui.lista

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fiap.alaganao.data.Relato
import com.fiap.alaganao.data.formatarData
import com.fiap.alaganao.ui.theme.Verde
import com.fiap.alaganao.ui.theme.corDoNivel

enum class Filtro(val texto: String) {
    TODOS("Todos"),
    ALAGANDO("Alagando"),
    RESOLVIDOS("Água baixou")
}

@Composable
fun ListaScreen(
    modifier: Modifier = Modifier,
    viewModel: ListaViewModel = hiltViewModel(),
    onNovoRelato: () -> Unit,
    onEditar: (Long) -> Unit
) {
    val relatos by viewModel.relatos.collectAsState()
    var filtro by remember { mutableStateOf(Filtro.TODOS) }
    var relatoParaExcluir by remember { mutableStateOf<Relato?>(null) }
    val context = LocalContext.current

    val filtrados = when (filtro) {
        Filtro.TODOS -> relatos
        Filtro.ALAGANDO -> relatos.filter { !it.aguaBaixou }
        Filtro.RESOLVIDOS -> relatos.filter { it.aguaBaixou }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Filtro.entries.forEach { opcao ->
                    FilterChip(
                        selected = filtro == opcao,
                        onClick = { filtro = opcao },
                        label = { Text(opcao.texto) }
                    )
                }
            }

            if (filtrados.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (relatos.isEmpty()) {
                            "Nenhum relato ainda. Toque no + para avisar que a sua rua está alagando."
                        } else {
                            "Nenhum relato neste filtro."
                        },
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 88.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filtrados, key = { it.id }) { relato ->
                        RelatoCard(
                            relato = relato,
                            onAlternarStatus = {
                                viewModel.alternarStatus(relato)
                                val texto = if (relato.aguaBaixou) {
                                    "Relato reaberto: voltou a alagar"
                                } else {
                                    "Marcado: a água baixou"
                                }
                                Toast.makeText(context, texto, Toast.LENGTH_SHORT).show()
                            },
                            onEditar = { onEditar(relato.id) },
                            onExcluir = { relatoParaExcluir = relato }
                        )
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = onNovoRelato,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Novo relato")
        }
    }

    val alvo = relatoParaExcluir
    if (alvo != null) {
        AlertDialog(
            onDismissRequest = { relatoParaExcluir = null },
            title = { Text("Excluir relato?") },
            text = { Text("O relato de ${alvo.rua} será apagado e não poderá ser recuperado.") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.excluir(alvo)
                    relatoParaExcluir = null
                    Toast.makeText(context, "Relato excluído", Toast.LENGTH_SHORT).show()
                }) {
                    Text("Excluir")
                }
            },
            dismissButton = {
                TextButton(onClick = { relatoParaExcluir = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
private fun RelatoCard(
    relato: Relato,
    onAlternarStatus: () -> Unit,
    onEditar: () -> Unit,
    onExcluir: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .background(corDoNivel(relato.nivel), CircleShape)
                )
                Text(
                    text = relato.rua,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp)
                )
                Text(
                    text = relato.nivel,
                    style = MaterialTheme.typography.labelLarge
                )
            }

            Text(
                text = relato.bairro,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (relato.descricao.isNotBlank()) {
                Text(
                    text = relato.descricao,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Text(
                text = formatarData(relato.dataHora),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp)
            )

            Text(
                text = if (relato.aguaBaixou) "A água baixou" else "Ainda alagando",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = if (relato.aguaBaixou) Verde else MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(top = 4.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onAlternarStatus) {
                    Text(if (relato.aguaBaixou) "Reabrir" else "A água baixou")
                }
                Box(modifier = Modifier.weight(1f))
                IconButton(onClick = onEditar) {
                    Icon(Icons.Filled.Edit, contentDescription = "Editar relato")
                }
                IconButton(onClick = onExcluir) {
                    Icon(Icons.Filled.Delete, contentDescription = "Excluir relato")
                }
            }
        }
    }
}
