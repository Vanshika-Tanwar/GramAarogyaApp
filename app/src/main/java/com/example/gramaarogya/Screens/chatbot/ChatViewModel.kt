package com.example.gramaarogya.Screens.chatbot

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gramaarogya.Models.Messages
import com.example.gramaarogya.Screens.Constants
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.ServerException
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

class ChatViewModel: ViewModel() {
    val messageList by lazy {
        mutableStateListOf<Messages>()
    }

    val generativeModel: GenerativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = Constants.geminiAPIKey
    )

    fun sendMessage(question: String) {
        try {
            viewModelScope.launch {
                val chat = generativeModel.startChat(
                    history = messageList.map {
                        content(it.role) { text(it.message) }
                    }.toList()
                )
                messageList.add(Messages(message = question, role = "user"))
                messageList.add(Messages(message = "typing...", role = "model"))
                val response = chat.sendMessage(question)
                messageList.removeAt(messageList.lastIndex)
                messageList.add(Messages(message = response.text.toString(), role = "model"))
            }
        } catch (e: Exception) {
            val errorMessage = "api error ${e.message}"
            messageList.add((Messages(message = errorMessage, role = "model")))
        } catch (e: ServerException) {
            val errorMessage = "server error ${e.message}"
            messageList.add((Messages(message = errorMessage, role = "model")))
        }
    }
}