package com.example.mylab5.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListPersonScreen(
    db: PersonDatabase,
    refreshTrigger: MutableState<Boolean>,
    onBack: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var persons by remember { mutableStateOf(emptyList<Person>()) }

    LaunchedEffect(refreshTrigger.value) {
        scope.launch {
            persons = db.personDao().getAll()
        }
    }

    Column {
        TopAppBar(
            title = { Text("Powrót") },
            navigationIcon = {
                IconButton(onClick = { onBack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Wróć do home")
                }
            }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            itemsIndexed(persons) { index, person ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "${index + 1}. ${person.firstName} ${person.lastName} (ID: ${person.id})",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Data urodzenia: ${person.birthDate}")
                        Text("Telefon: ${person.phone}")
                        Text("E-mail: ${person.email}")
                        Text("Adres: ${person.address}")
                    }
                }
            }
        }
    }
}
