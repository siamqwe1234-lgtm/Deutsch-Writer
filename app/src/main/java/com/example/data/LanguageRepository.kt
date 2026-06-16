package com.example.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers

class LanguageRepository(private val languageDao: LanguageDao) {

    // FLOWS
    val categories: Flow<List<Category>> = languageDao.getAllCategoriesFlow()
    val vocabulary: Flow<List<VocabularyItem>> = languageDao.getAllVocabularyFlow()
    val sentences: Flow<List<SentenceItem>> = languageDao.getAllSentencesFlow()
    val grammar: Flow<List<GrammarItem>> = languageDao.getAllGrammarFlow()
    val dailyChallenges: Flow<List<DailyChallenge>> = languageDao.getAllDailyChallengesFlow()
    val savedResponses: Flow<List<SavedResponse>> = languageDao.getAllSavedResponsesFlow()
    val mistakes: Flow<List<ReviewMistake>> = languageDao.getAllMistakesFlow()
    val userProfile: Flow<UserProfile?> = languageDao.getUserProfileFlow()

    fun getVocabularyByCategory(cat: String) = languageDao.getVocabularyByCategoryFlow(cat)
    fun getFavoriteVocabulary() = languageDao.getFavoriteVocabularyFlow()

    fun getSentencesByCategory(cat: String) = languageDao.getSentencesByCategoryFlow(cat)
    fun getFavoriteSentences() = languageDao.getFavoriteSentencesFlow()

    fun getGrammarByCategory(cat: String) = languageDao.getGrammarByCategoryFlow(cat)
    fun getFavoriteGrammar() = languageDao.getFavoriteGrammarFlow()

    fun getSavedResponsesForChallenge(challengeId: Int) = languageDao.getResponsesForChallengeFlow(challengeId)

    // WRITE METHODS
    suspend fun insertCategory(category: Category) = withContext(Dispatchers.IO) {
        languageDao.insertCategory(category)
    }

    suspend fun deleteCategory(category: Category) = withContext(Dispatchers.IO) {
        languageDao.deleteCategory(category)
    }

    suspend fun insertVocabulary(item: VocabularyItem) = withContext(Dispatchers.IO) {
        languageDao.insertVocabulary(item)
    }

    suspend fun updateVocabulary(item: VocabularyItem) = withContext(Dispatchers.IO) {
        languageDao.updateVocabulary(item)
    }

    suspend fun deleteVocabulary(item: VocabularyItem) = withContext(Dispatchers.IO) {
        languageDao.deleteVocabulary(item)
    }

    suspend fun insertSentence(item: SentenceItem) = withContext(Dispatchers.IO) {
        languageDao.insertSentence(item)
    }

    suspend fun updateSentence(item: SentenceItem) = withContext(Dispatchers.IO) {
        languageDao.updateSentence(item)
    }

    suspend fun deleteSentence(item: SentenceItem) = withContext(Dispatchers.IO) {
        languageDao.deleteSentence(item)
    }

    suspend fun insertGrammar(item: GrammarItem) = withContext(Dispatchers.IO) {
        languageDao.insertGrammar(item)
    }

    suspend fun updateGrammar(item: GrammarItem) = withContext(Dispatchers.IO) {
        languageDao.updateGrammar(item)
    }

    suspend fun deleteGrammar(item: GrammarItem) = withContext(Dispatchers.IO) {
        languageDao.deleteGrammar(item)
    }

    suspend fun insertSavedResponse(response: SavedResponse) = withContext(Dispatchers.IO) {
        languageDao.insertSavedResponse(response)
    }

    suspend fun insertMistake(mistake: ReviewMistake) = withContext(Dispatchers.IO) {
        languageDao.insertMistake(mistake)
    }

    suspend fun deleteMistake(id: Int) = withContext(Dispatchers.IO) {
        languageDao.deleteMistakeById(id)
    }

    suspend fun clearAllMistakes() = withContext(Dispatchers.IO) {
        languageDao.clearAllMistakes()
    }

    suspend fun insertUserProfile(profile: UserProfile) = withContext(Dispatchers.IO) {
        languageDao.insertUserProfile(profile)
    }

    // UTILITIES
    suspend fun getChallengeByDay(day: Int): DailyChallenge? = withContext(Dispatchers.IO) {
        languageDao.getChallengeByDay(day)
    }

    suspend fun getUserProfileDirect(): UserProfile? = withContext(Dispatchers.IO) {
        languageDao.getUserProfileDirect()
    }

    // CHECK & PREPOPULATE ON START
    suspend fun checkAndPrepopulate() = withContext(Dispatchers.IO) {
        val existingCats = categories.firstOrNull()
        if (existingCats.isNullOrEmpty()) {
            // Populate standard categories
            val logCategories = LectureLogData.lessons.flatMap { 
                listOf(
                    Category(name = "Lecture ${it.videoNumber}", type = "Sentence"),
                    Category(name = "Lecture ${it.videoNumber}", type = "Grammar")
                )
            }
            
            val defaultCategories = listOf(
                Category(name = "Family", type = "Vocabulary"),
                Category(name = "Food", type = "Vocabulary"),
                Category(name = "Travel", type = "Vocabulary"),
                Category(name = "Work", type = "Vocabulary"),
                Category(name = "Daily Life", type = "Vocabulary"),
                Category(name = "A1 Vocabulary", type = "Vocabulary"),
                Category(name = "A2 Vocabulary", type = "Vocabulary"),
                Category(name = "B1 Vocabulary", type = "Vocabulary"),

                Category(name = "Family", type = "Sentence"),
                Category(name = "Food", type = "Sentence"),
                Category(name = "Travel", type = "Sentence"),
                Category(name = "Work", type = "Sentence"),
                Category(name = "Daily Life", type = "Sentence"),

                Category(name = "Verb Practice", type = "Grammar"),
                Category(name = "Articles", type = "Grammar"),
                Category(name = "Adjectives", type = "Grammar")
            ) + logCategories
            
            val combinedCats = defaultCategories + ExtraLessonData.sentences.map { Category(name = it.category, type = "Sentence") }.distinctBy { it.name } + ExtraLessonData.grammarTopics.map { Category(name = it.category, type = "Grammar") }.distinctBy { it.name }
            
            for (cat in combinedCats.distinctBy { it.name + it.type }) {
                languageDao.insertCategory(cat)
            }

            val logSentences = LectureLogData.lessons.flatMap { log ->
                log.sentenceStructures.map {
                    SentenceItem(
                        category = "Lecture ${log.videoNumber}",
                        bengaliSentence = it.bengaliMatch,
                        germanTranslation = it.example,
                        difficultyLevel = log.cefrLevel
                    )
                }
            }

            // Populate Sentences
            val defaultSentences = listOf(
                SentenceItem(category = "Family", bengaliSentence = "আমার একটি ভাই আছে।", germanTranslation = "Ich habe einen Bruder.", difficultyLevel = "A1"),
                SentenceItem(category = "Family", bengaliSentence = "তিনি আমার মা।", germanTranslation = "Sie ist meine Mutter.", difficultyLevel = "A1"),
                SentenceItem(category = "Family", bengaliSentence = "আমরা আমাদের পরিবারকে ভালোবাসি।", germanTranslation = "Wir lieben unsere Familie.", difficultyLevel = "A2"),
                
                SentenceItem(category = "Food", bengaliSentence = "আমি জল পান করছি।", germanTranslation = "Ich trinke Wasser.", difficultyLevel = "A1"),
                SentenceItem(category = "Food", bengaliSentence = "আমি একটি আপেল খাই।", germanTranslation = "Ich esse einen Apfel.", difficultyLevel = "A1"),
                SentenceItem(category = "Food", bengaliSentence = "রুটিটি খুব সুস্বাদু।", germanTranslation = "Das Brot ist sehr lecker.", difficultyLevel = "A2"),
                
                SentenceItem(category = "Travel", bengaliSentence = "খাবার হোটেলটি কোথায়?", germanTranslation = "Wo ist das Hotel?", difficultyLevel = "A1"),
                SentenceItem(category = "Travel", bengaliSentence = "ট্রেনটি সময়মতো আসছে।", germanTranslation = "Der Zug kommt pünktlich.", difficultyLevel = "A2"),
                SentenceItem(category = "Travel", bengaliSentence = "আমি বিমান টিকিট খুঁজছি।", germanTranslation = "Ich suche ein Flugticket.", difficultyLevel = "B1"),

                SentenceItem(category = "Work", bengaliSentence = "আমি অফিসে কাজ করি।", germanTranslation = "Ich arbeite im Büro.", difficultyLevel = "A1"),
                SentenceItem(category = "Work", bengaliSentence = "তিনি একজন ডাক্তার এবং খুব দয়ালু।", germanTranslation = "Er ist Arzt und sehr nett.", difficultyLevel = "A2"),
                SentenceItem(category = "Work", bengaliSentence = "আমার একটি নতুন ল্যাপটপ দরকার।", germanTranslation = "Ich brauche einen neuen Laptop.", difficultyLevel = "B1"),

                SentenceItem(category = "Daily Life", bengaliSentence = "আমি জার্মান শিখছি।", germanTranslation = "Ich lerne Deutsch.", difficultyLevel = "A1"),
                SentenceItem(category = "Daily Life", bengaliSentence = "আপনার নাম কি?", germanTranslation = "Wie heißen Sie?", difficultyLevel = "A1"),
                SentenceItem(category = "Daily Life", bengaliSentence = "আজ আমাদের হাতে সময় কম।", germanTranslation = "Heute haben wir wenig Zeit.", difficultyLevel = "A2")
            ) + logSentences + ExtraLessonData.sentences
            for (sent in defaultSentences) {
                languageDao.insertSentence(sent)
            }

            val logGrammarItems = listOf(
                GrammarItem(category = "Lecture 1", question = "আমি জার্মান শিখি (lerne)", correctAnswer = "Ich lerne Deutsch", explanation = "Ich takes -e suffix", difficultyLevel = "A1"),
                GrammarItem(category = "Lecture 2", question = "তুমি জল পান করো (trinken)", correctAnswer = "Du trinkst Wasser", explanation = "Du takes -st suffix", difficultyLevel = "A1"),
                GrammarItem(category = "Lecture 3", question = "একুশ (21)", correctAnswer = "Einundzwanzig", explanation = "1 und 20", difficultyLevel = "A1"),
                GrammarItem(category = "Lecture 4", question = "আমি ঢাকায় থাকি (wohnen)", correctAnswer = "Ich wohne in Dhaka", explanation = "wohnen takes in", difficultyLevel = "A1"),
                GrammarItem(category = "Lecture 5", question = "আমরা জার্মান শিখতে পারি (können)", correctAnswer = "Wir können Deutsch lernen", explanation = "Modal verb can + infinitive at the end", difficultyLevel = "A1"),
                GrammarItem(category = "Lecture 6", question = "আমরা আগামীকাল জার্মান শিখব (werden)", correctAnswer = "Wir werden morgen Deutsch lernen", explanation = "Future tense werden + infinitive at the end", difficultyLevel = "A1"),
                GrammarItem(category = "Lecture 7", question = "আপনি কোথা থেকে এসেছেন? (kommen)", correctAnswer = "Woher kommen Sie?", explanation = "W-word + Verb + Subject", difficultyLevel = "A1"),
                GrammarItem(category = "Lecture 8", question = "তুমি কি টেনিস খেলো? (spielen)", correctAnswer = "Spielst du Tennis?", explanation = "Verb at position 1 for yes/no question", difficultyLevel = "A1"),
                GrammarItem(category = "Lecture 10", question = "আমি লোকটিকে একটি বল দিচ্ছি (geben)", correctAnswer = "Ich gebe dem Mann einen Ball", explanation = "dem Mann (Dative), einen Ball (Accusative)", difficultyLevel = "A1"),
                GrammarItem(category = "Lecture 11", question = "আমার কাছে আমার কলমটি আছে (haben, Kuli)", correctAnswer = "Ich habe meinen Kuli", explanation = "Accusative masculine possessive", difficultyLevel = "A1"),
                GrammarItem(category = "Lecture 12", question = "সে তার ঘড়িটি ভালোবাসে (lieben, Uhr)", correctAnswer = "Er liebt seine Uhr", explanation = "Accusative feminine possessive", difficultyLevel = "A1")
            )

            // Populate Grammar
            val defaultGrammar = listOf(
                GrammarItem(category = "Verb Practice", question = "আমি যাই (gehen)", correctAnswer = "Ich gehe", explanation = "Conjugation of 'gehen' for first-person singular 'ich' is 'gehe'.", difficultyLevel = "A1"),
                GrammarItem(category = "Verb Practice", question = "তুমি ফুটবল খেলো। (spielen)", correctAnswer = "Du spielst Fußball", explanation = "Conjugation of 'spielen' for 'du' takes the suffix '-st' -> 'spielst'.", difficultyLevel = "A1"),
                GrammarItem(category = "Verb Practice", question = "আমরা জার্মান কথা বলি। (sprechen)", correctAnswer = "Wir sprechen Deutsch", explanation = "Conjugation of 'sprechen' for 'wir' is the infinitive 'sprechen'.", difficultyLevel = "A2"),
                GrammarItem(category = "Verb Practice", question = "সে একটি বই পড়ে। (lesen)", correctAnswer = "Er liest ein Buch", explanation = "Unregelmäßiges Verb 'lesen' turns 'e' into 'ie' for third person 'er' -> 'liest'.", difficultyLevel = "A2"),
                
                GrammarItem(category = "Articles", question = "আপেলটি (The apple - m.)", correctAnswer = "Der Apfel", explanation = "'Apfel' is masculine, nominative article is 'der'.", difficultyLevel = "A1"),
                GrammarItem(category = "Articles", question = "জলটি (The water - n.)", correctAnswer = "Das Wasser", explanation = "'Wasser' is neuter, nominative article is 'das'.", difficultyLevel = "A1"),
                GrammarItem(category = "Articles", question = "বোনটি (The sister - f.)", correctAnswer = "Die Schwester", explanation = "'Schwester' is feminine, nominative article is 'die'.", difficultyLevel = "A1"),
                
                GrammarItem(category = "Adjectives", question = "ছোট বাড়িটি (The small house)", correctAnswer = "Das kleine Haus", explanation = "Weak adjectival ending after definite neuter article 'das' in nominative is '-e' -> 'kleine'.", difficultyLevel = "A2"),
                GrammarItem(category = "Adjectives", question = "একটি বড় গাড়ি (A big car)", correctAnswer = "Ein großes Auto", explanation = "Mixed adjectival ending after indefinite article 'ein' for neuter nominative is '-es' -> 'großes'.", difficultyLevel = "B1")
            ) + logGrammarItems + ExtraLessonData.grammarTopics
            for (gram in defaultGrammar) {
                languageDao.insertGrammar(gram)
            }

            // Populate Daily Challenges
            val defaultChallenges = listOf(
                DailyChallenge(dayNumber = 1, promptBengali = "আজ আপনার দিনটি কেমন ছিল? সংক্ষিপ্তভাবে লিখুন। (How was your day?)", vocabularyHints = "দিনটি (der Tag), ভালো (gut), সুন্দর (schön), কাজ (die Arbeit)", dateString = ""),
                DailyChallenge(dayNumber = 2, promptBengali = "আপনার প্রিয় খাদ্য সম্পর্কে ২-৩টি লাইন জার্মান ভাষায় লিখুন। (Your favorite food)", vocabularyHints = "প্রিয় খাবার (das Lieblingsessen), ভাত (der Reis), সুস্বাদু (lecker), রান্না করা (kochen)", dateString = ""),
                DailyChallenge(dayNumber = 3, promptBengali = "আপনি কেন জার্মান ভাষা শিখছেন? উদ্দেশ্যটি লিখুন। (Why learn German?)", vocabularyHints = "ভাষা (die Sprache), শেখা (lernen), জার্মানি (Deutschland), কাজ (die Arbeit)", dateString = ""),
                DailyChallenge(dayNumber = 4, promptBengali = "আপনার বাড়ি সম্পর্কে কিছু বর্ণনা দিন। (Describe your house)", vocabularyHints = "বাড়ি (das Haus), ছোট (klein), বাগান (der Garten), সুন্দর (gemütlich)", dateString = ""),
                DailyChallenge(dayNumber = 5, promptBengali = "আপনার প্রিয় বন্ধু সম্পর্কে কিছু কথা লিখুন। (Your best friend)", vocabularyHints = "বন্ধু (der Freund), দয়ালু (nett), বিশ্বস্ত (treu), হাসিখুশি (lustig)", dateString = "")
            )
            for (ch in defaultChallenges) {
                languageDao.insertDailyChallenge(ch)
            }
        }

        // Load 298-word structured comprehensive vocabulary list if not already populated
        val vocabCount = languageDao.getVocabularyCountDirect()
        if (vocabCount < 100) {
            val combinedCats = DefaultVocabulary.categories + Category(name = "Everyday Vocabulary", type = "Vocabulary")
            for (cat in combinedCats) {
                languageDao.insertCategory(cat)
            }
            for (vocab in DefaultVocabulary.items) {
                languageDao.insertVocabulary(vocab)
            }
            for (vocab in NewVocabulary.getItems()) {
                languageDao.insertVocabulary(vocab)
            }
        }

        // Initialize Singleton Profile
        if (getUserProfileDirect() == null) {
            languageDao.insertUserProfile(UserProfile(id = 1))
        }
    }
}
