package com.example.gramaarogya.Screens.chatbot

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gramaarogya.BuildConfig
import com.example.gramaarogya.Models.Messages
import com.example.gramaarogya.Screens.lang.LanguageManager
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.ServerException
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

class ChatViewModel: ViewModel() {
    val messageList by lazy {
        mutableStateListOf<Messages>()
    }

    private fun modelFor(languageCode: String) = GenerativeModel(
        modelName = "gemini-3.5-flash-lite",
        apiKey = BuildConfig.GEMINI_API_KEY,
        systemInstruction = content { text(LanguageManager.geminiChatPrompt(languageCode)) }
    )

    fun sendMessage(question: String, typingText: String, errorText: String, languageCode: String) {
        viewModelScope.launch {
            val chat = modelFor(languageCode).startChat(
                history = messageList.map { content(it.role) { text(it.message) } }
            )
            messageList.add(Messages(message = question, role = "user"))
            messageList.add(Messages(message = typingText, role = "model"))
            try {
                val response = chat.sendMessage(question)
                messageList.removeAt(messageList.lastIndex)
                messageList.add(Messages(message = response.text.toString(), role = "model"))
            } catch (e: Exception) {
                Log.e("ChatViewModel", "sendMessage failed", e)
                messageList.removeAt(messageList.lastIndex)
                messageList.add(Messages(message = errorText, role = "model"))
            }
        }
    }
}