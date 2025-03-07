package hu.ait.mydreamjournalapp.dream

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.SafetySetting
import com.google.ai.client.generativeai.type.generationConfig

val model = GenerativeModel(
    modelName = "gemini-1.5-flash",
    apiKey = "AIzaSyAbuFhlaFVjO_a99Aj31UrljtxXt2Fbmtw",
    generationConfig = generationConfig {
        temperature = 0.15f
        topK = 32
        topP = 1f
        maxOutputTokens = 4096
    },
    safetySettings = listOf(
        SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.MEDIUM_AND_ABOVE),
        SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.MEDIUM_AND_ABOVE),
        SafetySetting(HarmCategory.SEXUALLY_EXPLICIT, BlockThreshold.MEDIUM_AND_ABOVE),
        SafetySetting(HarmCategory.DANGEROUS_CONTENT, BlockThreshold.MEDIUM_AND_ABOVE),
    )
)

