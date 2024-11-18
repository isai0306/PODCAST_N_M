package com.example.podcastplayer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.podcastplayer.ui.theme.PodcastPlayerTheme

class LoginActivity : ComponentActivity() {
    private lateinit var databaseHelper: UserDatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databaseHelper = UserDatabaseHelper(this)
        setContent {
            PodcastPlayerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LoginScreen(this, databaseHelper)
                }
            }
        }
    }
}
@Composable
fun LoginScreen(context: Context, databaseHelper: UserDatabaseHelper) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF6a3ef9),
                        Color(0xFF3d19b5),
                        Color.Black
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Card(
            elevation = 16.dp,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.9f)
                .wrapContentHeight()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black) // Transparent card to show background
            ) {
                // Background Image
// Background Image
                Image(
                    painter = painterResource(R.drawable.img),
                    contentDescription = "Card Background",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    alignment = Alignment.TopCenter
                )

                // Content Layer
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(200.dp)) // Offset for the background image

                    // Welcome Text with Cursive Font
                    Text(
                        text = "Welcome Back!",
                        color = Color(0xFF6a3ef9),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Cursive // Cursive font
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Container for Username and Password
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color.Black,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Column {
                            // Username Field
                            OutlinedTextField(
                                value = username,
                                onValueChange = { username = it },
                                label = { Text(text = "Username", color = Color.White) },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = "Username Icon",
                                        tint = Color(0xFF6a3ef9)
                                    )
                                },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Color(0xFF6a3ef9),
                                    unfocusedBorderColor = Color.Gray,
                                    cursorColor = Color(0xFF6a3ef9),
                                    textColor = Color.White
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Password Field
                            OutlinedTextField(
                                value = password,
                                onValueChange = { password = it },
                                label = { Text(text = "Password", color = Color.White) },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Lock,
                                        contentDescription = "Password Icon",
                                        tint = Color(0xFF6a3ef9)
                                    )
                                },
                                visualTransformation = PasswordVisualTransformation(),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Color(0xFF6a3ef9),
                                    unfocusedBorderColor = Color.Gray,
                                    cursorColor = Color(0xFF6a3ef9),
                                    textColor = Color.White
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Error Message
                    if (error.isNotEmpty()) {
                        Text(
                            text = error,
                            color = MaterialTheme.colors.error,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    // Login Button
                    Button(
                        onClick = {
                            if (username.isNotEmpty() && password.isNotEmpty()) {
                                val user = databaseHelper.getUserByUsername(username)
                                if (user != null && user.password == password) {
                                    error = ""
                                    context.startActivity(
                                        Intent(context, MainActivity::class.java)
                                    )
                                } else {
                                    error = "Invalid username or password"
                                }
                            } else {
                                error = "Please fill all fields"
                            }
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6a3ef9)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Log In",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Sign Up and Forgot Password Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextButton(onClick = {
                            context.startActivity(
                                Intent(context, RegistrationActivity::class.java)
                            )
                        }) {
                            Text(text = "Sign Up", color = Color(0xFF6a3ef9))
                        }

                        TextButton(onClick = { /* Add Forgot Password functionality */ }) {
                            Text(text = "Forgot Password?", color = Color(0xFF6a3ef9))
                        }
                    }
                }
            }
        }
    }
}
