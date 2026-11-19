package com.example.gramaarogya.Screens.report

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gramaarogya.BuildConfig
import com.example.gramaarogya.R
import com.example.gramaarogya.Screens.lang.LanguageManager
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReportViewModel : ViewModel() {

    var summary by mutableStateOf<String?>(null)
        private set
    var isLoading by mutableStateOf(false)
        private set
    var error by mutableStateOf<String?>(null)
        private set

    private fun modelFor(languageCode: String) = GenerativeModel(
        modelName = "gemini-3.5-flash-lite",
        apiKey = BuildConfig.GEMINI_API_KEY,
        systemInstruction = content { text(LanguageManager.geminiChatPrompt(languageCode)) }
    )

    private fun promptFor(languageName: String, context: Context) = """
    You are a health report assistant for patients in rural India who may have limited medical knowledge.
    Read the attached medical report image and reply in $languageName, using simple, easy words and short sentences.
    
    Use exactly these section headings, word for word. Do not translate, change or add headings. Plain text only (no markdown, no ** or #):
    

    ${context.getString(R.string.report_h_type)}:
    (one line, e.g. Blood test, Urine test, X-ray report)

    ${context.getString(R.string.report_h_summary)}:
    (2-3 simple sentences on what this report is about)

    ${context.getString(R.string.report_h_normal)}:
    (list the values that are in the normal range)

    ${context.getString(R.string.report_h_attention)}:
    (list each value that is high or low, with the value, the normal range, and one simple line on what it may indicate. If everything is normal, say that nothing unusual was found)

    ${context.getString(R.string.report_h_tips)}:
    (1-2 simple lifestyle tips related to the findings, like water, rest or activity.
    If the report names a specific condition or diagnosis, also give 2-3 foods to eat and 2-3 foods to avoid for that condition.)

    ${context.getString(R.string.report_h_next)}:
    (tell the patient to show this report to a doctor, and mention if anything looks urgent)

    Rules:
    - Only use values actually visible in the report. Never guess or invent numbers.
    - If a part is blurry or unreadable, say so instead of guessing.
    - Do not diagnose any disease and do not suggest medicines or doses.
    - Tips must be general lifestyle advice only (food, water, sleep, exercise). Never suggest medicines, supplements or doses.
    - If the image is not a medical report, reply only with one short sentence in $languageName saying it does not look like a medical report and asking the user to upload a clear photo of the report.
    - Food advice must be general and simple. Say that diet needs may differ and a doctor or dietitian should confirm.
    - If the language is Hindi or Punjabi, write only in its own script, never in Roman letters.
        - Write every number as digits (0-9) exactly as printed in the report, and keep units and abbreviations exactly as printed (for example 140 mEq/L, 4.2, mg/dL). Never write numbers or units out in words.
""".trimIndent()

    fun analyze(context: Context, uri: Uri) {
        viewModelScope.launch {
            isLoading = true
            error = null
            summary = null
            try {
                val bitmap = withContext(Dispatchers.IO) {
                    val opts = BitmapFactory.Options().apply { inSampleSize = 2 }
                    context.contentResolver.openInputStream(uri)?.use {
                        BitmapFactory.decodeStream(it, null, opts)
                    }
                } ?: throw IllegalStateException(context.getString(R.string.report_err_read_image))

                val languageCode = LanguageManager.get(context) ?: "en"
                val response = modelFor(languageCode).generateContent(content {
                    image(bitmap)
                    text(promptFor(LanguageManager.geminiLanguageName(languageCode), context))
                })
                summary = response.text ?: context.getString(R.string.report_err_no_response)
            } catch (e: Exception) {
                error = if (e is IllegalStateException) e.message
                else context.getString(R.string.common_generic_error)
            } finally {
                isLoading = false
            }
        }
    }
}