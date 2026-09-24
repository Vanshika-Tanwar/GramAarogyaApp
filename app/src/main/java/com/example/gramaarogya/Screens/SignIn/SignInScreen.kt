package com.example.gramaarogya.Screens.SignIn

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
//import com.example.koffi.Navigation.AppNavigationItem
//import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import com.example.gramaarogya.Navigation.GramAarogyaAppNavItem
import com.example.gramaarogya.ui.theme.bgWhite
import com.example.gramaarogya.ui.theme.lightgray

//import com.example.koffi.Database.AppDatabase
//import com.example.koffi.Repository.Auth.AuthRepository
//import com.example.koffi.Repository.Auth.AuthViewModelFactory
//import com.example.koffi.ui.theme.bgCartGray
//import com.example.koffi.ui.theme.bgSpecialGray
//import com.example.koffi.ui.theme.bgWhite
//import com.example.koffi.ui.theme.lightgray

@Composable
fun SignInScreen(
    navHostController: NavHostController,
    //viewModel: SignInViewModel = viewModel()
) {
    val context = LocalContext.current

//    val database = remember {
//        AppDatabase.getDatabase(context)
//    }
//
//    val repository = remember {
//        AuthRepository(database.userDao())
//    }
//
//    val factory = remember {
//        AuthViewModelFactory(repository)
//    }

    val viewModel: SignInViewModel = viewModel(
        //factory = factory
    )
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = bgWhite,
        modifier = Modifier.fillMaxSize()
    ) {
            innerPadding ->
        Box(
            Modifier
                .padding(innerPadding)
                .background(color = bgWhite)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 32.dp)
                    .background(color = bgWhite),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = "Hey there",
                    //style = MaterialTheme.typography.headlineSmall,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.LightGray

                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Welcome Back",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(30.dp))

                // Email
                TextField(
                    value = uiState.email,
                    onValueChange = viewModel::onEmailChange,
                    modifier = Modifier
                        .fillMaxWidth(),
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Email",
                            color = Color.DarkGray,
                            fontWeight = FontWeight.Normal
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Email"
                        )
                    },
                    shape = RoundedCornerShape(14.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = lightgray,
                        unfocusedContainerColor = lightgray,
                        disabledContainerColor = Color.LightGray,

                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,

                        focusedPlaceholderColor = Color.LightGray,
                        unfocusedPlaceholderColor = Color.LightGray,

                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Password
                TextField(
                    value = uiState.password,
                    onValueChange = viewModel::onPasswordChange,
                    modifier = Modifier
                        .fillMaxWidth(),
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Password",
                            color = Color.DarkGray,
                            fontWeight = FontWeight.Normal
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Password"
                        )
                    },
                    shape = RoundedCornerShape(14.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = lightgray,
                        unfocusedContainerColor = lightgray,
                        disabledContainerColor = Color.LightGray,

                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,

                        focusedPlaceholderColor = Color.LightGray,
                        unfocusedPlaceholderColor = Color.LightGray,

                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    )
                )

                Spacer(modifier = Modifier.height(300.dp))

                Button(
                    onClick = {
//                        viewModel.signIn {
//                            navHostController.navigate(
//                                GramAarogyaAppNavItem.dashboardScreen.route
//                            ) {
//                                popUpTo(
//                                    GramAarogyaAppNavItem.SignInScreen.route
//                                ) {
//                                    inclusive = true
//                                }
//                            }
//                        }
                        navHostController.navigate(
                            GramAarogyaAppNavItem.dashboardScreen.route
                        ) {
                            popUpTo(
                                GramAarogyaAppNavItem.SignInScreen.route
                            ) {
                                inclusive = true
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    //enabled = viewModel.canSignIn() && !uiState.isLoading
                ) {
                    Text("Login")
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        navHostController.navigate(
                            GramAarogyaAppNavItem.SignUpScreen.route
                        ) {
                            popUpTo( GramAarogyaAppNavItem.SignInScreen.route) {
                                inclusive = true
                            }
                        }
                    }
                ) {

                    Text(
                        text = "Don't have an account yet?",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Text(
                        text = " Register",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }


}