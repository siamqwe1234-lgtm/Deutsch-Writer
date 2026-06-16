package com.example.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.spring
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.ui.viewmodel.LanguageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistLessonsScreen(
    viewModel: LanguageViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val completedSet by viewModel.completedLessons.collectAsStateWithLifecycle()
    val profile by viewModel.profile.collectAsStateWithLifecycle()
    
    var selectedLessonId by remember { mutableStateOf<Int?>(null) }
    var searchQuery by remember { mutableStateOf("") }

    val allLessons = remember { LectureLogData.lessons + com.example.data.LectureLogData2.extendedLessons }
    val filteredLessons = remember(searchQuery) {
        if (searchQuery.isBlank()) {
            allLessons
        } else {
            allLessons.filter {
                it.title.contains(searchQuery, ignoreCase = true) ||
                it.grammarTopics.any { t -> t.contains(searchQuery, ignoreCase = true) } ||
                it.vocabularyList.any { v -> v.word.contains(searchQuery, ignoreCase = true) || v.translation.contains(searchQuery, ignoreCase = true) }
            }
        }
    }

    if (selectedLessonId != null) {
        val lesson = allLessons.first { it.videoNumber == selectedLessonId }
        LessonDetailView(
            lesson = lesson,
            isCompleted = completedSet.contains(lesson.videoNumber),
            onToggleCompleted = { viewModel.toggleLessonCompleted(lesson.videoNumber) },
            viewModel = viewModel,
            onBack = { selectedLessonId = null }
        )
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Stats Header
            Card(
                modifier = Modifier
                    .fillModifier()
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "ক্রাশ কোর্স স্টাডি লগ",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "German Masterclass A1",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                        Surface(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "${completedSet.size} / ${allLessons.size} Done",
                                color = Color.White,
                                fontWeight = FontWeight.Black,
                                fontSize = 13.sp,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(14.dp))
                    
                    // Simple checklist dots for completing logs
                    Row(
                        modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        for (i in 1..allLessons.size) {
                            val isDone = completedSet.contains(i)
                            Box(
                                modifier = Modifier
                                    .width(10.dp)
                                    .height(10.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (isDone) MaterialTheme.colorScheme.primary 
                                        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f)
                                    )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "🏆 প্রতিটি ভিডিওর স্টাডি নোট কমপ্লিট করুন এবং ইন্টারেক্টিভ কুইজে অংশ নিন। প্রতি লেসনে +৩০ XP প্রোগ্রেস বোনাস লাভ করুন!",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Search input field
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillIsValid()
                    .fillMaxWidth(),
                placeholder = { Text("শব্দ, বিষয়বস্তু বা থিওরি খুঁজুন...", fontSize = 14.sp) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear")
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                )
            )

            // Scrollable Lessons Feed List
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                if (filteredLessons.isEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 40.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                Icons.Default.MenuBook,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                                modifier = Modifier.size(64.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "কোনো লেকচার লগ পাওয়া যায়নি",
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                } else {
                    items(filteredLessons) { lesson ->
                        val isDone = completedSet.contains(lesson.videoNumber)
                        LessonLogCard(
                            lesson = lesson,
                            isCompleted = isDone,
                            onToggleCompleted = { viewModel.toggleLessonCompleted(lesson.videoNumber) },
                            onClick = { selectedLessonId = lesson.videoNumber }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LessonLogCard(
    lesson: LectureLog,
    isCompleted: Boolean,
    onToggleCompleted: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("lesson_card_${lesson.videoNumber}"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(
                                if (isCompleted) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                                else MaterialTheme.colorScheme.secondaryContainer
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = String.format("%02d", lesson.videoNumber),
                            fontWeight = FontWeight.Black,
                            fontSize = 14.sp,
                            color = if (isCompleted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                    
                    Surface(
                        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = lesson.cefrLevel,
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }

                // Checkbox to mark completion directly
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onToggleCompleted() }
                ) {
                    Checkbox(
                        checked = isCompleted,
                        onCheckedChange = { onToggleCompleted() }
                    )
                    Text(
                        text = "Done",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isCompleted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = lesson.title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "💡 মূল শিক্ষা:",
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = lesson.keyLearnings.firstOrNull() ?: "",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${lesson.vocabularyList.size} Words • ${lesson.sentenceStructures.size} Syntax Formations",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                    fontWeight = FontWeight.Medium
                )
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "নোট ও প্র্যাকটিস",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Icon(
                        Icons.Default.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun LessonDetailView(
    lesson: LectureLog,
    isCompleted: Boolean,
    onToggleCompleted: () -> Unit,
    viewModel: LanguageViewModel,
    onBack: () -> Unit
) {
    var activeTab by remember { mutableStateOf(0) }
    val tabsList = listOf("লেকচার নোট", "ভোকাবুলারি (${lesson.vocabularyList.size})", "সেন্টেন্স ও গ্রামার", "প্র্যাকটিস কুইজ")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Upper Title block
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.onSurface)
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "LECTURE LOGS • VIDEO #${lesson.videoNumber}",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = lesson.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            
            IconButton(onClick = onToggleCompleted) {
                Icon(
                    imageVector = if (isCompleted) Icons.Default.CheckCircle else Icons.Outlined.CheckCircle,
                    contentDescription = "Completed",
                    tint = if (isCompleted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        // Secondary visual progress row or level tags
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "A1 Level Certified",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
            if (isCompleted) {
                Surface(
                    color = Color.Green.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Completed (+30 XP Gained)",
                        color = Color(0xFF2E7D32),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Tab bar UI
        ScrollableTabRow(
            selectedTabIndex = activeTab,
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.primary,
            edgePadding = 16.dp
        ) {
            tabsList.forEachIndexed { index, title ->
                Tab(
                    selected = activeTab == index,
                    onClick = { activeTab = index },
                    text = {
                        Text(
                            text = title,
                            fontWeight = if (activeTab == index) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 14.sp
                        )
                    }
                )
            }
        }

        HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))

        // Content panel based on Active Tab index
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(MaterialTheme.colorScheme.background)
        ) {
            when (activeTab) {
                0 -> LectureNotesTab(lesson = lesson)
                1 -> VocabularyTab(lesson = lesson, viewModel = viewModel)
                2 -> SentenceSyntaxTab(lesson = lesson, viewModel = viewModel)
                3 -> LessonQuizTab(lesson = lesson, isCompleted = isCompleted, onCompleted = onToggleCompleted)
            }
        }
    }
}

// ---------------- TAB 1: GENERAL NOTES & MASTER INSIGHTS ----------------
@Composable
fun LectureNotesTab(lesson: LectureLog) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // CEFR & Summary
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "📖 মূল শিক্ষা ও সারসংক্ষেপ",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    lesson.keyLearnings.forEach { str ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text("•", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Black)
                            Text(text = str, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
                        }
                    }
                }
            }
        }

        // Grammar rules list
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "📝 ব্যাকরণ এবং রুলস (Grammar & Rules)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    lesson.grammarTopics.forEach { str ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Default.BookmarkBorder, contentDescription = null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(16.dp).padding(top = 2.dp))
                            Text(text = str, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
                        }
                    }
                }
            }
        }

        // Master class insights
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "⭐ শিক্ষক মহোদয়ের বিশেষ পরামর্শ ও টিপস",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    lesson.insights.forEach { ins ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text("✨", fontSize = 13.sp)
                            Text(text = ins, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
                        }
                    }
                }
            }
        }

        // Quick review cheat card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("📋", fontSize = 16.sp)
                        Text(
                            text = "এক নজরে রিভিশন (Quick Revision Check)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = lesson.quickRevision,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

// ---------------- TAB 2: DETAILED VOCABULARY LIST ----------------
@Composable
fun VocabularyTab(lesson: LectureLog, viewModel: LanguageViewModel) {
    if (lesson.vocabularyList.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("এই লেসনে আলাদা কোনো শব্দ তালিকা নেই।")
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(lesson.vocabularyList) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = item.word,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                IconButton(
                                    onClick = { viewModel.speak(item.word) },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        Icons.Default.VolumeUp,
                                        contentDescription = "Speak Pronunciation",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "→ ${item.translation}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            if (item.explanation.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "💡 ${item.explanation}",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// ---------------- TAB 3: KEY SENTENCES & GESTURE EXTRAS ----------------
@Composable
fun SentenceSyntaxTab(lesson: LectureLog, viewModel: LanguageViewModel) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Extra visual prepositions tricks if it is Lesson 12
        if (lesson.videoNumber == 12) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "🙌 শারীরিক অঙ্গভঙ্গিতে প্রিপজিশন মনে রাখার ট্রিক",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        val gestures = listOf(
                            "👄 মুখে স্পর্শ থাকা ➔ an (যেমন: দেওয়ালে ঝুলন্ত)",
                            "💁 মাথার ওপর স্পর্শ থাকা ➔ auf (যেমন: টেবিলের ওপর রাখা)",
                            "☁️ মাথার ওপরে শূন্যস্থান বা ফাঁকা ➔ über (যেমন: ঝুলন্ত ফ্যান)",
                            "⬇️ মুখের নিচে অবস্থান ➔ unter (যেমন: টেবিলের তলায়)",
                            "👤 মুখের সম্মুখে ➔ vor / পেছনে ➔ hinter",
                            "👉 হাত এক পাশে রাখা ➔ neben / দুই হাতের মাঝখানে ➔ zwischen"
                        )
                        gestures.forEach { text ->
                            Text(
                                text = text,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                modifier = Modifier.padding(vertical = 2.dp)
                            )
                        }
                    }
                }
            }
        }

        // List sentence list
        if (lesson.sentenceStructures.isEmpty()) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("এই লেসনে বিশেষ কোনো বাক্য কাঠামো দেওয়া নেই।")
                }
            }
        } else {
            items(lesson.sentenceStructures) { sent ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "কাঠামো: ${sent.pattern}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.secondary
                            )
                            IconButton(onClick = { viewModel.speak(sent.example) }, modifier = Modifier.size(24.dp)) {
                                Icon(Icons.Default.VolumeUp, contentDescription = "Listen", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = sent.example,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "bengali: ${sent.bengaliMatch}",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

// ---------------- TAB 4: INTERACTIVE PRACTICE QUIZ ----------------
data class CustomLessonQuiz(
    val question: String,
    val options: List<String>,
    val correctIdx: Int,
    val explanation: String
)

@Composable
fun LessonQuizTab(lesson: LectureLog, isCompleted: Boolean, onCompleted: () -> Unit) {
    // Generate tailored quizzes for each of the 13 lessons
    val quiz = remember(lesson.videoNumber) {
        when (lesson.videoNumber) {
            1 -> CustomLessonQuiz(
                question = "জার্মান ভাষায় ‘আমরা’ (We)-র সঠিক প্রোনাউন কোনটি?",
                options = listOf("Ich", "Du", "Er", "Wir"),
                correctIdx = 3,
                explanation = "Wir অর্থ হলো 'আমরা'। Ich (আমি), Du (তুমি), Er (সে)।"
            )
            2 -> CustomLessonQuiz(
                question = "‘trinken’ (পান করা) ক্রিয়াপদটিকে ‘Du’ (তুমি) অনুযায়ী রূপান্তর বা Conjugate করলে সঠিক রূপ কী হবে?",
                options = listOf("trinke", "trinkst", "trinkt", "trinken"),
                correctIdx = 1,
                explanation = "রুল অনুযায়ী, Du-এর সাথে ‘-st’ সাফিক্স যুক্ত হয়। তাই সঠিক উত্তর: Du trinkst।"
            )
            3 -> CustomLessonQuiz(
                question = "২৩ (23) সংখ্যাটির সঠিক জার্মান অনুবাদ কোনটি?",
                options = listOf("zwanzigdrei", "dreizwanzig", "dreiundzwanzig", "einundzwanzig"),
                correctIdx = 2,
                explanation = "রুল: প্রথমে একক ঘর (drei/3) বসে, মাঝে 'und', এবং শেষে দশক ঘর (zwanzig/20) বসে।"
            )
            4 -> CustomLessonQuiz(
                question = "‘Ich wohne ___ Dhaka.’ (আমি ঢাকায় থাকি।) শূন্যস্থানে কোন প্রিপজিশন বসবে?",
                options = listOf("aus", "in", "mit", "zu"),
                correctIdx = 1,
                explanation = "শহরের নামের পূর্বে বসবাসের ক্ষেত্রে সর্বদা 'in' এবং দেশের থেকে আসার ক্ষেত্রে ‘aus’ বসে।"
            )
            5 -> CustomLessonQuiz(
                question = "জার্মান বাক্যে মোডাল ভার্ব যুক্ত হলে, মূল অ্যাকশন ভার্বটি কোথায় বসে?",
                options = listOf("২য় পজিশনে", "১ম পজিশনে", "অপরিবর্তিত (Infinitive) অবস্থায় বাক্যের একেবারে শেষে", "বাক্যের শুরুতে"),
                correctIdx = 2,
                explanation = "মোডাল ভার্ব ২য় পজিশনে বসে সাবজেক্ট অনুযায়ী কনজুগেট হয়, আর মূল ভার্ব ইনফিনিটিভ উপায়ে একদম শেষে বসে।"
            )
            6 -> CustomLessonQuiz(
                question = "‘werden’ (Will) সাহায্যকারী ভার্বটির ‘Du’-এর সাপেক্ষে কনজুগেটেড রূপ কী?",
                options = listOf("werde", "wird", "wirst", "werdet"),
                correctIdx = 2,
                explanation = "werden conjugation for Du is 'wirst' (Du wirst morgen lernen)."
            )
            7 -> CustomLessonQuiz(
                question = "সম্মানসূচক বা অপরিচিতদের সাথে জার্মান ভাষায় কোন সর্বনামটি (Pronoun) ব্যবহার করা আবশ্যক?",
                options = listOf("du", "Sie (ক্যাপিটাল S বিশিষ্ট)", "sie", "ihr"),
                correctIdx = 1,
                explanation = "সম্মানসূচক আপনি বুঝাতে ‘Sie’ প্রোনাউনটি সর্বদা বড় হাতের ‘S’ দিয়ে লেখা হয়।"
            )
            8 -> CustomLessonQuiz(
                question = "জার্মান ভাষায় হ্যাঁ/না বোধক প্রশ্নে (Yes/No Questions) কনজুগেটেড ভার্বের সঠিক পজিশন কত?",
                options = listOf("২য় পজিশনে", "১ম পজিশনে", "বাক্যের একদম শেষে", "৩য় পজিশনে"),
                correctIdx = 1,
                explanation = "হ্যাঁ/না প্রশ্নের ক্ষেত্রে Verb সবসময় ১ম পজিশনে বসে শুরু হয় (যেমন: Spielst du?)."
            )
            9 -> CustomLessonQuiz(
                question = "রাতের বিদায় সম্ভাষণ ‘शुभ রাত্রি’ (Good night)-র জন্য সঠিক জার্মান ফ্রেজ কোনটি?",
                options = listOf("Guten Morgen", "Guten Nacht", "Gute Nacht", "Guten Abend"),
                correctIdx = 2,
                explanation = "Nacht (স্ত্রীলিঙ্গ) হওয়ায় শেষে ‘e’ যুক্ত হয়ে ‘Gute Nacht’ হয়, Guten নয়।"
            )
            10 -> CustomLessonQuiz(
                question = "বাক্যে ডিরেক্ট অবজেক্ট (Direct Object)-এর ওপর কোন কেস বা কারক বাস্তবায়িত হয়?",
                options = listOf("Nominative", "Accusative", "Dative", "Genitive"),
                correctIdx = 1,
                explanation = "Nominative হলো কর্তা (Subject), Dative হলো ইনডিরেক্ট অবজেক্ট, এবং Accusative ডিরেক্ট অবজেক্ট।"
            )
            11 -> CustomLessonQuiz(
                question = "কেস ট্রিক অনুযায়ী dative masculine বা dative neutral অবজেক্টের পূর্বে ইনডেফিনিট আর্টিকেলের রূপ কী হবে?",
                options = listOf("ein", "einen", "einem", "einer"),
                correctIdx = 2,
                explanation = "Dative-এ masculine/neuter আর্টিকেল Ein পরিবর্তিত হয়ে ‘einem’ হয়ে যায়।"
            )
            12 -> CustomLessonQuiz(
                question = "নিচের কোনটি dative preposition (যার পরে সর্বদা dative কেস বসে)?",
                options = listOf("für", "ohne", "gegen", "mit"),
                correctIdx = 3,
                explanation = "mit, zu, nach, aus, bei, seit, von এগুলোর পর সর্বদা dative কেস বসে।"
            )
            else -> CustomLessonQuiz(
                question = "জার্মান ভাষায় ‘আমি রান্না করছি’ (I am cooking) বুঝাতে কোন গঠনটি শতভাগ সঠিক?",
                options = listOf("Ich bin kochen", "Ich koche", "Ich kochende", "Ich bin am kochend"),
                correctIdx = 1,
                explanation = "জার্মান ভাষায় আলাদা কোনো কন্টিনিউয়াস টেন্স নেই। সাধারণ প্রেজেন্ট কাল ‘Ich koche’ এর অর্থই আমি রান্না করছি।"
            )
        }
    }

    var selectedOption by remember { mutableStateOf<Int?>(null) }
    var evaluated by remember { mutableStateOf(false) }
    var isCorrect by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "❔ লেসন কুইজ পরীক্ষা",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = quiz.question,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        items(quiz.options.size) { index ->
            val isSelected = selectedOption == index
            val cardColor = when {
                evaluated && index == quiz.correctIdx -> Color(0xFFC8E6C9) // Perfect light green
                evaluated && isSelected && !isCorrect -> Color(0xFFFFCDD2) // Soft error red
                isSelected -> MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                else -> MaterialTheme.colorScheme.surface
            }

            val borderColor = when {
                evaluated && index == quiz.correctIdx -> Color(0xFF2E7D32)
                evaluated && isSelected && !isCorrect -> Color(0xFFC62828)
                isSelected -> MaterialTheme.colorScheme.primary
                else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(!evaluated) { selectedOption = index }
                    .border(1.5.dp, borderColor, RoundedCornerShape(14.dp)),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = cardColor)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = quiz.options[index],
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                    
                    if (evaluated && index == quiz.correctIdx) {
                        Icon(Icons.Default.CheckCircle, contentDescription = "Correct", tint = Color(0xFF2E7D32))
                    } else if (evaluated && isSelected && !isCorrect) {
                        Icon(Icons.Default.Cancel, contentDescription = "Incorrect", tint = Color(0xFFC62828))
                    } else {
                        RadioButton(
                            selected = isSelected,
                            onClick = { if (!evaluated) selectedOption = index }
                        )
                    }
                }
            }
        }

        item {
            if (!evaluated) {
                Button(
                    onClick = {
                        if (selectedOption != null) {
                            evaluated = true
                            isCorrect = selectedOption == quiz.correctIdx
                            if (isCorrect && !isCompleted) {
                                // Mark video as completed automatically!
                                onCompleted()
                            }
                        }
                    },
                    enabled = selectedOption != null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("অনুবাদ যাচাই করুন", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isCorrect) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
                        )
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = if (isCorrect) "🎉 চমৎকার! উত্তরটি সঠিক হয়েছে।" else "❌ দুর্ভাগ্যবশত উত্তরটি ভুল হয়েছে।",
                                fontWeight = FontWeight.Bold,
                                color = if (isCorrect) Color(0xFF2E7D32) else Color(0xFFC62828),
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "রুলস ব্যাখ্যা: ${quiz.explanation}",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Button(
                        onClick = {
                            selectedOption = null
                            evaluated = false
                            isCorrect = false
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) {
                        Text("পুনরায় অনুশীলন করুন (Retry Quiz)", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

// Quick helper to fix standard issues
private fun Modifier.fillModifier(): Modifier = this
private fun Modifier.fillIsValid(): Modifier = this
