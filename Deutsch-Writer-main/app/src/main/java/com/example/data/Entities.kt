package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

interface ReviewItem {
    val id: Int
    val nextRevisionTimeMs: Long
    val srsIntervalDays: Float
    val srsEaseFactor: Float
    val srsRepetitions: Int
}

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val type: String // "Vocabulary", "Sentence", "Grammar"
)

@Entity(tableName = "vocabulary")
data class VocabularyItem(
    @PrimaryKey(autoGenerate = true) override val id: Int = 0,
    val category: String,
    val bengaliWord: String,
    val germanTranslation: String,
    val isFavorite: Boolean = false,
    val difficultyLevel: String = "A1",
    override val nextRevisionTimeMs: Long = 0L,
    override val srsIntervalDays: Float = 0f,
    override val srsEaseFactor: Float = 2.5f,
    override val srsRepetitions: Int = 0
) : ReviewItem

@Entity(tableName = "sentences")
data class SentenceItem(
    @PrimaryKey(autoGenerate = true) override val id: Int = 0,
    val category: String,
    val bengaliSentence: String,
    val germanTranslation: String,
    val isFavorite: Boolean = false,
    val difficultyLevel: String = "A1",
    override val nextRevisionTimeMs: Long = 0L,
    override val srsIntervalDays: Float = 0f,
    override val srsEaseFactor: Float = 2.5f,
    override val srsRepetitions: Int = 0
) : ReviewItem

@Entity(tableName = "grammar")
data class GrammarItem(
    @PrimaryKey(autoGenerate = true) override val id: Int = 0,
    val category: String,
    val question: String,
    val correctAnswer: String,
    val explanation: String = "",
    val isFavorite: Boolean = false,
    val difficultyLevel: String = "A1",
    override val nextRevisionTimeMs: Long = 0L,
    override val srsIntervalDays: Float = 0f,
    override val srsEaseFactor: Float = 2.5f,
    override val srsRepetitions: Int = 0
) : ReviewItem


@Entity(tableName = "daily_challenges")
data class DailyChallenge(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val promptBengali: String,
    val vocabularyHints: String, // Comma separated hints, e.g., "আজ (heute), শান্ত (ruhig)"
    val dayNumber: Int, // Day indexing for progression
    val dateString: String // Optional date binding YYYY-MM-DD
)

@Entity(tableName = "saved_responses")
data class SavedResponse(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val challengeId: Int,
    val userResponseGerman: String,
    val dateString: String, // YYYY-MM-DD
    val feedbackJson: String = "" // Optional notes or parsing
)

@Entity(tableName = "mistakes")
data class ReviewMistake(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val contentType: String, // "vocab", "sentence", "grammar"
    val contentId: Int, // References the source entity id
    val bengaliText: String,
    val expectedGerman: String,
    val userGermanEntered: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey val id: Int = 1, // Singleton profile
    val completedExercises: Int = 0,
    val correctAnswers: Int = 0,
    val accuracyPercentage: Float = 0f,
    val dailyStreak: Int = 0,
    val xpPoints: Int = 0,
    val learningLevel: String = "A1", // "A1", "A2", "B1"
    val lastActiveDate: String = "" // "YYYY-MM-DD"
)
