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

    private val model = GenerativeModel(
        modelName = "gemini-3.5-flash-lite",
        apiKey = BuildConfig.GEMINI_API_KEY
    )

    private val prompt = """
    You are a health report assistant for patients in rural India who may have limited medical knowledge.
    Read the attached medical report image and reply in simple, easy English with short sentences.

    Use exactly this structure, in plain text only (no markdown, no ** or #):

    REPORT TYPE:
    (one line, e.g. Blood test, Urine test, X-ray report)

    SUMMARY:
    (2-3 simple sentences on what this report is about)

    NORMAL RESULTS:
    (list the values that are in the normal range)

    NEEDS ATTENTION:
    (list each value that is high or low, with the value, the normal range, and one simple line on what it may indicate. If everything is normal, write "Nothing unusual found")

        TIPS:
    (1-2 simple lifestyle tips related to the findings, like water, rest or activity.
    If the report names a specific condition or diagnosis, also give 2-3 foods to eat and 2-3 foods to avoid for that condition.)

    NEXT STEPS:
    (tell the patient to show this report to a doctor, and mention if anything looks urgent)

    Rules:
    - Only use values actually visible in the report. Never guess or invent numbers.
    - If a part is blurry or unreadable, say so instead of guessing.
    - Do not diagnose any disease and do not suggest medicines or doses.
    - Tips must be general lifestyle advice only (food, water, sleep, exercise). Never suggest medicines, supplements or doses.
    - If the image is not a medical report, reply only: "This does not look like a medical report. Please upload a clear photo of your report."
    - Food advice must be general and simple. Say that diet needs may differ and a doctor or dietitian should confirm.
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
                } ?: throw IllegalStateException("Could not read the image")

                val response = model.generateContent(content {
                    image(bitmap)
                    text(prompt)
                })
                summary = response.text ?: "No response from model"
            } catch (e: Exception) {
                error = e.message ?: "Something went wrong"
            } finally {
                isLoading = false
            }
        }
    }
}