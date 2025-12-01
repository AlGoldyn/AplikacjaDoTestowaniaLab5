package com.example.mylab5.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeletePersonScreen(
    db: PersonDatabase,
    refreshTrigger: MutableState<Boolean>,
    onBack: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var id by remember { mutableStateOf("") }

    var showErrorDialog by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var deletedPersonName by remember { mutableStateOf("") }

    Column {
        TopAppBar(
            title = { Text("Powrót") },
            navigationIcon = {
                IconButton(onClick = { onBack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Wróć do home")
                }
            }
        )

        Column(Modifier.padding(20.dp)) {
            OutlinedTextField(
                value = id,
                onValueChange = { id = it },
                label = { Text("ID do usunięcia") },
                singleLine = true
            )

            Spacer(Modifier.height(20.dp))

            Button(onClick = {
                val personId = id.toIntOrNull()
                if (personId == null) {
                    showErrorDialog = true
                    return@Button
                }

                scope.launch {
                    try {
                        val persons = db.personDao().getAll()
                        val personToDelete = persons.find { it.id == personId }

                        if (personToDelete == null) {
                            showErrorDialog = true
                        } else {
                            db.personDao().deleteById(personId)
                            deletedPersonName = "${personToDelete.firstName} ${personToDelete.lastName}"
                            showSuccessDialog = true
                            refreshTrigger.value = !refreshTrigger.value
                            id = ""
                        }
                    } catch (e: Exception) {
                        showErrorDialog = true
                    }
                }
            }) {
                Text("Usuń")
            }
        }

        if (showErrorDialog) {
            AlertDialog(
                onDismissRequest = { showErrorDialog = false },
                title = { Text("Błąd") },
                text = { Text("Nie istnieje takie ID lub wartość jest nieprawidłowa") },
                confirmButton = {
                    TextButton(onClick = { showErrorDialog = false }) {
                        Text("OK")
                    }
                }
            )
        }

        if (showSuccessDialog) {
            AlertDialog(
                onDismissRequest = { showSuccessDialog = false },
                title = { Text("Usunięto użytkownika") },
                text = { Text("Usunięto użytkownika $deletedPersonName") },
                confirmButton = {
                    TextButton(onClick = { showSuccessDialog = false }) {
                        Text("OK")
                    }
                }
            )
        }
    }
}
