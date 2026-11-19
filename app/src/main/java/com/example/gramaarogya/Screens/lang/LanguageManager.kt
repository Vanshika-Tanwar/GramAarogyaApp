package com.example.gramaarogya.Screens.lang

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

object LanguageManager {
    private const val PREFS = "gram_aarogya_prefs"
    private const val KEY = "app_language"

    fun get(context: Context): String? =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getString(KEY, null)

    fun save(context: Context, code: String) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit().putString(KEY, code).apply()
    }

    fun wrap(context: Context): Context {
        val code = get(context) ?: return context
        val config = Configuration(context.resources.configuration)
        config.setLocale(Locale.forLanguageTag(code))
        return context.createConfigurationContext(config)
    }
    fun geminiLanguageName(code: String?): String = when (code) {
        "hi" -> "Hindi, written in Devanagari script"
        "pa" -> "Punjabi, written in Gurmukhi script"
        else -> "English"
    }
    fun geminiChatPrompt(code: String?): String {
        val language = when (code) {
            "hi" -> "Reply only in Hindi, written in Devanagari script. हमेशा केवल देवनागरी लिपि में जवाब दें। रोमन अक्षरों का इस्तेमाल न करें।"
            "pa" -> "Reply only in Punjabi, written in Gurmukhi script. ਹਮੇਸ਼ਾ ਸਿਰਫ਼ ਗੁਰਮੁਖੀ ਲਿਪੀ ਵਿੱਚ ਜਵਾਬ ਦਿਓ। ਰੋਮਨ ਅੱਖਰਾਂ ਦੀ ਵਰਤੋਂ ਨਾ ਕਰੋ।"
            else -> "Reply in English."
        }
        return "$language " +
                "Even if the user types in Roman letters (like Hinglish), do not reply in Roman letters unless the language is English. " +
                "Write medicine and medical terms in the same script too, but always write numbers as digits (0 to 9) and keep units like mg/dL exactly as they are. " +
                "Use plain text only, with no markdown, no asterisks and no # symbols. " +
                "Use simple, short sentences for a person with little medical knowledge."
    }
}