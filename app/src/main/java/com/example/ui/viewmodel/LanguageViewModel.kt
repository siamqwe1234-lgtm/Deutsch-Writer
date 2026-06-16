package com.example.ui.viewmodel

import android.app.Application
import android.speech.tts.TextToSpeech
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class LanguageViewModel(
    application: Application,
    private val repository: LanguageRepository
) : AndroidViewModel(application), TextToSpeech.OnInitListener {

    // TextToSpeech Engine
    private var tts: TextToSpeech? = null
    private var isTtsInitialized = false

    private val _completedLessons = MutableStateFlow<Set<Int>>(emptySet())
    val completedLessons = _completedLessons.asStateFlow()

    init {
        // Initialize TextToSpeech Safely
        try {
            tts = TextToSpeech(application, this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        
        // Check and pre-populate database on start if empty
        viewModelScope.launch {
            repository.checkAndPrepopulate()
            updateStreakIfNeeded()
            loadCompletedLessons()
        }
    }

    private fun loadCompletedLessons() {
        val sp = getApplication<Application>().getSharedPreferences("playlist_lessons", android.content.Context.MODE_PRIVATE)
        val completed = (1..13).filter { sp.getBoolean("lesson_completed_$it", false) }.toSet()
        _completedLessons.value = completed
    }

    fun toggleLessonCompleted(number: Int) {
        val sp = getApplication<Application>().getSharedPreferences("playlist_lessons", android.content.Context.MODE_PRIVATE)
        val current = sp.getBoolean("lesson_completed_$number", false)
        sp.edit().putBoolean("lesson_completed_$number", !current).apply()
        
        val updatedSet = if (current) {
            _completedLessons.value - number
        } else {
            _completedLessons.value + number
        }
        _completedLessons.value = updatedSet

        // Grant 30 XP when marked completed, otherwise deduct 30 XP if unmarked task
        grantXPAndProgress(!current, 30)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.GERMAN)
            if (result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED) {
                isTtsInitialized = true
            }
        }
    }

    fun speak(text: String) {
        if (isTtsInitialized && tts != null) {
            val cleanText = text.replace(Regex("[^a-zA-ZäöüÄÖÜß\\s]"), "")
            tts?.speak(cleanText, TextToSpeech.QUEUE_FLUSH, null, "GermanSpeech")
        }
    }

    override fun onCleared() {
        super.onCleared()
        tts?.stop()
        tts?.shutdown()
    }

    // --- CODES FOR STATE MANAGEMENT ---
    val categories: StateFlow<List<Category>> = repository.categories
        .map { list -> list.distinctBy { it.name } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val profile: StateFlow<UserProfile> = repository.userProfile
        .map { it ?: UserProfile() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserProfile())

    val mistakes: StateFlow<List<ReviewMistake>> = repository.mistakes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val savedResponses: StateFlow<List<SavedResponse>> = repository.savedResponses
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val dailyChallenges: StateFlow<List<DailyChallenge>> = repository.dailyChallenges
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _chatHistory = MutableStateFlow<List<Content>>(emptyList())
    val chatHistory = _chatHistory.asStateFlow()
    
    private val _isChatLoading = MutableStateFlow(false)
    val isChatLoading = _isChatLoading.asStateFlow()

    fun initChatIfEmpty() {
        if (_chatHistory.value.isEmpty() && !_isChatLoading.value) {
            viewModelScope.launch {
                _isChatLoading.value = true
                val responseText = GeminiClient.chatWithTutor(listOf(Content(parts = listOf(Part(text = "Start the session now. Greet me and show the menu as described in your prompt.")), role = "user")))
                _chatHistory.value = listOf(Content(parts = listOf(Part(text = responseText)), role = "model"))
                _isChatLoading.value = false
            }
        }
    }

    fun sendChatMessage(message: String) {
        if (message.isBlank()) return
        viewModelScope.launch {
            _isChatLoading.value = true
            val userMsg = Content(parts = listOf(Part(text = message)), role = "user")
            val currentList = _chatHistory.value.toMutableList()
            currentList.add(userMsg)
            _chatHistory.value = currentList
            
            val responseText = GeminiClient.chatWithTutor(currentList)
            val modelMsg = Content(parts = listOf(Part(text = responseText)), role = "model")
            
            currentList.add(modelMsg)
            _chatHistory.value = currentList.toList()
            _isChatLoading.value = false
        }
    }
    
    fun clearChat() {
        _chatHistory.value = emptyList()
        initChatIfEmpty()
    }

    // Filter properties
    private val _selectedLevelFilter = MutableStateFlow<String>("All") // "All", "A1", "A2", "B1"
    val selectedLevelFilter = _selectedLevelFilter.asStateFlow()

    private val _selectedCategoryFilter = MutableStateFlow<String>("All") // Name or "All"
    val selectedCategoryFilter = _selectedCategoryFilter.asStateFlow()

    // PRACTICE CONFIGURATION
    private val _practiceLimit = MutableStateFlow<Int>(0) // 0 means all
    val practiceLimit = _practiceLimit.asStateFlow()

    private val _practiceRandomize = MutableStateFlow<Boolean>(false)
    val practiceRandomize = _practiceRandomize.asStateFlow()
    
    fun setPracticeConfig(limit: Int, randomize: Boolean) {
        _practiceLimit.value = limit
        _practiceRandomize.value = randomize
        resetIndices()
    }

    // Filters content flow based on selected difficulty level and categories
    val vocabularyList: StateFlow<List<VocabularyItem>> = combine(
        repository.vocabulary,
        _selectedLevelFilter,
        _selectedCategoryFilter,
        _practiceLimit,
        _practiceRandomize
    ) { list, level, category, limit, randomize ->
        var filteredList = list.filter { item ->
            (level == "All" || item.difficultyLevel == level) &&
            (category == "All" || item.category == category)
        }
        if (randomize) {
            filteredList = filteredList.shuffled()
        }
        if (limit > 0) {
            filteredList = filteredList.take(limit)
        }
        filteredList
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val sentenceList: StateFlow<List<SentenceItem>> = combine(
        repository.sentences,
        _selectedLevelFilter,
        _selectedCategoryFilter,
        _practiceLimit,
        _practiceRandomize
    ) { list, level, category, limit, randomize ->
        var filteredList = list.filter { item ->
            (level == "All" || item.difficultyLevel == level) &&
            (category == "All" || item.category == category)
        }
        if (randomize) {
            filteredList = filteredList.shuffled()
        }
        if (limit > 0) {
            filteredList = filteredList.take(limit)
        }
        filteredList
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val grammarList: StateFlow<List<GrammarItem>> = combine(
        repository.grammar,
        _selectedLevelFilter,
        _selectedCategoryFilter,
        _practiceLimit,
        _practiceRandomize
    ) { list, level, category, limit, randomize ->
        var filteredList = list.filter { item ->
            (level == "All" || item.difficultyLevel == level) &&
            (category == "All" || item.category == category)
        }
        if (randomize) {
            filteredList = filteredList.shuffled()
        }
        if (limit > 0) {
            filteredList = filteredList.take(limit)
        }
        filteredList
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // ACTIVE SESSION LEARNING STATE
    private val _currentVocabIndex = MutableStateFlow(0)
    val currentVocabIndex = _currentVocabIndex.asStateFlow()

    private val _currentSentenceIndex = MutableStateFlow(0)
    val currentSentenceIndex = _currentSentenceIndex.asStateFlow()

    private val _currentGrammarIndex = MutableStateFlow(0)
    val currentGrammarIndex = _currentGrammarIndex.asStateFlow()

    // User Text input state
    val userTranslationInput = MutableStateFlow("")

    // Evaluation feedback states
    private val _evaluationState = MutableStateFlow<Boolean?>(null) // null: not evaluated, true: correct, false: wrong
    val evaluationState = _evaluationState.asStateFlow()
    
    val aiFeedback = MutableStateFlow<String?>(null)

    // Search and search results state
    val searchKeyword = MutableStateFlow("")
    val searchResultsVocabulary: StateFlow<List<VocabularyItem>> = combine(
        repository.vocabulary,
        searchKeyword
    ) { list, query ->
        if (query.isBlank()) emptyList()
        else list.filter { it.bengaliWord.contains(query, ignoreCase = true) || it.germanTranslation.contains(query, ignoreCase = true) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val searchResultsSentences: StateFlow<List<SentenceItem>> = combine(
        repository.sentences,
        searchKeyword
    ) { list, query ->
        if (query.isBlank()) emptyList()
        else list.filter { it.bengaliSentence.contains(query, ignoreCase = true) || it.germanTranslation.contains(query, ignoreCase = true) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // STREAK MANAGEMENT LOGGER
    private fun getTodayDateString(): String {
        val sdf = SimpleDateFormat("yyyy-MM-DD", Locale.getDefault())
        return sdf.format(Date())
    }

    private suspend fun updateStreakIfNeeded() {
        val user = repository.getUserProfileDirect() ?: UserProfile()
        val today = getTodayDateString()
        if (user.lastActiveDate != today) {
            val sdf = SimpleDateFormat("yyyy-MM-DD", Locale.getDefault())
            var newStreak = user.dailyStreak
            if (user.lastActiveDate.isNotEmpty()) {
                try {
                    val lastDate = sdf.parse(user.lastActiveDate)
                    val currentDate = sdf.parse(today)
                    if (currentDate != null && lastDate != null) {
                        val diff = currentDate.time - lastDate.time
                        val diffDays = diff / (24 * 60 * 60 * 1000)
                        if (diffDays == 1L) {
                            newStreak += 1
                        } else if (diffDays > 1L) {
                            newStreak = 1
                        }
                    }
                } catch (e: Exception) {
                    newStreak = 1
                }
            } else {
                newStreak = 1
            }
            repository.insertUserProfile(user.copy(lastActiveDate = today, dailyStreak = newStreak))
        }
    }

    // UPDATE PROGRESS PROFILE ON SUCCESS/FAILURE
    private fun grantXPAndProgress(correct: Boolean, xpEarned: Int) {
        viewModelScope.launch {
            val user = repository.getUserProfileDirect() ?: UserProfile()
            val totalExercises = user.completedExercises + 1
            val newCorrect = if (correct) user.correctAnswers + 1 else user.correctAnswers
            val newXp = if (correct) user.xpPoints + xpEarned else user.xpPoints
            val acc = (newCorrect.toFloat() / totalExercises.toFloat()) * 100f
            
            // Derive level from XP
            val newLvl = when {
                newXp >= 400 -> "B1"
                newXp >= 150 -> "A2"
                else -> "A1"
            }

            val today = getTodayDateString()
            repository.insertUserProfile(
                user.copy(
                    completedExercises = totalExercises,
                    correctAnswers = newCorrect,
                    accuracyPercentage = acc,
                    xpPoints = newXp,
                    learningLevel = newLvl,
                    lastActiveDate = today
                )
            )
        }
    }

    // ACTIONS: FILTERS
    fun setLevelFilter(lvl: String) {
        _selectedLevelFilter.value = lvl
        resetIndices()
    }

    fun setCategoryFilter(cat: String) {
        _selectedCategoryFilter.value = cat
        resetIndices()
    }

    fun resetIndices() {
        _currentVocabIndex.value = 0
        _currentSentenceIndex.value = 0
        _currentGrammarIndex.value = 0
        _evaluationState.value = null
        aiFeedback.value = null
        userTranslationInput.value = ""
    }

    // EVALUATE ANSWERS (Flexible Punctuation & Whitespace check)
    fun checkVocabularyAnswer(item: VocabularyItem) {
        val userAns = userTranslationInput.value.trim().lowercase().replace(Regex("[.\\-?,!]+"), "")
        val expectedAns = item.germanTranslation.trim().lowercase().replace(Regex("[.\\-?,!]+"), "")

        val isMatch = userAns == expectedAns
        _evaluationState.value = isMatch

        grantXPAndProgress(isMatch, 10)

        if (!isMatch) {
            // Log mistake
            viewModelScope.launch {
                repository.insertMistake(
                    ReviewMistake(
                        contentType = "vocab",
                        contentId = item.id,
                        bengaliText = item.bengaliWord,
                        expectedGerman = item.germanTranslation,
                        userGermanEntered = userTranslationInput.value
                    )
                )
            }
        }
    }

    fun checkSentenceAnswer(item: SentenceItem) {
        val rawUserInput = userTranslationInput.value
        val userAns = rawUserInput.trim().lowercase().replace(Regex("[.\\-?,!]+"), "")
        val expectedAns = item.germanTranslation.trim().lowercase().replace(Regex("[.\\-?,!]+"), "")

        val isMatch = userAns == expectedAns
        _evaluationState.value = isMatch
        aiFeedback.value = "Analyzing with AI..."

        grantXPAndProgress(isMatch, 20)
        
        viewModelScope.launch {
            val feedback = GeminiClient.checkSentence(rawUserInput, item.germanTranslation)
            aiFeedback.value = feedback
        }

        if (!isMatch) {
            // Log mistake
            viewModelScope.launch {
                repository.insertMistake(
                    ReviewMistake(
                        contentType = "sentence",
                        contentId = item.id,
                        bengaliText = item.bengaliSentence,
                        expectedGerman = item.germanTranslation,
                        userGermanEntered = userTranslationInput.value
                    )
                )
            }
        }
    }

    fun checkGrammarAnswer(item: GrammarItem) {
        val userAns = userTranslationInput.value.trim().lowercase().replace(Regex("[.\\-?,!]+"), "")
        val expectedAns = item.correctAnswer.trim().lowercase().replace(Regex("[.\\-?,!]+"), "")

        val isMatch = userAns == expectedAns
        _evaluationState.value = isMatch

        grantXPAndProgress(isMatch, 15)

        if (!isMatch) {
            // Log mistake
            viewModelScope.launch {
                repository.insertMistake(
                    ReviewMistake(
                        contentType = "grammar",
                        contentId = item.id,
                        bengaliText = item.question,
                        expectedGerman = item.correctAnswer,
                        userGermanEntered = userTranslationInput.value
                    )
                )
            }
        }
    }

    fun clearEvaluation() {
        _evaluationState.value = null
        aiFeedback.value = null
        userTranslationInput.value = ""
    }

    fun nextVocabulary() {
        val total = vocabularyList.value.size
        if (total > 0) {
            _currentVocabIndex.value = (_currentVocabIndex.value + 1) % total
        }
        clearEvaluation()
    }

    fun nextSentence() {
        val total = sentenceList.value.size
        if (total > 0) {
            _currentSentenceIndex.value = (_currentSentenceIndex.value + 1) % total
        }
        clearEvaluation()
    }

    fun nextGrammar() {
        val total = grammarList.value.size
        if (total > 0) {
            _currentGrammarIndex.value = (_currentGrammarIndex.value + 1) % total
        }
        clearEvaluation()
    }

    // ACTIONS: FAVORITES TOGGLING
    fun toggleVocabularyFavorite(item: VocabularyItem) {
        viewModelScope.launch {
            repository.updateVocabulary(item.copy(isFavorite = !item.isFavorite))
        }
    }

    fun toggleSentenceFavorite(item: SentenceItem) {
        viewModelScope.launch {
            repository.updateSentence(item.copy(isFavorite = !item.isFavorite))
        }
    }

    fun toggleGrammarFavorite(item: GrammarItem) {
        viewModelScope.launch {
            repository.updateGrammar(item.copy(isFavorite = !item.isFavorite))
        }
    }

    fun getFavoriteVocabulary(): Flow<List<VocabularyItem>> = repository.getFavoriteVocabulary()
    fun getFavoriteSentences(): Flow<List<SentenceItem>> = repository.getFavoriteSentences()

    // SAVED RESPONSE: DAILY CHALLENGES
    fun submitDailyChallenge(challengeId: Int, responseText: String) {
        val today = getTodayDateString()
        viewModelScope.launch {
            repository.insertSavedResponse(
                SavedResponse(
                    challengeId = challengeId,
                    userResponseGerman = responseText,
                    dateString = today
                )
            )
            // Daily challenge yields 30 XP
            grantXPAndProgress(true, 30)
        }
    }

    // CRUD: CUSTOM CONTENT MANAGERS
    fun addCategory(name: String, type: String) {
        viewModelScope.launch {
            repository.insertCategory(Category(name = name.trim(), type = type))
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            repository.deleteCategory(category)
        }
    }

    fun addVocabulary(category: String, bengaliWord: String, germanTranslation: String, lvl: String) {
        viewModelScope.launch {
            repository.insertVocabulary(
                VocabularyItem(
                    category = category,
                    bengaliWord = bengaliWord.trim(),
                    germanTranslation = germanTranslation.trim(),
                    difficultyLevel = lvl
                )
            )
        }
    }

    fun editVocabulary(item: VocabularyItem) {
        viewModelScope.launch {
            repository.updateVocabulary(item)
        }
    }

    fun deleteVocabulary(item: VocabularyItem) {
        viewModelScope.launch {
            repository.deleteVocabulary(item)
        }
    }

    fun addSentence(category: String, bengaliSentence: String, germanTranslation: String, lvl: String) {
        viewModelScope.launch {
            repository.insertSentence(
                SentenceItem(
                    category = category,
                    bengaliSentence = bengaliSentence.trim(),
                    germanTranslation = germanTranslation.trim(),
                    difficultyLevel = lvl
                )
            )
        }
    }

    fun editSentence(item: SentenceItem) {
        viewModelScope.launch {
            repository.updateSentence(item)
        }
    }

    fun deleteSentence(item: SentenceItem) {
        viewModelScope.launch {
            repository.deleteSentence(item)
        }
    }

    fun addGrammar(category: String, question: String, correctAnswer: String, explanation: String, lvl: String) {
        viewModelScope.launch {
            repository.insertGrammar(
                GrammarItem(
                    category = category,
                    question = question.trim(),
                    correctAnswer = correctAnswer.trim(),
                    explanation = explanation.trim(),
                    difficultyLevel = lvl
                )
            )
        }
    }

    fun editGrammar(item: GrammarItem) {
        viewModelScope.launch {
            repository.updateGrammar(item)
        }
    }

    fun deleteGrammar(item: GrammarItem) {
        viewModelScope.launch {
            repository.deleteGrammar(item)
        }
    }

    fun deleteMistake(id: Int) {
        viewModelScope.launch {
            repository.deleteMistake(id)
        }
    }

    fun clearMistakes() {
        viewModelScope.launch {
            repository.clearAllMistakes()
        }
    }

    // SCHEDULE REVISION
    fun scheduleRevision(type: String, id: Int, delayMs: Long, quality: Int = -1) {
        viewModelScope.launch {
            val nowMs = System.currentTimeMillis()

            suspend fun calculateSrs(
                currentInterval: Float,
                currentEase: Float,
                currentReps: Int,
                update: suspend (Long, Float, Float, Int) -> Unit
            ) {
                if (quality == -1) {
                    update(nowMs + delayMs, currentInterval, currentEase, currentReps)
                    return
                }

                var nextInterval = currentInterval
                var nextReps = currentReps
                var nextEase = currentEase + (0.1f - (5 - quality) * (0.08f + (5 - quality) * 0.02f))
                if (nextEase < 1.3f) nextEase = 1.3f

                if (quality >= 3) {
                    if (currentReps == 0) {
                        nextInterval = 1f
                    } else if (currentReps == 1) {
                        nextInterval = 6f
                    } else {
                        nextInterval = currentInterval * currentEase
                    }
                    nextReps++
                } else {
                    nextReps = 0
                    nextInterval = 1f
                }

                val autoDelayMs = (nextInterval * 24L * 60L * 60L * 1000L).toLong()
                update(nowMs + autoDelayMs, nextInterval, nextEase, nextReps)
            }

            when (type) {
                "vocab" -> {
                    val item = vocabularyList.value.find { it.id == id }
                    item?.let {
                        calculateSrs(it.srsIntervalDays, it.srsEaseFactor, it.srsRepetitions) { nMs, nInt, nEase, nReps ->
                            repository.updateVocabulary(it.copy(
                                nextRevisionTimeMs = nMs, srsIntervalDays = nInt, srsEaseFactor = nEase, srsRepetitions = nReps
                            ))
                        }
                    }
                }
                "sentence" -> {
                    val item = sentenceList.value.find { it.id == id }
                    item?.let {
                        calculateSrs(it.srsIntervalDays, it.srsEaseFactor, it.srsRepetitions) { nMs, nInt, nEase, nReps ->
                            repository.updateSentence(it.copy(
                                nextRevisionTimeMs = nMs, srsIntervalDays = nInt, srsEaseFactor = nEase, srsRepetitions = nReps
                            ))
                        }
                    }
                }
                "grammar" -> {
                    val item = grammarList.value.find { it.id == id }
                    item?.let {
                        calculateSrs(it.srsIntervalDays, it.srsEaseFactor, it.srsRepetitions) { nMs, nInt, nEase, nReps ->
                            repository.updateGrammar(it.copy(
                                nextRevisionTimeMs = nMs, srsIntervalDays = nInt, srsEaseFactor = nEase, srsRepetitions = nReps
                            ))
                        }
                    }
                }
            }
        }
    }

    val dueRevisionsCount: StateFlow<Int> = combine(
        repository.vocabulary,
        repository.sentences,
        repository.grammar
    ) { v, s, g ->
        val now = System.currentTimeMillis()
        v.count { it.nextRevisionTimeMs in 1..now } +
        s.count { it.nextRevisionTimeMs in 1..now } +
        g.count { it.nextRevisionTimeMs in 1..now }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)
}

// Factory Provider
class LanguageViewModelFactory(
    private val application: Application,
    private val repository: LanguageRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LanguageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LanguageViewModel(application, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
