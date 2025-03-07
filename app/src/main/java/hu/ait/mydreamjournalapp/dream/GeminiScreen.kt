package hu.ait.mydreamjournalapp.dream

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun GeminiScreen(
    viewModel: GeminiViewModel = viewModel(),
    journalContent: String
) {
    val textResult =
        viewModel.textGenerationResult.collectAsState().value
    val showTextResult = remember { mutableStateOf(false) }
    val isLoading = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = CenterHorizontally
    ) {
        Button(onClick = {
            viewModel.generateStory(prompt = "Analyze this dream: $journalContent")
            isLoading.value = true
            showTextResult.value = true
            coroutineScope.launch {
                delay(3000)
                isLoading.value = false
                showTextResult.value = true
            }
                         },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
                    shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = "Analyze Dream")
        }

        Text(
            text = textResult ?: "No Analysis Generated Yet",
            modifier = Modifier.padding(top = 16.dp)
        )

        DisposableEffect(Unit) {
            onDispose {
                showTextResult.value = false
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

    }
}
