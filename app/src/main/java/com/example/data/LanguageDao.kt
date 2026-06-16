package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface LanguageDao {

    // --- CODES FOR CATEGORIES ---
    @Query("SELECT * FROM categories ORDER BY name ASC")
    fun getAllCategoriesFlow(): Flow<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category): Long

    @Delete
    suspend fun deleteCategory(category: Category)

    // --- CODES FOR VOCABULARY ---
    @Query("SELECT * FROM vocabulary")
    fun getAllVocabularyFlow(): Flow<List<VocabularyItem>>

    @Query("SELECT COUNT(*) FROM vocabulary")
    suspend fun getVocabularyCountDirect(): Int

    @Query("SELECT * FROM vocabulary WHERE category = :categoryName")
    fun getVocabularyByCategoryFlow(categoryName: String): Flow<List<VocabularyItem>>

    @Query("SELECT * FROM vocabulary WHERE isFavorite = 1")
    fun getFavoriteVocabularyFlow(): Flow<List<VocabularyItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVocabulary(item: VocabularyItem): Long

    @Update
    suspend fun updateVocabulary(item: VocabularyItem)

    @Delete
    suspend fun deleteVocabulary(item: VocabularyItem)

    // --- CODES FOR SENTENCES ---
    @Query("SELECT * FROM sentences")
    fun getAllSentencesFlow(): Flow<List<SentenceItem>>

    @Query("SELECT * FROM sentences WHERE category = :categoryName")
    fun getSentencesByCategoryFlow(categoryName: String): Flow<List<SentenceItem>>

    @Query("SELECT * FROM sentences WHERE isFavorite = 1")
    fun getFavoriteSentencesFlow(): Flow<List<SentenceItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSentence(item: SentenceItem): Long

    @Update
    suspend fun updateSentence(item: SentenceItem)

    @Delete
    suspend fun deleteSentence(item: SentenceItem)

    // --- CODES FOR GRAMMAR ---
    @Query("SELECT * FROM grammar")
    fun getAllGrammarFlow(): Flow<List<GrammarItem>>

    @Query("SELECT * FROM grammar WHERE category = :categoryName")
    fun getGrammarByCategoryFlow(categoryName: String): Flow<List<GrammarItem>>

    @Query("SELECT * FROM grammar WHERE isFavorite = 1")
    fun getFavoriteGrammarFlow(): Flow<List<GrammarItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGrammar(item: GrammarItem): Long

    @Update
    suspend fun updateGrammar(item: GrammarItem)

    @Delete
    suspend fun deleteGrammar(item: GrammarItem)

    // --- CODES FOR DAILY CHALLENGES ---
    @Query("SELECT * FROM daily_challenges ORDER BY dayNumber ASC")
    fun getAllDailyChallengesFlow(): Flow<List<DailyChallenge>>

    @Query("SELECT * FROM daily_challenges WHERE dayNumber = :dayNum LIMIT 1")
    suspend fun getChallengeByDay(dayNum: Int): DailyChallenge?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyChallenge(challenge: DailyChallenge): Long

    // --- CODES FOR SAVED RESPONSES ---
    @Query("SELECT * FROM saved_responses")
    fun getAllSavedResponsesFlow(): Flow<List<SavedResponse>>

    @Query("SELECT * FROM saved_responses WHERE challengeId = :challengeId")
    fun getResponsesForChallengeFlow(challengeId: Int): Flow<List<SavedResponse>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedResponse(response: SavedResponse): Long

    // --- CODES FOR REVIEW MISTAKES ---
    @Query("SELECT * FROM mistakes ORDER BY timestamp DESC")
    fun getAllMistakesFlow(): Flow<List<ReviewMistake>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMistake(mistake: ReviewMistake): Long

    @Query("DELETE FROM mistakes WHERE id = :id")
    suspend fun deleteMistakeById(id: Int)

    @Query("DELETE FROM mistakes")
    suspend fun clearAllMistakes()

    // --- CODES FOR USER PROFILE ---
    @Query("SELECT * FROM user_profile WHERE id = 1 LIMIT 1")
    fun getUserProfileFlow(): Flow<UserProfile?>

    @Query("SELECT * FROM user_profile WHERE id = 1 LIMIT 1")
    suspend fun getUserProfileDirect(): UserProfile?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserProfile(profile: UserProfile): Long
}
