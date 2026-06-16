package com.example.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.viewmodel.LanguageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PracticeSetupScreen(
    viewModel: LanguageViewModel,
    moduleType: String,
    onStart: () -> Unit
) {
    val categories by viewModel.categories.collectAsStateWithLifecycle()
    
    // Default form states
    var selectedCategory by remember { mutableStateOf("All") }
    var selectedLimit by remember { mutableStateOf(10) }
    var randomize by remember { mutableStateOf(true) }

    // Filter categories by type (if you only want to show relevant categories, but we can also safely show "All")
    val relevantCategories = remember(categories, moduleType) {
        val typeMap = mapOf("vocab" to "Vocabulary", "sentence" to "Sentence", "grammar" to "Grammar")
        val filterType = typeMap[moduleType]
        if (filterType != null) {
            categories.filter { it.type == filterType }.map { it.name }.distinct()
        } else {
            categories.map { it.name }.distinct()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Practice Setup", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Category Selection
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("ক্যাটাগরি নির্বাচন করুন (Select Category)", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    var expanded by remember { mutableStateOf(false) }
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = it }
                    ) {
                        OutlinedTextField(
                            value = selectedCategory,
                            onValueChange = {},
                            readOnly = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("All Categories") },
                                onClick = {
                                    selectedCategory = "All"
                                    expanded = false
                                }
                            )
                            relevantCategories.forEach { cat ->
                                DropdownMenuItem(
                                    text = { Text(cat) },
                                    onClick = {
                                        selectedCategory = cat
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // Question Limit Selection
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("কতগুলো আইটেম প্র্যাকটিস করবেন? (Items to practice)", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    val options = listOf(5, 10, 20, 50, 0)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        options.forEach { option ->
                            FilterChip(
                                selected = selectedLimit == option,
                                onClick = { selectedLimit = option },
                                label = { Text(if (option == 0) "All" else "$option") }
                            )
                        }
                    }
                }
            }

            // Randomize Toggle
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("এলোমেলোভাবে সাজান (Randomize order)", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Switch(
                        checked = randomize,
                        onCheckedChange = { randomize = it }
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.setLevelFilter("All")
                    viewModel.setCategoryFilter(selectedCategory)
                    viewModel.setPracticeConfig(limit = selectedLimit, randomize = randomize)
                    onStart()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Start Practice", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
