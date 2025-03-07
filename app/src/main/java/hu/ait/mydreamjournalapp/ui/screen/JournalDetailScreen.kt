package hu.ait.mydreamjournalapp.ui.screen

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import hu.ait.mydreamjournalapp.dream.GeminiScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.ait.mydreamjournalapp.R
import hu.ait.mydreamjournalapp.ui.theme.PastelLavender
import hu.ait.mydreamjournalapp.ui.theme.PastelMint
import hu.ait.mydreamjournalapp.ui.theme.PastelPeach
import hu.ait.mydreamjournalapp.ui.theme.PastelPink
import hu.ait.mydreamjournalapp.ui.theme.PastelPurple

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalDetailScreen(
    journalId: Int,
    viewModel: JournalDetailViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val journal by viewModel.journalState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    var isEditDialogOpen by remember { mutableStateOf(false) }
    var newJournalName by remember { mutableStateOf(journal?.name ?: "") }

    LaunchedEffect(journal?.journalContent) {
        journal?.journalContent?.takeIf { it.isNotBlank() }?.let {
            Log.d("JournalDetailScreen", "Analyzing dream: $it")
        }
    }

    LaunchedEffect(journalId) {
        viewModel.loadJournal(journalId)
    }

    val colorOptions = listOf(
        PastelPurple,
        PastelPink,
        PastelLavender,
        PastelPeach,
        PastelMint
    )

    val currentBackgroundColor = colorOptions.getOrNull(journal?.coverColor ?: 0) ?: PastelLavender

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(journal?.name ?: "Journal Details")
                        journal?.createdAt?.let { createdAt ->
                            Text(
                                text = "Created: ${formatDate(createdAt)}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = currentBackgroundColor,
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { isEditDialogOpen = true }) {
                        Icon(Icons.Filled.Edit, contentDescription = "Edit Journal Name")
                    }
                    IconButton(onClick = {
                        journal?.let {
                            viewModel.deleteJournal(it)

                            navController.popBackStack()
                        }
                    }) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete Journal")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(currentBackgroundColor)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else if (journal != null) {
                var journalContent by remember { mutableStateOf(journal?.journalContent ?: "") }
                var isContentMinimized by remember { mutableStateOf(false) }

                val rotationAngle by animateFloatAsState(
                    targetValue = if (isContentMinimized) 0f else 180f,
                    animationSpec = tween(durationMillis = 300)
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { isContentMinimized = !isContentMinimized }
                            .background(Color.White, shape = RoundedCornerShape(16.dp))
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.journal_content),
                            modifier = Modifier.weight(1f)
                        )

                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Toggle Content Visibility",
                            modifier = Modifier.rotate(rotationAngle)
                        )
                    }

                    if (!isContentMinimized) {
                        OutlinedTextField(
                            value = journalContent,
                            onValueChange = { newContent ->
                                journalContent = newContent
                                journal?.let { viewModel.updateJournal(it.copy(journalContent = newContent)) }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 200.dp)
                                .padding(top = 16.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                containerColor = Color.White
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        item {
                            GeminiScreen(
                                viewModel = viewModel(),
                                journalContent = journalContent
                            )
                        }
                    }
                }

            } else if (errorMessage.isNotEmpty()) {
                AlertDialog(
                    onDismissRequest = { },
                    title = { Text("Error") },
                    text = { Text(errorMessage) },
                    confirmButton = {
                        TextButton(onClick = { }) {
                            Text("OK")
                        }
                    }
                )
            } else {
                Text(text = "No journal found.")
            }
        }
        if (isEditDialogOpen) {
            var selectedColor by remember { mutableStateOf(0) }

            val initialColorIndex = journal?.coverColor ?: 0
            selectedColor = initialColorIndex

            AlertDialog(
                onDismissRequest = { isEditDialogOpen = false },
                title = { Text(stringResource(R.string.edit_journal_name)) },
                text = {
                    Column {
                        OutlinedTextField(
                            value = newJournalName,
                            onValueChange = { newJournalName = it },
                            label = { Text(stringResource(R.string.journal_name)) },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(stringResource(R.string.select_a_background_color), style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.height(16.dp))
                        Column {
                            val halfSize = (colorOptions.size + 1) / 2
                            val firstRowColors = colorOptions.take(halfSize)
                            val secondRowColors = colorOptions.drop(halfSize)

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                firstRowColors.forEachIndexed { index, color ->
                                    Box(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .background(color)
                                            .border(
                                                width = 2.dp,
                                                color = if (selectedColor == index) Color.Black else Color.Transparent
                                            )
                                            .clickable { selectedColor = index }
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                secondRowColors.forEachIndexed { index, color ->
                                    Box(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .background(color)
                                            .border(
                                                width = 2.dp,
                                                color = if (selectedColor == index + halfSize) Color.Black else Color.Transparent
                                            )
                                            .clickable { selectedColor = index + halfSize }
                                    )
                                }
                            }
                        }

                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        journal?.let {
                            val updatedJournalName = if (newJournalName.isEmpty()) it.name else newJournalName
                            viewModel.updateJournal(it.copy(name = updatedJournalName, coverColor = selectedColor))
                        }
                        isEditDialogOpen = false
                    }) {
                        Text(stringResource(R.string.save))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { isEditDialogOpen = false }) {
                        Text(stringResource(R.string.cancel))
                    }
                }
            )
        }

    }

}}

fun formatDate(timestamp: Long): String {
    val formatter = java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault())
    return formatter.format(java.util.Date(timestamp))
}
