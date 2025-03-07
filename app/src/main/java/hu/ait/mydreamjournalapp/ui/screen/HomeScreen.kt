package hu.ait.mydreamjournalapp.ui.screen

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import hu.ait.mydreamjournalapp.CoolFont
import hu.ait.mydreamjournalapp.R
import hu.ait.mydreamjournalapp.data.Journal
import hu.ait.mydreamjournalapp.ui.theme.PastelPurple
import kotlin.math.PI
import kotlin.math.sin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {
    val journals by viewModel.journals.collectAsState(initial = emptyList())
    var showAddDialog by remember { mutableStateOf(false) }
    var editJournal by remember { mutableStateOf<Journal?>(null) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Dream Journal",
                        style = androidx.compose.ui.text.TextStyle(
                            fontFamily = CoolFont,
                            fontSize = 36.sp,
                            textAlign = TextAlign.Left,
                            color = androidx.compose.ui.graphics.Color.Black
                        )
                    ) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PastelPurple,
                    titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ),

            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.onPrimary,
                contentColor = Color.Black,
                modifier = Modifier.size(100.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Journal", modifier = Modifier.size(50.dp))
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
            if (journals.isEmpty()) {
                Text(
                    text = stringResource(R.string.no_journals_available_start_by_adding_a_new_journal),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .padding(bottom = 200.dp),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(journals) { journal ->
                        JournalCard(
                            journal = journal,
                            onDelete = { viewModel.deleteJournal(journal) },
                            onEdit = { editJournal = journal },
                            onNavigate = { journal.id.let { navController.navigate("folderDetail/$it") } }
                        )
                    }
                }
            }
        }

        if (showAddDialog || editJournal != null) {
            AddJournalDialog(
                journal = editJournal,
                onAddEditJournal = { journal ->
                    Log.d("HomeScreen", "Journal being saved: ${journal.name}, ID: ${journal.id}")

                    if (editJournal == null) {
                        viewModel.addJournal(journal)
                    } else {
                        viewModel.updateJournal(journal.copy(id = editJournal!!.id))
                    }
                    showAddDialog = false
                    editJournal = null
                },
                onCancel = {
                    showAddDialog = false
                    editJournal = null
                }
            )
        }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {

                Spacer(modifier = Modifier.weight(1f))

                WaveShape(modifier = Modifier.fillMaxWidth())
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(175.dp)
                        .background(PastelPurple)
                )
            }
        }
}}

@Composable
fun WaveShape(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height

        val waveHeight = 50f
        val waveLength = 200f

        val path = Path().apply {
            moveTo(0f, height - waveHeight)
            for (x in 0..width.toInt() step 20) {
                val y = waveHeight * sin(PI * x / waveLength.toDouble()).toFloat()
                lineTo(x.toFloat(), height - waveHeight + y)
            }
            lineTo(width, height)
            lineTo(0f, height)
            close()
        }

        drawPath(path, color = PastelPurple)
    }
}

