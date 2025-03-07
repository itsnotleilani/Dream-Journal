package hu.ait.mydreamjournalapp.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import hu.ait.mydreamjournalapp.data.Journal
import hu.ait.mydreamjournalapp.ui.theme.PastelLavender
import hu.ait.mydreamjournalapp.ui.theme.PastelMint
import hu.ait.mydreamjournalapp.ui.theme.PastelPeach
import hu.ait.mydreamjournalapp.ui.theme.PastelPink
import hu.ait.mydreamjournalapp.ui.theme.PastelPurple

@Composable
fun JournalCard(
    journal: Journal,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
    onNavigate: () -> Unit,
//    coverColor: Color = PastelPurple // Default to a dreamy pastel color
) {
    val colorOptions = listOf(
        PastelPurple,
        PastelPink,
        PastelLavender,
        PastelPeach,
        PastelMint
    )

    val color = colorOptions[journal.coverColor]

//    Log.d("coverColor", "coverColor: $folder.coverColor")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(8.dp)
            .clickable { onNavigate() },  // Navigate to the folder details
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Vertically center the content

        ) {
            Text(journal.name, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
//            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//                IconButton(onClick = onEdit) {
//                    Icon(Icons.Filled.Edit, contentDescription = "Edit")
//                }
//                IconButton(onClick = onDelete) {
//                    Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = Color.Red)
//                }
//            }
        }
    }
}
