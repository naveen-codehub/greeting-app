package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.network.JsonPlaceholderService
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch

class TodoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TodoFetcherScreen()
                }
            }
        }
    }
}

@Composable
fun TodoFetcherScreen() {
    var inputId by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var todoText by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Fetch TODO from JSONPlaceholder",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = inputId,
            onValueChange = { inputId = it },
            label = { Text("Enter TODO ID (e.g. 2)") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val id = inputId.toIntOrNull()
                if (id == null) {
                    errorMessage = "Please enter a valid number."
                    todoText = null
                    return@Button
                }

                scope.launch {
                    try {
                        isLoading = true
                        errorMessage = null
                        todoText = null

                        val todo = JsonPlaceholderService.api.getTodo(id)
                        todoText = """
                            ID: ${todo.id}
                            User ID: ${todo.userId}
                            Title: ${todo.title}
                            Completed: ${todo.completed}
                        """.trimIndent()
                    } catch (e: Exception) {
                        val message = e.localizedMessage ?: "Unknown error"
                        errorMessage = "Failed to fetch TODO: $message"
                    } finally {
                        isLoading = false
                    }
                }
            },
            enabled = !isLoading
        ) {
            Text(text = if (isLoading) "Fetching..." else "Fetch")
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (isLoading) {
            CircularProgressIndicator()
        }

        errorMessage?.let { msg ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = msg,
                color = MaterialTheme.colorScheme.error
            )
        }

        todoText?.let { text ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = text)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoFetcherPreview() {
    MyApplicationTheme {
        Surface {
            TodoFetcherScreen()
        }
    }
}

