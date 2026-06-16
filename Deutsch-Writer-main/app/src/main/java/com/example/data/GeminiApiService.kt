package com.example.data

import com.example.BuildConfig
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

@JsonClass(generateAdapter = true)
data class GenerateContentRequest(
    val systemInstruction: Content? = null,
    val contents: List<Content>
)

@JsonClass(generateAdapter = true)
data class Content(
    val parts: List<Part>,
    val role: String? = null
)

@JsonClass(generateAdapter = true)
data class Part(
    val text: String
)

@JsonClass(generateAdapter = true)
data class GenerateContentResponse(
    val candidates: List<Candidate>?
)

@JsonClass(generateAdapter = true)
data class Candidate(
    val content: Content?
)

interface GeminiApiService {
    @POST("v1beta/models/gemini-3.5-flash:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Body request: GenerateContentRequest
    ): GenerateContentResponse
}

object GeminiClient {
    private const val BASE_URL = "https://generativelanguage.googleapis.com/"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val service: GeminiApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(GeminiApiService::class.java)
    }

    suspend fun checkSentence(userSentence: String, expectedSentence: String): String = withContext(Dispatchers.IO) {
        val prompt = """
            You are a German Teacher. A student provided a translation.
            Expected translation: "$expectedSentence"
            Student's translation: "$userSentence"
            
            Provide instant feedback in exactly 1-2 brief sentences in English or Bengali. Tell them what grammatical or vocab error they made, if any, and encourage them. Don't be too strict on punctuation.
        """.trimIndent()
        
        try {
            val request = GenerateContentRequest(
                contents = listOf(Content(parts = listOf(Part(text = prompt))))
            )
            val response = service.generateContent(BuildConfig.GEMINI_API_KEY, request)
            response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: "Good attempt!"
        } catch (e: Exception) {
            "API error: Could not fetch AI feedback right now. (Check Settings for API Key!)"
        }
    }
    
    suspend fun chatWithTutor(history: List<Content>): String = withContext(Dispatchers.IO) {
        try {
            val systemInstruction = Content(parts = listOf(Part(text = TutorPrompt.SYSTEM_PROMPT)))
            val request = GenerateContentRequest(
                systemInstruction = systemInstruction,
                contents = history
            )
            val response = service.generateContent(BuildConfig.GEMINI_API_KEY, request)
            response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: "I am sorry, I couldn't generate a response."
        } catch (e: Exception) {
            "API error: ${e.message}"
        }
    }
}
