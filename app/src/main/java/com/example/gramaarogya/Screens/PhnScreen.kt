package com.example.gramaarogya.Screens

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gramaarogya.Navigation.GramAarogyaAppNavItem
import com.example.gramaarogya.R
import com.google.firebase.Firebase
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.auth
import java.util.concurrent.TimeUnit

@Composable
@Preview
fun PhnScreen(navHostController: NavController? = null) {
    val context = LocalContext.current
    val activity = context as? Activity

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "GramAarogyaLogo",
            modifier = Modifier.size(300.dp)
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "OTP Verification",
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(30.dp))
        var phnNum by remember { mutableStateOf("") }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "+91",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                value = phnNum,
                onValueChange = { phnNum = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Enter phone number") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = {
                val phoneNumberWithCountryCode = "+91" + phnNum
                val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                        Firebase.auth.signInWithCredential(credential).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show()
                                navHostController?.navigate(GramAarogyaAppNavItem.otpScreen.route)
                            } else {
                                Log.e("PhoneAuth", "Login failed: ${task.exception?.message}")
                                Toast.makeText(context, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    override fun onVerificationFailed(e: FirebaseException) {
                        Log.e("PhoneAuth", "Verification failed: ${e.message}")
                        Toast.makeText(context, "Verification failed: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                    override fun onCodeSent(
                        verificationID: String,
                        token: PhoneAuthProvider.ForceResendingToken
                    ) {
                        navHostController?.currentBackStackEntry?.savedStateHandle?.set("verificationId", verificationID)
                        navHostController?.navigate(GramAarogyaAppNavItem.otpScreen.route)
                    }
                }
                if (activity != null) {
                    val options = PhoneAuthOptions.newBuilder(Firebase.auth)
                        .setPhoneNumber(phoneNumberWithCountryCode)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(activity)
                        .setCallbacks(callbacks)
                        .build()
                    PhoneAuthProvider.verifyPhoneNumber(options)
                } else {
                    Toast.makeText(context, "Activity context is not available.", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black, contentColor = Color.White)
        ) {
            Text("Get OTP", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}