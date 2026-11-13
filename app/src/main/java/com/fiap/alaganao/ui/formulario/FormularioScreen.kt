package com.fiap.alaganao.ui.formulario

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fiap.alaganao.data.Niveis

@Composable
fun FormularioScreen(
    modifier: Modifier = Modifier,
    viewModel: FormularioViewModel = hiltViewModel(),
    onVoltar: () -> Unit
) {
    val estado by viewModel.estado.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Onde está alagando?",
            style = MaterialTheme.typography.titleLarge
        )

        OutlinedTextField(
            value = estado.rua,
            onValueChange = { viewModel.onRuaChange(it) },
            label = { Text("Rua *") },
            singleLine = true,
            isError = estado.erroRua != null,
            supportingText = { estado.erroRua?.let { Text(it) } },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = estado.bairro,
            onValueChange = { viewModel.onBairroChange(it) },
            label = { Text("Bairro *") },
            singleLine = true,
            isError = estado.erroBairro != null,
            supportingText = { estado.erroBairro?.let { Text(it) } },
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "Gravidade",
            style = MaterialTheme.typography.titleMedium
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Niveis.todos.forEach { nivel ->
                FilterChip(
                    selected = estado.nivel == nivel,
                    onClick = { viewModel.onNivelChange(nivel) },
                    label = { Text(nivel) }
                )
            }
        }
        Text(
            text = Niveis.explicacao(estado.nivel),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        OutlinedTextField(
            value = estado.descricao,
            onValueChange = { viewModel.onDescricaoChange(it) },
            label = { Text("Descrição (opcional)") },
            minLines = 3,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                viewModel.salvar {
                    val texto = if (estado.editando) "Relato atualizado!" else "Relato registrado!"
                    Toast.makeText(context, texto, Toast.LENGTH_SHORT).show()
                    onVoltar()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (estado.editando) "Salvar alterações" else "Registrar relato")
        }

        OutlinedButton(
            onClick = onVoltar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancelar")
        }
    }
}
