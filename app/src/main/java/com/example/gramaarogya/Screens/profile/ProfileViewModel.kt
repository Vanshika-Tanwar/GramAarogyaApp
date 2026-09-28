package com.example.gramaarogya.Screens.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

data class UserProfile(
    val name: String = "",
    val phone: String = "",
    val address: String = "",
    val dob: String = "",
    val gender: String = ""
)

class ProfileViewModel : ViewModel() {
    var profile by mutableStateOf(UserProfile())
        private set
    var isLoading by mutableStateOf(true)
        private set
    var error by mutableStateOf<String?>(null)
        private set

    init {
        fetchProfile()
    }

    fun fetchProfile() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if(uid == null){
            error = "Not logged in"
            isLoading = false
            return
        }
    }
    /*
    FirebaseFirestore.getInstance()
    .collection("users")
    .document(uid)
    .get()
    .addOnSuccessListener{ doc ->
    profile = UserProfile(
    name = doc.getString("name") ?: "",
                    phone = doc.getString("phone") ?: "",
                    address = doc.getString("address") ?: "",
                    dob = doc.getString("dob") ?: "",
                    gender = doc.getString("gender") ?: ""
                )
                isLoading = false
}
.addOnFailureListener{
e ->
error = e.message
isLoading = false
}}
    */
}