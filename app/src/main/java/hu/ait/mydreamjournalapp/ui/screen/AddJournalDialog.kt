package hu.ait.mydreamjournalapp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import hu.ait.mydreamjournalapp.R
import hu.ait.mydreamjournalapp.data.Journal
import hu.ait.mydreamjournalapp.ui.theme.PastelPurple

@Composable
fun AddJournalDialog(
    journal: Journal?,
    onAddEditJournal: (Journal) -> Unit,
    onCancel: () -> Unit
) {
    var journalName by remember { mutableStateOf(journal?.name ?: "") }

    AlertDialog(
        onDismissRequest = { onCancel() },
        title = {
            Text(text = if (journal == null) stringResource(R.string.add_journal) else stringResource(
                R.string.edit_journal
            )
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = journalName,
                    onValueChange = { journalName = it },
                    label = { Text(stringResource(R.string.journal_name_in_add)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (journalName.isNotBlank()) {
                        val newJournal = journal?.copy(name = journalName) ?: Journal(name = journalName, journalContent = "", createdAt = System.currentTimeMillis())
                        onAddEditJournal(newJournal)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = PastelPurple,
                    contentColor = Color.Black
                ),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(stringResource(R.string.save))
            }
        },
        dismissButton = {
            Button(
                onClick = { onCancel() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = PastelPurple,
                    contentColor = Color.Black
                ),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(stringResource(R.string.cancel))
            }
        }

    )
}
