package hu.ait.mydreamjournalapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import hu.ait.mydreamjournalapp.ui.screen.JournalDetailScreen
import hu.ait.mydreamjournalapp.ui.screen.JournalDetailViewModel
import hu.ait.mydreamjournalapp.ui.screen.JournalViewModel
import hu.ait.mydreamjournalapp.ui.screen.HomeScreen
import hu.ait.mydreamjournalapp.ui.theme.MyDreamJournalAppTheme
import hu.ait.mydreamjournalapp.ui.theme.PastelLavender
import hu.ait.mydreamjournalapp.ui.theme.PastelPurple
import kotlinx.coroutines.delay
import kotlin.random.Random

val CoolFont = FontFamily(Font(R.font.caviar_dreams))

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyDreamJournalAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    TodoAppNavHost(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun TodoAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "splash"
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable("splash") { SplashScreen(navController) }
        composable("todolist") { HomeScreen(navController = navController) }
        composable(
            route = "folderDetail/{folderId}",
            arguments = listOf(navArgument("folderId") { type = NavType.IntType })
        ) { backStackEntry ->
            val journalId = backStackEntry.arguments?.getInt("folderId")
            if (journalId != null) {
                JournalDetailHandler(journalId, navController)
            } else {
                Text("Invalid journal ID", modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun JournalDetailHandler(journalId: Int, navController: NavHostController) {
    val journalViewModel: JournalViewModel = hiltViewModel()
    val journalState by journalViewModel.journalState.collectAsState()
    val viewModel: JournalDetailViewModel = hiltViewModel()

    LaunchedEffect(journalId) {
        journalViewModel.getJournalById(journalId)
    }

    if (journalState != null) {
        JournalDetailScreen(
            journalId = journalState!!.id,
            viewModel = viewModel,
            navController = navController
        )
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun SplashScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PastelPurple),
        contentAlignment = Alignment.Center
    ) {
        DrawStars(10)

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(id = R.drawable.diary),
                contentDescription = "Diary Image",
                modifier = Modifier
                    .size(200.dp)
                    .padding(16.dp)
            )
            Text(
                text = "Dream Journal",
                style = androidx.compose.ui.text.TextStyle(
                    fontFamily = CoolFont,
                    fontSize = 36.sp,
                    textAlign = TextAlign.Center,
                    color = androidx.compose.ui.graphics.Color.Black
                )
            )
            Spacer(modifier = Modifier.weight(1f))

        }

        LaunchedEffect(Unit) {
            delay(3000)
            navController.navigate("todolist") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }
}

@Composable
fun DrawStars(count: Int) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        for (i in 0 until count) {
            val x = Random.nextFloat() * size.width
            val y = Random.nextFloat() * size.height
            drawStar(Offset(x, y), size = 75f)
        }
    }
}

fun DrawScope.drawStar(offset: Offset, size: Float) {

    drawPath(
        path = starPath(offset, size),
        color = PastelLavender
    )}

private fun starPath(offset: Offset, size: Float): Path {
    val path = Path()

    val points = 5

    val angle = 2 * Math.PI / points

    val outerRadius = size
    val innerRadius = size * 0.4f

    var currentAngle = -Math.PI / 2

    for (i in 0 until points) {
        val outerX = offset.x + (outerRadius * Math.cos(currentAngle)).toFloat()
        val outerY = offset.y + (outerRadius * Math.sin(currentAngle)).toFloat()
        if (i == 0) {
            path.moveTo(outerX, outerY)
        } else {
            path.lineTo(outerX, outerY)
        }

        currentAngle += angle / 2
        val innerX = offset.x + (innerRadius * Math.cos(currentAngle)).toFloat()
        val innerY = offset.y + (innerRadius * Math.sin(currentAngle)).toFloat()
        path.lineTo(innerX, innerY)

        currentAngle += angle / 2
    }

    path.close()
    return path
}
