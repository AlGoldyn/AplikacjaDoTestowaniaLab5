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
fun AddPersonScreen(
    db: PersonDatabase,
    onBack: () -> Unit
) {
    val scope = rememberCoroutineScope()

    // Dane wpisywane przez użytkownika
    var first by remember { mutableStateOf("") }
    var last by remember { mutableStateOf("") }
    var birth by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    // Zmienna przechowująca błędy walidacji
    var firstError by remember { mutableStateOf<String?>(null) }
    var lastError by remember { mutableStateOf<String?>(null) }
    var phoneError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var birthError by remember { mutableStateOf<String?>(null) }

    fun validate(): Boolean {
        var ok = true

        // IMIĘ
        firstError = if (first.isBlank()) {
            ok = false
            "Imię nie może być puste"
        } else null

        // NAZWISKO
        lastError = if (last.isBlank()) {
            ok = false
            "Nazwisko nie może być puste"
        } else null

        // TELEFON = dokładnie 9 cyfr
        phoneError = if (!phone.matches(Regex("^\\d{9}\$"))) {
            ok = false
            "Telefon musi mieć dokładnie 9 cyfr"
        } else null

        // EMAIL = musi mieć @ i rozszerzenie
        emailError = if (!email.matches(Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$"))) {
            ok = false
            "Niepoprawny adres e-mail"
        } else null

        // DATA URODZENIA = DD.MM.YYYY
        birthError = if (!birth.matches(Regex("^\\d{2}\\.\\d{2}\\.\\d{4}\$"))) {
            ok = false
            "Data musi być w formacie DD.MM.RRRR"
        } else null

        return ok
    }

    Column {
        TopAppBar(
            title = { Text("Powrót") },
            navigationIcon = {
                IconButton(onClick = { onBack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Wróć")
                }
            }
        )

        Column(Modifier.padding(20.dp)) {

            // IMIĘ
            OutlinedTextField(
                value = first,
                onValueChange = { first = it },
                label = { Text("Imię") },
                isError = firstError != null,
                supportingText = { firstError?.let { Text(it, color = MaterialTheme.colorScheme.error) } }
            )

            // NAZWISKO
            OutlinedTextField(
                value = last,
                onValueChange = { last = it },
                label = { Text("Nazwisko") },
                isError = lastError != null,
                supportingText = { lastError?.let { Text(it, color = MaterialTheme.colorScheme.error) } }
            )

            // DATA URODZENIA
            OutlinedTextField(
                value = birth,
                onValueChange = { birth = it },
                label = { Text("Data urodzenia (DD.MM.RRRR)") },
                isError = birthError != null,
                supportingText = { birthError?.let { Text(it, color = MaterialTheme.colorScheme.error) } }
            )

            // TELEFON
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Telefon") },
                isError = phoneError != null,
                supportingText = { phoneError?.let { Text(it, color = MaterialTheme.colorScheme.error) } }
            )

            // EMAIL
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("E-mail") },
                isError = emailError != null,
                supportingText = { emailError?.let { Text(it, color = MaterialTheme.colorScheme.error) } }
            )

            // ADRES — opcjonalny
            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Adres") }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    if (validate()) {
                        scope.launch {
                            db.personDao().insert(
                                Person(
                                    firstName = first,
                                    lastName = last,
                                    birthDate = birth,
                                    phone = phone,
                                    email = email,
                                    address = address
                                )
                            )
                            onBack()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Zapisz")
            }
        }
    }
}
