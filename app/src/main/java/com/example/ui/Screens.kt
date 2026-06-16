package com.example.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.LanguageViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
    viewModel: LanguageViewModel,
    modifier: Modifier = Modifier
) {
    var currentScreen by remember { mutableStateOf("home") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (currentScreen) {
                            "home" -> "Deutsch Writer"
                            "vocab" -> "Vocabulary Practice"
                            "sentence" -> "Sentence Practice"
                            "grammar" -> "Grammar Practice"
                            "challenge" -> "Daily Challenge"
                            "progress" -> "Progress Summary"
                            "manager" -> "Content Manager"
                            "search" -> "Search & Bookmarks"
                            "mistakes" -> "Mistake Review"
                            "playlist_lessons" -> "A1 Playlists Study Logs"
                            "tutor" -> "DeutschMeister Tutor"
                            else -> "Deutsch Writer"
                        },
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                navigationIcon = {
                    if (currentScreen != "home") {
                        IconButton(onClick = { 
                            currentScreen = "home"
                            viewModel.resetIndices()
                        }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                },
                actions = {
                    if (currentScreen == "home") {
                        IconButton(onClick = { currentScreen = "search" }) {
                            Icon(Icons.Default.Search, contentDescription = "Search & Favorites")
                        }
                        IconButton(onClick = { currentScreen = "mistakes" }) {
                            Icon(Icons.Default.Warning, contentDescription = "Review Mistakes")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            when (currentScreen) {
                "home" -> HomeScreen(
                    viewModel = viewModel,
                    onNavigate = { screen -> currentScreen = screen }
                )
                "vocab" -> VocabularyScreen(viewModel = viewModel)
                "sentence" -> SentenceScreen(viewModel = viewModel)
                "grammar" -> GrammarScreen(viewModel = viewModel)
                "challenge" -> ChallengeScreen(viewModel = viewModel)
                "progress" -> ProgressScreen(viewModel = viewModel)
                "manager" -> ManagerScreen(viewModel = viewModel)
                "search" -> SearchFavoritesScreen(viewModel = viewModel)
                "mistakes" -> MistakesScreen(viewModel = viewModel)
                "smart_revision" -> SmartRevisionScreen(viewModel = viewModel)
                "playlist_lessons" -> PlaylistLessonsScreen(
                    viewModel = viewModel,
                    onBack = { currentScreen = "home" }
                )
                "tutor" -> DeutschMeisterScreen(viewModel = viewModel)
            }
        }
    }
}

// ==================== HOME HUB SCREEN ====================
@Composable
fun HomeScreen(
    viewModel: LanguageViewModel,
    onNavigate: (String) -> Unit
) {
    val profile by viewModel.profile.collectAsStateWithLifecycle()
    val mistakes by viewModel.mistakes.collectAsStateWithLifecycle()
    val categories by viewModel.categories.collectAsStateWithLifecycle()
    val dueCount by viewModel.dueRevisionsCount.collectAsStateWithLifecycle()
    val practiceLimit by viewModel.practiceLimit.collectAsStateWithLifecycle()
    val practiceRandomize by viewModel.practiceRandomize.collectAsStateWithLifecycle()

    var levelFilter by remember { mutableStateOf("All") }
    var selectedCategory by remember { mutableStateOf("All") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // --- SCORE & STATS HEADER ---
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "স্বাগতম! (Welcome!)",
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 14.sp
                            )
                            Text(
                                text = "German Writer Hub",
                                color = Color.White,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        
                        // Learning Level Tag
                        Surface(
                            color = Color.White.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = profile.learningLevel,
                                color = Color.White,
                                fontWeight = FontWeight.Black,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        StatItem(
                            icon = Icons.Default.Bolt,
                            value = "${profile.xpPoints} XP",
                            label = "Leveled Score",
                            tint = Color(0xFFFFD700)
                        )
                        StatItem(
                            icon = Icons.Default.LocalFireDepartment,
                            value = "${profile.dailyStreak} Days",
                            label = "Streak Count",
                            tint = Color(0xFFFF5722)
                        )
                        StatItem(
                            icon = Icons.Default.CheckCircle,
                            value = String.format("%.0f%%", profile.accuracyPercentage),
                            label = "Accuracy",
                            tint = Color(0xFF4CAF50)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Progress linear bar to next level
                    val xpLimit = valXpLimit(profile.learningLevel)
                    val progressRatio = (profile.xpPoints.toFloat() / xpLimit.toFloat()).coerceIn(0f, 1f)
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Next milestone progress",
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 12.sp
                            )
                            Text(
                                text = "${profile.xpPoints} / $xpLimit XP",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        LinearProgressIndicator(
                            progress = { progressRatio },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(CircleShape),
                            color = Color.White,
                            trackColor = Color.White.copy(alpha = 0.3f)
                        )
                    }
                }
            }
        }

        // --- FILTERS BAR ---
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
                    .padding(14.dp)
            ) {
                Text(
                    text = "Learning Level & Category Configuration",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(10.dp))

                // Difficulty Filters Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val levels = listOf("All", "A1", "A2", "B1")
                    levels.forEach { lvl ->
                        val isSelected = levelFilter == lvl
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background)
                                .clickable {
                                    levelFilter = lvl
                                    viewModel.setLevelFilter(lvl)
                                }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = lvl,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Category Filter selection dropdown / pills
                Text(
                    text = "Active Category Domain",
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(6.dp))

                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val items = listOf("All") + categories.map { it.name }.distinct().take(3)
                        items.forEach { cat ->
                            val isSelected = selectedCategory == cat
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(30.dp))
                                    .background(if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f) else MaterialTheme.colorScheme.background)
                                    .clickable {
                                        selectedCategory = cat
                                        viewModel.setCategoryFilter(cat)
                                    }
                                    .padding(horizontal = 14.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = cat,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Practice Mode Settings
                Text(
                    text = "Practice Setup: Limit & Order",
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Predefined items
                    val limits = listOf(0, 5, 10, 20)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        limits.forEach { limit ->
                            val isSelected = practiceLimit == limit
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background)
                                    .clickable { viewModel.setPracticeConfig(limit, practiceRandomize) }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = if (limit == 0) "All" else "$limit",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                    
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Random Mode", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(8.dp))
                        Switch(
                            checked = practiceRandomize,
                            onCheckedChange = { viewModel.setPracticeConfig(practiceLimit, it) },
                            modifier = Modifier.scale(0.8f)
                        )
                    }
                }
            }
        }

        // --- MISTAKES SHORTCUT ALERTER ---
        if (mistakes.isNotEmpty()) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(ErrorRed.copy(alpha = 0.1f))
                        .clickable { onNavigate("mistakes") }
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.ErrorOutline, contentDescription = "Mistakes", tint = ErrorRed)
                        Column {
                            Text(
                                text = "Review Mistakes (${mistakes.size})",
                                fontWeight = FontWeight.Bold,
                                color = ErrorRed,
                                fontSize = 14.sp
                            )
                            Text(
                                text = "Perfect your misspelled German translations!",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                        }
                    }
                    Icon(Icons.Default.ArrowForward, contentDescription = "Go", tint = ErrorRed)
                }
            }
        }

        // --- DUE REVISIONS SHORTCUT ---
        if (dueCount > 0) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(SuccessGreen.copy(alpha = 0.1f))
                        .clickable { onNavigate("smart_revision") }
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.AccessTime, contentDescription = "Due Reviews", tint = SuccessGreen)
                        Column {
                            Text(
                                text = "Smart Revisions Due ($dueCount)",
                                fontWeight = FontWeight.Bold,
                                color = SuccessGreen,
                                fontSize = 14.sp
                            )
                            Text(
                                text = "Items ready for spaced repetition review!",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                        }
                    }
                    Icon(Icons.Default.ArrowForward, contentDescription = "Go", tint = SuccessGreen)
                }
            }
        }

        // --- FEATURED PLAYLIST SECTION ---
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigate("playlist_lessons") }
                    .testTag("featured_playlist_card"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.PlayArrow,
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = "Play master logs",
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "A1 Playlists Study Logs",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = "13 Videos",
                                    color = Color.White,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "ভিডিও লেকচার স্টাডি লগস ও প্র্যাকটিস সেশন",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Explore lecture transcripts, key grammar notes, and perform interactive sentence-building quizzes.",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.75f)
                        )
                    }
                    Icon(
                        Icons.Default.ArrowForward,
                        contentDescription = "Open Playlists",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigate("tutor") },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(MaterialTheme.colorScheme.secondary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("🤖", fontSize = 24.sp)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "DeutschMeister / AI Tutor",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Text(
                            text = "আপনার স্মার্ট জার্মান ভাষা শেখার সঙ্গী। Chat with the German Tutor App AI.",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f),
                            lineHeight = 14.sp
                        )
                    }
                    Icon(
                        Icons.Default.ArrowForward,
                        contentDescription = "Open Tutor",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }

        // --- DASHBOARD TILES GRID ---
        item {
            Text(
                text = "Training Areas",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
            )
        }

        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    DashboardTile(
                        title = "Vocabulary",
                        bengali = "শব্দকোষ চর্চা",
                        icon = Icons.Default.MenuBook,
                        description = "Translate words",
                        color = Color(0xFF3B82F6),
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate("vocab") }
                    )
                    DashboardTile(
                        title = "Sentences",
                        bengali = "বাক্য অনুবাদ",
                        icon = Icons.Default.Translate,
                        description = "Full syntax practice",
                        color = Color(0xFF10B981),
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate("sentence") }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    DashboardTile(
                        title = "Grammar",
                        bengali = "ব্যাকরণ চর্চা",
                        icon = Icons.Default.Style,
                        description = "Conjugation & articles",
                        color = Color(0xFF8B5CF6),
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate("grammar") }
                    )
                    DashboardTile(
                        title = "Daily Challenge",
                        bengali = "দৈনিক লেখনী",
                        icon = Icons.Default.Create,
                        description = "Write 1-5 sentences",
                        color = Color(0xFFF59E0B),
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate("challenge") }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    DashboardTile(
                        title = "Progress Stats",
                        bengali = "অগ্রগতি রেখা",
                        icon = Icons.Default.BarChart,
                        description = "View streak & logs",
                        color = Color(0xFFEC4899),
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate("progress") }
                    )
                    DashboardTile(
                        title = "Manager",
                        bengali = "কন্টেন্ট ম্যানেজার",
                        icon = Icons.Default.Settings,
                        description = "Add & organize words",
                        color = Color(0xFF64748B),
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate("manager") }
                    )
                }
            }
        }
    }
}

private fun valXpLimit(level: String): Int {
    return when (level) {
        "A1" -> 150
        "A2" -> 400
        else -> 1000
    }
}

@Composable
fun StatItem(icon: androidx.compose.ui.graphics.vector.ImageVector, value: String, label: String, tint: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(icon, contentDescription = label, tint = tint, modifier = Modifier.size(28.dp))
        Spacer(modifier = Modifier.height(4.dp))
        Text(value, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text(label, color = Color.White.copy(alpha = 0.7f), fontSize = 10.sp)
    }
}

@Composable
fun DashboardTile(
    title: String,
    bengali: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    description: String,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .testTag("tile_${title.lowercase()}")
            .height(130.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(color.copy(alpha = 0.15f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
                }
                Icon(
                    Icons.Default.ArrowForward,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                    modifier = Modifier.size(16.dp)
                )
            }
            
            Column {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = bengali,
                    fontWeight = FontWeight.Medium,
                    fontSize = 11.sp,
                    color = color
                )
                Text(
                    text = description,
                    fontSize = 9.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


// ==================== VOCABULARY SCREEN ====================
@Composable
fun VocabularyScreen(viewModel: LanguageViewModel) {
    var setupDone by remember { mutableStateOf(false) }

    if (!setupDone) {
        PracticeSetupScreen(
            viewModel = viewModel,
            moduleType = "vocab",
            onStart = { setupDone = true }
        )
        return
    }

    val vocabularyList by viewModel.vocabularyList.collectAsStateWithLifecycle()
    val cursor by viewModel.currentVocabIndex.collectAsStateWithLifecycle()
    val textState by viewModel.userTranslationInput.collectAsStateWithLifecycle()
    val evaluation by viewModel.evaluationState.collectAsStateWithLifecycle()

    if (vocabularyList.isEmpty()) {
        EmptyStateView(
            msg = "কোন শব্দ নেই! অনুগ্রহ করে কনফিগারেশন পরিবর্তন করুন বা নতুন শব্দ যোগ করুন।",
            desc = "No words match your learning filter. Check filters or custom lists."
        )
        return
    }

    val activeItem = vocabularyList.getOrNull(cursor) ?: vocabularyList.first()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Tracker Header
        CardTrackerHeader(index = cursor, total = vocabularyList.size, level = activeItem.difficultyLevel)

        // Core Translation Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "বাংলা শব্দ:",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))
                
                Text(
                    text = activeItem.bengaliWord,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Category: ${activeItem.category}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Action Row (Speaking & Fave bookmark)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    IconButton(
                        onClick = { viewModel.speak(activeItem.germanTranslation) },
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), CircleShape)
                            .size(48.dp)
                    ) {
                        Icon(Icons.Default.VolumeUp, contentDescription = "Pronounce", tint = MaterialTheme.colorScheme.primary)
                    }

                    IconButton(
                        onClick = { viewModel.toggleVocabularyFavorite(activeItem) },
                        modifier = Modifier
                            .background(
                                if (activeItem.isFavorite) Color(0xFFFFD700).copy(alpha = 0.1f)
                                else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
                                CircleShape
                            )
                            .size(48.dp)
                    ) {
                        Icon(
                            imageVector = if (activeItem.isFavorite) Icons.Default.Star else Icons.Outlined.StarBorder,
                            contentDescription = "Bookmark",
                            tint = if (activeItem.isFavorite) Color(0xFFFF9800) else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    }
                }
            }
        }

        // EVALUATION BOX ALERTER
        EvaluationFeedback(
            evaluation = evaluation,
            expectedAnswer = activeItem.germanTranslation,
            onScheduleManual = { delay -> viewModel.scheduleRevision("vocab", activeItem.id, delay) },
            onScheduleAuto = { quality -> viewModel.scheduleRevision("vocab", activeItem.id, 0, quality) }
        )

        // TEXT ENTRY INPUT FIELD
        OutlinedTextField(
            value = textState,
            onValueChange = { viewModel.userTranslationInput.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("vocabulary_input")
                .heightIn(min = 64.dp),
            placeholder = { Text("German Translation (e.g. das Buch)") },
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
            ),
            singleLine = true
        )

        // FLOW ACTION BUTTONS
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    if (evaluation == null) {
                        viewModel.checkVocabularyAnswer(activeItem)
                    } else {
                        viewModel.nextVocabulary()
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .testTag("action_button")
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (evaluation == null) MaterialTheme.colorScheme.primary else SuccessGreen
                )
            ) {
                Text(
                    text = if (evaluation == null) "Check Translation" else "Translate Next ➔",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}


// ==================== SENTENCE SCREEN ====================
@Composable
fun SentenceScreen(viewModel: LanguageViewModel) {
    var setupDone by remember { mutableStateOf(false) }

    if (!setupDone) {
        PracticeSetupScreen(
            viewModel = viewModel,
            moduleType = "sentence",
            onStart = { setupDone = true }
        )
        return
    }

    val sentencesList by viewModel.sentenceList.collectAsStateWithLifecycle()
    val cursor by viewModel.currentSentenceIndex.collectAsStateWithLifecycle()
    val textState by viewModel.userTranslationInput.collectAsStateWithLifecycle()
    val evaluation by viewModel.evaluationState.collectAsStateWithLifecycle()

    if (sentencesList.isEmpty()) {
        EmptyStateView(
            msg = "কোন বাক্য পাওয়া যায়নি!",
            desc = "No sentences match your active level or domain. Change filter to view more."
        )
        return
    }

    val activeItem = sentencesList.getOrNull(cursor) ?: sentencesList.first()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CardTrackerHeader(index = cursor, total = sentencesList.size, level = activeItem.difficultyLevel)

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "বাংলা বাক্য:",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = activeItem.bengaliSentence,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    lineHeight = 32.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Category: ${activeItem.category}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    IconButton(
                        onClick = { viewModel.speak(activeItem.germanTranslation) },
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), CircleShape)
                            .size(48.dp)
                    ) {
                        Icon(Icons.Default.VolumeUp, contentDescription = "Listen", tint = MaterialTheme.colorScheme.primary)
                    }

                    IconButton(
                        onClick = { viewModel.toggleSentenceFavorite(activeItem) },
                        modifier = Modifier
                            .background(
                                if (activeItem.isFavorite) Color(0xFFFFD700).copy(alpha = 0.1f)
                                else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
                                CircleShape
                            )
                            .size(48.dp)
                    ) {
                        Icon(
                            imageVector = if (activeItem.isFavorite) Icons.Default.Star else Icons.Outlined.StarBorder,
                            contentDescription = "Bookmark",
                            tint = if (activeItem.isFavorite) Color(0xFFFF9800) else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    }
                }
            }
        }

        val aiFeedback by viewModel.aiFeedback.collectAsStateWithLifecycle()
        EvaluationFeedback(
            evaluation = evaluation,
            expectedAnswer = activeItem.germanTranslation,
            aiFeedback = aiFeedback,
            onScheduleManual = { delay -> viewModel.scheduleRevision("sentence", activeItem.id, delay) },
            onScheduleAuto = { quality -> viewModel.scheduleRevision("sentence", activeItem.id, 0, quality) }
        )

        OutlinedTextField(
            value = textState,
            onValueChange = { viewModel.userTranslationInput.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("sentence_input")
                .heightIn(min = 90.dp),
            placeholder = { Text("Write translated German sentence here...") },
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
            ),
            maxLines = 3
        )

        Button(
            onClick = {
                if (evaluation == null) {
                    viewModel.checkSentenceAnswer(activeItem)
                } else {
                    viewModel.nextSentence()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (evaluation == null) MaterialTheme.colorScheme.primary else SuccessGreen
            )
        ) {
            Text(
                text = if (evaluation == null) "Submit Translation" else "Next Sentence ➔",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}


// ==================== GRAMMAR PRACTICE SCREEN ====================
@Composable
fun GrammarScreen(viewModel: LanguageViewModel) {
    var setupDone by remember { mutableStateOf(false) }

    if (!setupDone) {
        PracticeSetupScreen(
            viewModel = viewModel,
            moduleType = "grammar",
            onStart = { setupDone = true }
        )
        return
    }

    val grammarList by viewModel.grammarList.collectAsStateWithLifecycle()
    val cursor by viewModel.currentGrammarIndex.collectAsStateWithLifecycle()
    val textState by viewModel.userTranslationInput.collectAsStateWithLifecycle()
    val evaluation by viewModel.evaluationState.collectAsStateWithLifecycle()

    if (grammarList.isEmpty()) {
        EmptyStateView(
            msg = "কোন ব্যাকরণ প্রশ্ন পাওয়া যায়নি!",
            desc = "No grammar questions in database yet matching selected flags."
        )
        return
    }

    val activeItem = grammarList.getOrNull(cursor) ?: grammarList.first()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CardTrackerHeader(index = cursor, total = grammarList.size, level = activeItem.difficultyLevel)

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Category: ${activeItem.category} 💡",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(14.dp))
                
                Text(
                    text = activeItem.question,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                )

                if (activeItem.explanation.isNotEmpty() && evaluation != null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.08f))
                            .padding(12.dp)
                    ) {
                        Text(
                            text = "Grammar Rule Explanation:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = activeItem.explanation,
                            fontSize = 11.sp,
                            lineHeight = 16.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    IconButton(
                        onClick = { viewModel.speak(activeItem.correctAnswer) },
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), CircleShape)
                            .size(48.dp)
                    ) {
                        Icon(Icons.Default.VolumeUp, contentDescription = "Listen", tint = MaterialTheme.colorScheme.primary)
                    }

                    IconButton(
                        onClick = { viewModel.toggleGrammarFavorite(activeItem) },
                        modifier = Modifier
                            .background(
                                if (activeItem.isFavorite) Color(0xFFFFD700).copy(alpha = 0.1f)
                                else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
                                CircleShape
                            )
                            .size(48.dp)
                    ) {
                        Icon(
                            imageVector = if (activeItem.isFavorite) Icons.Default.Star else Icons.Outlined.StarBorder,
                            contentDescription = "Bookmark",
                            tint = if (activeItem.isFavorite) Color(0xFFFF9800) else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    }
                }
            }
        }

        EvaluationFeedback(
            evaluation = evaluation,
            expectedAnswer = activeItem.correctAnswer,
            onScheduleManual = { delay -> viewModel.scheduleRevision("grammar", activeItem.id, delay) },
            onScheduleAuto = { quality -> viewModel.scheduleRevision("grammar", activeItem.id, 0, quality) }
        )

        OutlinedTextField(
            value = textState,
            onValueChange = { viewModel.userTranslationInput.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 64.dp),
            placeholder = { Text("Submit German answer cue...") },
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
            ),
            singleLine = true
        )

        Button(
            onClick = {
                if (evaluation == null) {
                    viewModel.checkGrammarAnswer(activeItem)
                } else {
                    viewModel.nextGrammar()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (evaluation == null) MaterialTheme.colorScheme.primary else SuccessGreen
            )
        ) {
            Text(
                text = if (evaluation == null) "Check Grammar" else "Next Rule ➔",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}


// ==================== DAILY WRITING CHALLENGE SCREEN ====================
@Composable
fun ChallengeScreen(viewModel: LanguageViewModel) {
    val challenges by viewModel.dailyChallenges.collectAsStateWithLifecycle()
    val responses by viewModel.savedResponses.collectAsStateWithLifecycle()

    var activeChallengeIndex by remember { mutableStateOf(0) }
    var responseDraft by remember { mutableStateOf("") }
    var userSavedMessage by remember { mutableStateOf<String?>(null) }

    if (challenges.isEmpty()) {
        EmptyStateView(
            msg = "কোন দৈনিক চ্যালেঞ্জ পাওয়া যায়নি!",
            desc = "Prepopulated daily challenges are missing. Check Content Manager."
        )
        return
    }

    val activeChallenge = challenges.getOrNull(activeChallengeIndex) ?: challenges.first()
    val activeChallengeResponses = responses.filter { it.challengeId == activeChallenge.id }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Progression Dots for Challenges
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Daily Challenge Pro",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    challenges.forEachIndexed { idx, _ ->
                        val isSelected = activeChallengeIndex == idx
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))
                                .clickable {
                                    activeChallengeIndex = idx
                                    responseDraft = ""
                                    userSavedMessage = null
                                }
                        )
                    }
                }
            }
        }

        // Prompt Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(Color(0xFFFFF7ED), CircleShape)
                                .size(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFF97316), modifier = Modifier.size(16.dp))
                        }
                        Text(
                            text = "Daily Prompt ${activeChallenge.dayNumber}",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    
                    Text(
                        text = activeChallenge.promptBengali,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 28.sp
                    )
                    
                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Vocabulary Helpers & Cues (শব্দার্থ ইঙ্গিত):",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    
                    Spacer(modifier = Modifier.height(6.dp))

                    val hints = activeChallenge.vocabularyHints.split(", ")
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        hints.forEach { hint ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.08f))
                                    .padding(horizontal = 10.dp, vertical = 5.dp)
                            ) {
                                Text(
                                    text = hint,
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }

        // Writing zone
        item {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Write your German journal entry (1-5 sentences):",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    modifier = Modifier.padding(bottom = 6.dp)
                )

                OutlinedTextField(
                    value = responseDraft,
                    onValueChange = { responseDraft = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    placeholder = { Text("z.B. Heute war ein wunderschöner Tag... (Minimum 1 German sentence)") },
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                    ),
                    maxLines = 10
                )
            }
        }

        item {
            Button(
                onClick = {
                    if (responseDraft.isNotBlank()) {
                        viewModel.submitDailyChallenge(activeChallenge.id, responseDraft)
                        userSavedMessage = "Response recorded successfully in local journal logs! +30 XP Granted."
                        responseDraft = ""
                    }
                },
                enabled = responseDraft.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Submit Entry to Logs",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }

        // Notification Alerts
        if (userSavedMessage != null) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(SuccessGreen.copy(alpha = 0.15f))
                        .padding(14.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = SuccessGreen)
                        Text(
                            text = userSavedMessage!!,
                            color = SuccessGreen,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // PAST LOG HISTORY Responses list
        if (activeChallengeResponses.isNotEmpty()) {
            item {
                Text(
                    text = "Your Logged Submissions (${activeChallengeResponses.size})",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }

            items(activeChallengeResponses) { log ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Date: ${log.dateString}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            IconButton(onClick = { viewModel.speak(log.userResponseGerman) }) {
                                Icon(Icons.Default.VolumeUp, contentDescription = "Pronounce Logged text", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = log.userResponseGerman,
                            lineHeight = 20.sp,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}

// FlowRow fallback helper for layout
@Composable
fun FlowRow(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    content: @Composable () -> Unit
) {
    // Standard Simple wrap implementations
    androidx.compose.foundation.layout.Row(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
}


// ==================== VISUAL PROGRESS STATISTICS SCREEN ====================
@Composable
fun ProgressScreen(viewModel: LanguageViewModel) {
    val profile by viewModel.profile.collectAsStateWithLifecycle()
    val responses by viewModel.savedResponses.collectAsStateWithLifecycle()
    val completedLessons by viewModel.completedLessons.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Progress Metrics & History",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        // Playlist Lesson Progress Ring or Bar
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(64.dp)) {
                        CircularProgressIndicator(
                            progress = { completedLessons.size.toFloat() / 13f },
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.primary,
                            trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                            strokeWidth = 6.dp
                        )
                        Text(
                            text = "${((completedLessons.size.toFloat() / 13f) * 100).toInt()}%",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Column {
                        Text(
                            text = "Course Completion",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "${completedLessons.size} of 13 Lecture Logs finished.",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }

        // Comprehensive Core Stat Board
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Current Learning Level Status: ${profile.learningLevel}",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 16.sp
                    )

                    Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ProfileStatDetail(label = "Total Finished Lessons", valStr = "${profile.completedExercises}")
                        ProfileStatDetail(label = "Correct Responses", valStr = "${profile.correctAnswers}")
                        ProfileStatDetail(label = "Score ACC", valStr = String.format("%.1f%%", profile.accuracyPercentage))
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ProfileStatDetail(label = "Total XP earned", valStr = "${profile.xpPoints}")
                        ProfileStatDetail(label = "Active Day Streak", valStr = "${profile.dailyStreak} 🔥")
                        ProfileStatDetail(label = "Last active stamp", valStr = profile.lastActiveDate.ifEmpty { "Today" })
                    }
                }
            }
        }

        // XP Breakdown Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Language level breakdown logic:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    LevelRow(lvlName = "A1 Breakthrough", xpRange = "0 - 149 XP", active = profile.learningLevel == "A1")
                    LevelRow(lvlName = "A2 Waystage", xpRange = "150 - 399 XP", active = profile.learningLevel == "A2")
                    LevelRow(lvlName = "B1 Threshold", xpRange = "400+ XP", active = profile.learningLevel == "B1")
                }
            }
        }

        // Saved Writing Journal Stats
        item {
            Text(
                text = "Writing Journals History",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        if (responses.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "কোন জার্নাল এন্ট্রি লিখিত নেই এখনো। দৈনিক চ্যালেঞ্জ পূর্ণ করুন!",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            items(responses) { entry ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Challenge ID: ${entry.challengeId}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = entry.dateString,
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = entry.userResponseGerman,
                            lineHeight = 18.sp,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileStatDetail(label: String, valStr: String) {
    Column {
        Text(text = label, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
        Text(text = valStr, fontSize = 16.sp, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.onSurface)
    }
}

@Composable
fun LevelRow(lvlName: String, xpRange: String, active: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = lvlName,
            fontSize = 12.sp,
            fontWeight = if (active) FontWeight.Bold else FontWeight.Normal,
            color = if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = if (active) "Active ✓ ($xpRange)" else xpRange,
            fontSize = 11.sp,
            color = if (active) SuccessGreen else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            fontWeight = if (active) FontWeight.Bold else FontWeight.Normal
        )
    }
}


// ==================== SEARCH & FAVORITES/BOOKMARKS SCREEN ====================
@Composable
fun SearchFavoritesScreen(viewModel: LanguageViewModel) {
    val searchKeyword by viewModel.searchKeyword.collectAsStateWithLifecycle()
    val vocabResults by viewModel.searchResultsVocabulary.collectAsStateWithLifecycle()
    val sentenceResults by viewModel.searchResultsSentences.collectAsStateWithLifecycle()

    var activeTab by remember { mutableStateOf("Search") } // "Search", "Bookmarks"

    // Fetch Bookmarked Items
    val vocabFavorites by viewModel.getFavoriteVocabulary().collectAsStateWithLifecycle(emptyList())
    val sentencesFavorites by viewModel.getFavoriteSentences().collectAsStateWithLifecycle(emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Tab Layout
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            listOf("Search", "Bookmarks ⭐").forEach { tab ->
                val isSelected = (tab.startsWith("Search") && activeTab == "Search") || (tab.startsWith("Book") && activeTab == "Bookmarks")
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { activeTab = if (tab.startsWith("Search")) "Search" else "Bookmarks" }
                        .padding(vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = tab,
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                    if (isSelected) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .width(60.dp)
                                .height(3.dp)
                                .background(PrimaryBlue, RoundedCornerShape(2.dp))
                        )
                    }
                }
            }
        }

        if (activeTab == "Search") {
            // Search Input Box
            OutlinedTextField(
                value = searchKeyword,
                onValueChange = { viewModel.searchKeyword.value = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("জার্মান বা বাংলা শব্দ দিয়ে সার্চ করুন...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface
                ),
                singleLine = true
            )

            if (searchKeyword.isBlank()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "টাইপ করা শুরু করুন...",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (vocabResults.isEmpty() && sentenceResults.isEmpty()) {
                        item {
                            Text(
                                "কোনো ফলাফল পাওয়া যায়নি।",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                        }
                    }

                    if (vocabResults.isNotEmpty()) {
                        item {
                            Text("Vocabulary Results", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                        }
                        items(vocabResults) { v ->
                            SearchResultRow(bengali = v.bengaliWord, german = v.germanTranslation, onSpeak = { viewModel.speak(v.germanTranslation) })
                        }
                    }

                    if (sentenceResults.isNotEmpty()) {
                        item {
                            Text("Sentence Results", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(top = 10.dp))
                        }
                        items(sentenceResults) { s ->
                            SearchResultRow(bengali = s.bengaliSentence, german = s.germanTranslation, onSpeak = { viewModel.speak(s.germanTranslation) })
                        }
                    }
                }
            }
        } else {
            // Bookmarks / Favorites View
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (vocabFavorites.isEmpty() && sentencesFavorites.isEmpty()) {
                    item {
                        Text(
                            "আপনার কোনো বুকমার্ক সংরক্ষণ করা নেই।",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp),
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    }
                }

                if (vocabFavorites.isNotEmpty()) {
                    item {
                        Text("Vocabulary Favorites ⭐", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                    }
                    items(vocabFavorites) { v ->
                        SearchResultRow(
                            bengali = v.bengaliWord,
                            german = v.germanTranslation,
                            onSpeak = { viewModel.speak(v.germanTranslation) },
                            onToggleFavorite = { viewModel.toggleVocabularyFavorite(v) }
                        )
                    }
                }

                if (sentencesFavorites.isNotEmpty()) {
                    item {
                        Text("Sentence Favorites ⭐", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(top = 10.dp))
                    }
                    items(sentencesFavorites) { s ->
                        SearchResultRow(
                            bengali = s.bengaliSentence,
                            german = s.germanTranslation,
                            onSpeak = { viewModel.speak(s.germanTranslation) },
                            onToggleFavorite = { viewModel.toggleSentenceFavorite(s) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SearchResultRow(
    bengali: String,
    german: String,
    onSpeak: () -> Unit,
    onToggleFavorite: (() -> Unit)? = null
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = bengali, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.SemiBold)
                Text(text = german, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            }
            Row {
                IconButton(onClick = onSpeak) {
                    Icon(Icons.Default.VolumeUp, contentDescription = "Speak translation", tint = MaterialTheme.colorScheme.primary)
                }
                if (onToggleFavorite != null) {
                    IconButton(onClick = onToggleFavorite) {
                        Icon(Icons.Default.Delete, contentDescription = "Remove bookmark", tint = ErrorRed)
                    }
                }
            }
        }
    }
}


// ==================== CONTENT MANAGER SCREEN ====================
@Composable
fun ManagerScreen(viewModel: LanguageViewModel) {
    val categories by viewModel.categories.collectAsStateWithLifecycle()
    val vocabList by viewModel.vocabularyList.collectAsStateWithLifecycle()
    val sentencesList by viewModel.sentenceList.collectAsStateWithLifecycle()
    val grammarList by viewModel.grammarList.collectAsStateWithLifecycle()

    var activeManagerTab by remember { mutableStateOf("Vocab") } // "Vocab", "Sentence", "Grammar", "Categories"

    // Form fields input states
    var selectCategory by remember { mutableStateOf("") }
    var inputBengaliWord by remember { mutableStateOf("") }
    var inputGermanWord by remember { mutableStateOf("") }
    var selectLevel by remember { mutableStateOf("A1") }

    var inputGrammarQuestion by remember { mutableStateOf("") }
    var inputGrammarAnswer by remember { mutableStateOf("") }
    var inputGrammarExplanation by remember { mutableStateOf("") }

    var categoryTypeName by remember { mutableStateOf("") }
    var categoryTypeSelection by remember { mutableStateOf("Vocabulary") } // "Vocabulary", "Sentence", "Grammar"

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Toolbar tabs switcher
        item {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                val tabs = listOf("Vocab", "Sentence", "Grammar", "Categories")
                tabs.forEach { tab ->
                    val isSelected = activeManagerTab == tab
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { activeManagerTab = tab }
                            .padding(vertical = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = tab,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                        if (isSelected) {
                            Spacer(modifier = Modifier.height(3.dp))
                            Box(
                                modifier = Modifier
                                    .width(30.dp)
                                    .height(3.dp)
                                    .background(PrimaryBlue, RoundedCornerShape(2.dp))
                            )
                        }
                    }
                }
            }
        }

        // ENTRY FORM AREA
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Add Custom Content Node",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.primary
                    )

                    when (activeManagerTab) {
                        "Categories" -> {
                            OutlinedTextField(
                                value = categoryTypeName,
                                onValueChange = { categoryTypeName = it },
                                label = { Text("Category Name (e.g., Work, Travel)") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            // Type selecting pills
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                val types = listOf("Vocabulary", "Sentence", "Grammar")
                                types.forEach { typ ->
                                    val actType = categoryTypeSelection == typ
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(if (actType) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background)
                                            .clickable { categoryTypeSelection = typ }
                                            .padding(vertical = 8.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = typ,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 10.sp,
                                            color = if (actType) Color.White else MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                            }

                            Button(
                                onClick = {
                                    if (categoryTypeName.isNotBlank()) {
                                        viewModel.addCategory(categoryTypeName, categoryTypeSelection)
                                        categoryTypeName = ""
                                    }
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Register Category")
                            }
                        }

                        "Vocab" -> {
                            OutlinedTextField(
                                value = inputBengaliWord,
                                onValueChange = { inputBengaliWord = it },
                                label = { Text("Bengali Word (বাংলা শব্দ)") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            OutlinedTextField(
                                value = inputGermanWord,
                                onValueChange = { inputGermanWord = it },
                                label = { Text("German Translation (জার্মান অনুবাদ)") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            
                            // Category Dropdown simulated / manual string
                            val vocabCats = categories.filter { it.type == "Vocabulary" }
                            if (selectCategory.isEmpty() && vocabCats.isNotEmpty()) {
                                selectCategory = vocabCats.first().name
                            }

                            Text("Select Active Category Domain:", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                vocabCats.forEach { c ->
                                    val isSelected = selectCategory == c.name
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) else MaterialTheme.colorScheme.background)
                                            .clickable { selectCategory = c.name }
                                            .padding(horizontal = 10.dp, vertical = 6.dp)
                                    ) {
                                        Text(c.name, fontSize = 10.sp, color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface)
                                    }
                                }
                            }

                            Text("Select Difficulty Level:", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                listOf("A1", "A2", "B1").forEach { level ->
                                    val isSelected = selectLevel == level
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background)
                                            .clickable { selectLevel = level }
                                            .padding(vertical = 8.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(level, fontWeight = FontWeight.Bold, color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface)
                                    }
                                }
                            }

                            Button(
                                onClick = {
                                    if (inputBengaliWord.isNotBlank() && inputGermanWord.isNotBlank()) {
                                        viewModel.addVocabulary(
                                            category = selectCategory.ifEmpty { "General" },
                                            bengaliWord = inputBengaliWord,
                                            germanTranslation = inputGermanWord,
                                            lvl = selectLevel
                                        )
                                        inputBengaliWord = ""
                                        inputGermanWord = ""
                                    }
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Register Vocabulary Word")
                            }
                        }

                        "Sentence" -> {
                            OutlinedTextField(
                                value = inputBengaliWord,
                                onValueChange = { inputBengaliWord = it },
                                label = { Text("Bengali Sentence (বাংলা বাক্য)") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            OutlinedTextField(
                                value = inputGermanWord,
                                onValueChange = { inputGermanWord = it },
                                label = { Text("German Translation (জার্মান অনুবাদ)") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            
                            val sentenceCats = categories.filter { it.type == "Sentence" }
                            if (selectCategory.isEmpty() && sentenceCats.isNotEmpty()) {
                                selectCategory = sentenceCats.first().name
                            }

                            Text("Select Target Category:", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                sentenceCats.forEach { c ->
                                    val isSelected = selectCategory == c.name
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) else MaterialTheme.colorScheme.background)
                                            .clickable { selectCategory = c.name }
                                            .padding(horizontal = 10.dp, vertical = 6.dp)
                                    ) {
                                        Text(c.name, fontSize = 10.sp, color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface)
                                    }
                                }
                            }

                            Button(
                                onClick = {
                                    if (inputBengaliWord.isNotBlank() && inputGermanWord.isNotBlank()) {
                                        viewModel.addSentence(
                                            category = selectCategory.ifEmpty { "General" },
                                            bengaliSentence = inputBengaliWord,
                                            germanTranslation = inputGermanWord,
                                            lvl = selectLevel
                                        )
                                        inputBengaliWord = ""
                                        inputGermanWord = ""
                                    }
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Register Sentence Node")
                            }
                        }

                        "Grammar" -> {
                            OutlinedTextField(
                                value = inputGrammarQuestion,
                                onValueChange = { inputGrammarQuestion = it },
                                label = { Text("Grammar Question/Bengali cue") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            OutlinedTextField(
                                value = inputGrammarAnswer,
                                onValueChange = { inputGrammarAnswer = it },
                                label = { Text("Grammar Correct German Phrase") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            OutlinedTextField(
                                value = inputGrammarExplanation,
                                onValueChange = { inputGrammarExplanation = it },
                                label = { Text("Rule Explanation (In English / Bengali)") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            
                            val grammarCats = categories.filter { it.type == "Grammar" }
                            if (selectCategory.isEmpty() && grammarCats.isNotEmpty()) {
                                selectCategory = grammarCats.first().name
                            }

                            Text("Select Targets Domain:", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                grammarCats.forEach { c ->
                                    val isSelected = selectCategory == c.name
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) else MaterialTheme.colorScheme.background)
                                            .clickable { selectCategory = c.name }
                                            .padding(horizontal = 10.dp, vertical = 6.dp)
                                    ) {
                                        Text(c.name, fontSize = 10.sp, color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface)
                                    }
                                }
                            }

                            Button(
                                onClick = {
                                    if (inputGrammarQuestion.isNotBlank() && inputGrammarAnswer.isNotBlank()) {
                                        viewModel.addGrammar(
                                            category = selectCategory.ifEmpty { "Verbs" },
                                            question = inputGrammarQuestion,
                                            correctAnswer = inputGrammarAnswer,
                                            explanation = inputGrammarExplanation,
                                            lvl = selectLevel
                                        )
                                        inputGrammarQuestion = ""
                                        inputGrammarAnswer = ""
                                        inputGrammarExplanation = ""
                                    }
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Register Grammar Core Exercise")
                            }
                        }
                    }
                }
            }
        }

        // MANAGE CURRENT ITEMS LIST
        item {
            Text(
                text = "Registered Data Nodes (Swipe / Delete to clean)",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        when (activeManagerTab) {
            "Categories" -> {
                items(categories) { cat ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(cat.name, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                                Text("Type Domain: ${cat.type}", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                            }
                            IconButton(onClick = { viewModel.deleteCategory(cat) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = ErrorRed)
                            }
                        }
                    }
                }
            }

            "Vocab" -> {
                items(vocabList) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(item.bengaliWord, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                                Text(item.germanTranslation, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontSize = 15.sp)
                                Text("Category: ${item.category} | Lvl: ${item.difficultyLevel}", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
                            }
                            IconButton(onClick = { viewModel.deleteVocabulary(item) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = ErrorRed)
                            }
                        }
                    }
                }
            }

            "Sentence" -> {
                items(sentencesList) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(item.bengaliSentence, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                                Text(item.germanTranslation, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontSize = 14.sp)
                                Text("Category: ${item.category} | Lvl: ${item.difficultyLevel}", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
                            }
                            IconButton(onClick = { viewModel.deleteSentence(item) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = ErrorRed)
                            }
                        }
                    }
                }
            }

            "Grammar" -> {
                items(grammarList) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(11.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(item.question, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                                Text(item.correctAnswer, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontSize = 14.sp)
                                Text("Category: ${item.category} | Expl: ${item.explanation.take(30)}...", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
                            }
                            IconButton(onClick = { viewModel.deleteGrammar(item) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = ErrorRed)
                            }
                        }
                    }
                }
            }
        }
    }
}


// ==================== MISTAKES REVIEW LIST SCREEN ====================
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MistakesScreen(viewModel: LanguageViewModel) {
    val mistakes by viewModel.mistakes.collectAsStateWithLifecycle()

    var activeMistakeSelection by remember { mutableStateOf<ReviewMistake?>(null) }
    var activeCorrectedDraft by remember { mutableStateOf("") }
    var feedbackMsg by remember { mutableStateOf<String?>(null) }

    if (mistakes.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(64.dp))
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "দারুণ! আপনার কোনো ভুলের ইতিহাস নেই।",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Everything was parsed successfully. Keep reading, typing and practice!",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    textAlign = TextAlign.Center
                )
            }
        }
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Mistakes Queue (${mistakes.size})",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = ErrorRed
                )
                Button(
                    onClick = { viewModel.clearMistakes() },
                    colors = ButtonDefaults.buttonColors(containerColor = ErrorRed.copy(alpha = 0.1f)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Clear All", color = ErrorRed, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                }
            }
        }

        if (activeMistakeSelection != null) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Correction Session active 🎛️",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 12.sp
                        )

                        Text(
                            text = activeMistakeSelection!!.bengaliText,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Text(
                            text = "Last answer you wrote: \"${activeMistakeSelection!!.userGermanEntered}\"",
                            color = ErrorRed,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        OutlinedTextField(
                            value = activeCorrectedDraft,
                            onValueChange = { activeCorrectedDraft = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Write corrected German translation...") },
                            shape = RoundedCornerShape(12.dp)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Button(
                                onClick = {
                                    val uText = activeCorrectedDraft.trim().lowercase().replace(Regex("[.\\-?,!]+"), "")
                                    val eText = activeMistakeSelection!!.expectedGerman.trim().lowercase().replace(Regex("[.\\-?,!]+"), "")
                                    if (uText == eText) {
                                        viewModel.speak(activeMistakeSelection!!.expectedGerman)
                                        viewModel.deleteMistake(activeMistakeSelection!!.id)
                                        feedbackMsg = "Correct! ✅ Item removed from error log."
                                        activeMistakeSelection = null
                                        activeCorrectedDraft = ""
                                    } else {
                                        feedbackMsg = "Not quite! Expected answer is still: \"${activeMistakeSelection!!.expectedGerman}\""
                                    }
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Verify & Save")
                            }
                            
                            OutlinedButton(
                                onClick = {
                                    activeMistakeSelection = null
                                    activeCorrectedDraft = ""
                                    feedbackMsg = null
                                }
                            ) {
                                Text("Cancel")
                            }
                        }
                    }
                }
            }
        }

        if (feedbackMsg != null) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                        .padding(12.dp)
                ) {
                    Text(text = feedbackMsg!!, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                }
            }
        }

        items(mistakes) { mist ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        activeMistakeSelection = mist
                        activeCorrectedDraft = ""
                        feedbackMsg = null
                    },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Type: ${mist.contentType.uppercase()}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        IconButton(onClick = { viewModel.deleteMistake(mist.id) }) {
                            Icon(Icons.Default.Close, contentDescription = "Dismiss", tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f), modifier = Modifier.size(16.dp))
                        }
                    }
                    
                    Text(
                        text = mist.bengaliText,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "You entered: \"${mist.userGermanEntered}\"",
                        fontSize = 11.sp,
                        color = ErrorRed
                    )
                    Text(
                        text = "Correct answer: \"${mist.expectedGerman}\"",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = SuccessGreen
                    )
                }
            }
        }
    }
}


// ==================== HELPER REUSABLE COMPOSABLES ====================
@Composable
fun CardTrackerHeader(index: Int, total: Int, level: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "${index + 1} of $total",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }

        Surface(
            color = Color(0xFFEFF6FF),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Level $level",
                color = PrimaryBlue,
                fontWeight = FontWeight.Black,
                fontSize = 12.sp,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }
    }
}

@Composable
fun EvaluationFeedback(
    evaluation: Boolean?, 
    expectedAnswer: String,
    aiFeedback: String? = null,
    onScheduleAuto: ((Int) -> Unit)? = null,
    onScheduleManual: ((Long) -> Unit)? = null
) {
    if (evaluation == null) return
    
    val containerBg = if (evaluation) SuccessGreen.copy(alpha = 0.15f) else ErrorRed.copy(alpha = 0.15f)
    val textColor = if (evaluation) SuccessGreen else ErrorRed
    val icon = if (evaluation) Icons.Default.CheckCircle else Icons.Default.HighlightOff
    val heading = if (evaluation) "Outstanding! Correct ✅" else "Oops! Incorrect ❌"

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("evaluation_box")
            .clip(RoundedCornerShape(16.dp))
            .background(containerBg)
            .padding(16.dp)
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(icon, contentDescription = null, tint = textColor, modifier = Modifier.size(36.dp))
                Column {
                    Text(text = heading, fontWeight = FontWeight.Bold, color = textColor, fontSize = 15.sp)
                    if (!evaluation) {
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Correct syntax: \"$expectedAnswer\"",
                            color = textColor,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            if (aiFeedback != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.5f))
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = "AI", tint = Color(0xFF9C27B0))
                        Text(
                            text = aiFeedback,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            if (onScheduleManual != null || onScheduleAuto != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Next revision time (Spaced Repetition):",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = textColor
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    val intervals = listOf(
                        Triple("AGAIN (10m)", 10 * 60 * 1000L, 0), // quality 0
                        Triple("HARD (Manual)", 24 * 60 * 60 * 1000L, 2), // quality 2
                        Triple("GOOD (Auto)", -1L, 4), // quality 4
                        Triple("EASY (Auto)", -1L, 5)  // quality 5
                    )
                    intervals.forEach { (label, durationMs, quality) ->
                        Surface(
                            onClick = { 
                                if (durationMs == -1L && onScheduleAuto != null) {
                                    onScheduleAuto(quality)
                                } else if (onScheduleManual != null) {
                                    onScheduleManual(durationMs)
                                }
                            },
                            color = textColor.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = label,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = textColor,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyStateView(msg: String, desc: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(Icons.Default.MenuBook, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f), modifier = Modifier.size(64.dp))
            Text(
                text = msg,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
            Text(
                text = desc,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                textAlign = TextAlign.Center
            )
        }
    }
}

enum class ExerciseType { FLASHCARD, MCQ, FILL_IN_BLANK, TRANSLATION }

data class DueReviewItem(
    val type: String,
    val id: Int,
    val question: String,
    val expectedAnswer: String,
    val category: String,
    val exerciseType: ExerciseType = ExerciseType.TRANSLATION,
    val mcqOptions: List<String> = emptyList(),
    val fillPrefix: String = "",
    val fillSuffix: String = ""
)

@Composable
fun SmartRevisionScreen(viewModel: LanguageViewModel) {
    var setupDone by remember { mutableStateOf(false) }

    if (!setupDone) {
        PracticeSetupScreen(
            viewModel = viewModel,
            moduleType = "smart_revision",
            onStart = { setupDone = true }
        )
        return
    }

    val textState = remember { mutableStateOf("") }
    val evaluation = remember { mutableStateOf<Boolean?>(null) }
    val aiFeedbackState = remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()
    
    // We compute due items locally using a flow or derived state
    val vList by viewModel.vocabularyList.collectAsStateWithLifecycle()
    val sList by viewModel.sentenceList.collectAsStateWithLifecycle()
    val gList by viewModel.grammarList.collectAsStateWithLifecycle()
    
    val dueItems = remember(vList, sList, gList) {
        val now = System.currentTimeMillis()
        
        val vi = vList.filter { it.nextRevisionTimeMs in 1..now }.map { item ->
            val exType = listOf(ExerciseType.FLASHCARD, ExerciseType.MCQ, ExerciseType.TRANSLATION).random()
            val mcqOpts = if (exType == ExerciseType.MCQ) {
                val others = vList.filter { it.id != item.id }.map { it.germanTranslation }.shuffled().take(3)
                (others + item.germanTranslation).shuffled()
            } else emptyList()
            
            DueReviewItem("vocab", item.id, "শব্দ: ${item.bengaliWord}", item.germanTranslation, item.category, exType, mcqOptions = mcqOpts)
        }
        
        val si = sList.filter { it.nextRevisionTimeMs in 1..now }.map { item ->
            val exType = listOf(ExerciseType.FILL_IN_BLANK, ExerciseType.TRANSLATION).random()
            var prefix = ""
            var suffix = ""
            var expected = item.germanTranslation
            if (exType == ExerciseType.FILL_IN_BLANK) {
                val words = item.germanTranslation.split(" ")
                if (words.size > 1) {
                    val missingIdx = (words.indices).random()
                    expected = words[missingIdx]
                    prefix = words.take(missingIdx).joinToString(" ")
                    suffix = words.drop(missingIdx + 1).joinToString(" ")
                }
            }
            DueReviewItem("sentence", item.id, "বাক্য: ${item.bengaliSentence}", expected, item.category, exType, fillPrefix = prefix, fillSuffix = suffix)
        }
        
        val gi = gList.filter { it.nextRevisionTimeMs in 1..now }.map { item ->
            DueReviewItem("grammar", item.id, "প্রশ্ন: ${item.question}", item.correctAnswer, item.category, ExerciseType.TRANSLATION)
        }
        
        var combined = (vi + si + gi).shuffled()
        val limit = viewModel.practiceLimit.value
        if (limit > 0) {
            combined = combined.take(limit)
        }
        combined
    }
    
    var index by remember { mutableStateOf(0) }
    
    if (dueItems.isEmpty() || index >= dueItems.size) {
        EmptyStateView("All caught up!", "Great job! You have no more spaced repetition reviews due right now.")
        return
    }
    
    val activeItem = dueItems[index]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CardTrackerHeader(index = index, total = dueItems.size, level = "Mixed")

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Spaced Repetition: ${activeItem.category}",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(14.dp))
                
                Text(
                    text = activeItem.question,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        EvaluationFeedback(
            evaluation = evaluation.value,
            expectedAnswer = activeItem.expectedAnswer,
            aiFeedback = aiFeedbackState.value,
            onScheduleManual = { delay -> 
                viewModel.scheduleRevision(activeItem.type, activeItem.id, delay)
                evaluation.value = null
                textState.value = ""
                aiFeedbackState.value = null
                index++
            },
            onScheduleAuto = { quality ->
                viewModel.scheduleRevision(activeItem.type, activeItem.id, 0, quality)
                evaluation.value = null
                textState.value = ""
                aiFeedbackState.value = null
                index++
            }
        )

        if (evaluation.value == null && activeItem.exerciseType == ExerciseType.FLASHCARD) {
            Button(
                onClick = { evaluation.value = true },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Reveal Answer", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        } else if (activeItem.exerciseType == ExerciseType.MCQ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                activeItem.mcqOptions.forEach { opt ->
                    Button(
                        onClick = {
                            val userAns = opt.trim().lowercase().replace(Regex("[.\\-?,!]+"), "")
                            val exactAns = activeItem.expectedAnswer.trim().lowercase().replace(Regex("[.\\-?,!]+"), "")
                            evaluation.value = (userAns == exactAns)
                        },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        enabled = evaluation.value == null
                    ) {
                        Text(opt, fontSize = 16.sp)
                    }
                }
            }
        } else if (activeItem.exerciseType == ExerciseType.FILL_IN_BLANK) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                    Text(activeItem.fillPrefix, fontSize = 16.sp)
                    Spacer(Modifier.width(8.dp))
                    OutlinedTextField(
                        value = textState.value,
                        onValueChange = { textState.value = it },
                        modifier = Modifier.widthIn(min = 100.dp, max = 150.dp),
                        singleLine = true
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(activeItem.fillSuffix, fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (evaluation.value == null) {
                            val userAns = textState.value.trim().lowercase().replace(Regex("[.\\-?,!]+"), "")
                            val exactAns = activeItem.expectedAnswer.trim().lowercase().replace(Regex("[.\\-?,!]+"), "")
                            val correct = (userAns == exactAns)
                            evaluation.value = correct
                            if (!correct && (activeItem.type == "sentence" || activeItem.type == "grammar")) {
                                aiFeedbackState.value = "Analyzing grammar error..."
                                coroutineScope.launch {
                                    val fullGiven = "${activeItem.fillPrefix} ${textState.value} ${activeItem.fillSuffix}"
                                    val fullExpected = "${activeItem.fillPrefix} ${activeItem.expectedAnswer} ${activeItem.fillSuffix}"
                                    aiFeedbackState.value = com.example.data.GeminiClient.checkSentence(fullGiven, fullExpected)
                                }
                            }
                        } else {
                            evaluation.value = null
                            textState.value = ""
                            aiFeedbackState.value = null
                            index++
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (evaluation.value == null) MaterialTheme.colorScheme.primary else SuccessGreen
                    )
                ) {
                    Text(if (evaluation.value == null) "Check" else "Next ➔", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        } else {
            // TRANSLATION style
            OutlinedTextField(
                value = textState.value,
                onValueChange = { textState.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("srs_input")
                    .heightIn(min = 64.dp),
                placeholder = { Text("Write answer here...") },
                shape = RoundedCornerShape(16.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (evaluation.value == null) {
                        val userAns = textState.value.trim().lowercase().replace(Regex("[.\\-?,!]+"), "")
                        val exactAns = activeItem.expectedAnswer.trim().lowercase().replace(Regex("[.\\-?,!]+"), "")
                        val correct = (userAns == exactAns)
                        evaluation.value = correct
                        if (!correct && (activeItem.type == "sentence" || activeItem.type == "grammar")) {
                            aiFeedbackState.value = "Analyzing grammar error..."
                            coroutineScope.launch {
                                aiFeedbackState.value = com.example.data.GeminiClient.checkSentence(textState.value, activeItem.expectedAnswer)
                            }
                        }
                    } else {
                        evaluation.value = null
                        textState.value = ""
                        aiFeedbackState.value = null
                        index++
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (evaluation.value == null) MaterialTheme.colorScheme.primary else SuccessGreen
                )
            ) {
                Text(
                    text = if (evaluation.value == null) "Check Interpretation" else "Next Due Item ➔",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}
