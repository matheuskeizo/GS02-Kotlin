package com.fiap.alaganao.ui.home

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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fiap.alaganao.data.Relato
import com.fiap.alaganao.data.formatarData
import com.fiap.alaganao.data.nomeDoRisco
import com.fiap.alaganao.ui.theme.Verde
import com.fiap.alaganao.ui.theme.corDoNivel
import com.fiap.alaganao.ui.theme.corDoRisco

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onNovoRelato: () -> Unit,
    onVerRelatos: () -> Unit
) {
    val estado by viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Alaga Não",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "Os moradores avisam onde está alagando e o app mostra " +
                            "quais ruas estão com mais risco agora.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Text(
                        text = "Para quem: moradores de áreas que alagam, idosos, " +
                            "motoristas e quem depende de ônibus.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ResumoCard(
                    titulo = "Alagando agora",
                    valor = estado.alagando,
                    modifier = Modifier.weight(1f)
                )
                ResumoCard(
                    titulo = "Água baixou",
                    valor = estado.aguaBaixou,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = onNovoRelato,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Está alagando aqui")
                }
                OutlinedButton(
                    onClick = onVerRelatos,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ver todos os relatos")
                }
            }
        }

        item {
            Text(
                text = "Ruas em risco agora",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
        }

        if (estado.ruasEmRisco.isEmpty()) {
            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Nenhum alagamento ativo",
                            style = MaterialTheme.typography.titleMedium,
                            color = Verde
                        )
                        Text(
                            text = "Quando alguém registrar um relato, a rua aparece aqui com o nível de risco.",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        } else {
            items(estado.ruasEmRisco, key = { it.nome }) { rua ->
                RuaCard(rua)
            }
        }

        if (estado.recentes.isNotEmpty()) {
            item {
                Text(
                    text = "Últimos relatos",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
            items(estado.recentes, key = { "recente-${it.id}" }) { relato ->
                RelatoRecente(relato)
            }
        }
    }
}

@Composable
private fun ResumoCard(titulo: String, valor: Int, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = valor.toString(),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
            Text(text = titulo, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun RuaCard(rua: RuaEmRisco) {
    val cor = corDoRisco(rua.risco)

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(cor, CircleShape)
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp)
                ) {
                    Text(
                        text = rua.nome,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = rua.bairro,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Text(
                    text = nomeDoRisco(rua.risco),
                    style = MaterialTheme.typography.labelLarge,
                    color = cor
                )
            }
            LinearProgressIndicator(
                progress = { rua.risco / 100f },
                color = cor,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            )
            Text(
                text = "${rua.relatosAtivos} relato(s) ativo(s) nesta rua",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
private fun RelatoRecente(relato: Relato) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(corDoNivel(relato.nivel), CircleShape)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
            ) {
                Text(text = relato.rua, style = MaterialTheme.typography.titleSmall)
                Text(
                    text = "${relato.bairro} • ${formatarData(relato.dataHora)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = if (relato.aguaBaixou) "Água baixou" else relato.nivel,
                style = MaterialTheme.typography.labelMedium,
                color = if (relato.aguaBaixou) Verde else MaterialTheme.colorScheme.secondary
            )
        }
    }
}
