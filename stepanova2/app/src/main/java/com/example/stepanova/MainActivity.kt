package com.example.stepanova

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                var cycleLength by remember { mutableStateOf(28) }
                var periodLength by remember { mutableStateOf(5) }

                NavHost(
                    navController = navController,
                    startDestination = "registration"
                ) {
                    composable("login") {
                        LoginScreen(
                            onLoginClick = { navController.navigate("myMenstruation") },
                            onRegisterClick = { navController.navigate("registration") }
                        )
                    }
                    composable("registration") {
                        RegistrationScreen(
                            onRegisterClick = { navController.navigate("myMenstruation") },
                            onLoginClick = { navController.navigate("login") }
                        )
                    }

                    composable("myMenstruation") {
                        MyMenstruationScreen(navController)
                    }
                }
            }
        }
    }
}


@Composable
fun RegistrationScreen(
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Регистрация",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top = 32.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 32.dp),
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Пароль") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Подтвердите пароль") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onRegisterClick,
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text("Зарегистрироваться")
            }

            Text(
                text = buildAnnotatedString {
                    append("Есть аккаунт? ")
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                        append("Войти")
                    }
                },
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 32.dp)
                    .clickable(onClick = onLoginClick),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 32.dp)
        ) {
            Text(
                text = "Добро пожаловать!",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "Войдите в свой аккаунт",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 32.dp),
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Пароль") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onLoginClick,
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text("Войти")
            }

            Text(
                text = buildAnnotatedString {
                    append("Нет аккаунта? ")
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                        append("Зарегистрироваться")
                    }
                },
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 32.dp)
                    .clickable(onClick = onRegisterClick),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


@Composable
fun BottomNavigationBar(navController: androidx.navigation.NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconButton(onClick = { navController.navigate("editScreen") }) {
            Icon(
                painter = painterResource(id = R.drawable.curcycle),
                contentDescription = "Календарь",
                tint = Color.Black,
                modifier = Modifier.size(36.dp) // Увеличенный размер иконки
            )
        }
        IconButton(onClick = { navController.navigate("myMenstruation") }) {
            Icon(
                painter = painterResource(id = R.drawable.calendar),
                contentDescription = "Редактировать",
                tint = Color.Black,
                modifier = Modifier.size(36.dp) // Увеличенный размер иконки
            )
        }
        IconButton(onClick = { navController.navigate("settingsScreen") }) {
            Icon(
                painter = painterResource(id = R.drawable.settings),
                contentDescription = "Настройки",
                tint = Color.Black,
                modifier = Modifier.size(36.dp) // Увеличенный размер иконки
            )
        }
    }
}

@Composable
fun MyMenstruationScreen(navController: androidx.navigation.NavController) {
    var isAddNoteClicked by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Моя Менструация",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
            ) {
                Text(
                    text = "Февраль 2025",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс").forEach { day ->
                        Text(
                            text = day,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.weight(1f),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }

                Divider(
                    color = Color.Black,
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                val daysInFebruary = 28
                val firstDayOffset = 5
                val totalCells = daysInFebruary + firstDayOffset
                val weeks = 6

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                ) {
                    for (week in 0 until weeks) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            for (dayOfWeek in 0 until 7) {
                                val dayNumber = week * 7 + dayOfWeek - firstDayOffset + 1
                                val isHighlighted = dayNumber in 3..7
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f)
                                        .border(0.5.dp, Color.Gray)
                                        .background(if (isHighlighted) Color.Red.copy(alpha = 0.2f) else Color.Transparent)
                                ) {
                                    if (dayNumber in 1..daysInFebruary) {
                                        Text(
                                            text = dayNumber.toString(),
                                            style = MaterialTheme.typography.bodyMedium,
                                            modifier = Modifier.align(Alignment.Center),
                                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Button(
                    onClick = {
                        isAddNoteClicked = !isAddNoteClicked
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(top = 16.dp),
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Text(if (isAddNoteClicked) "Подтвердить" else "Редактировать")
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "23 февраля",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.align(Alignment.Start)
                )
                Text(
                    text = "1 день цикла",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.padding(top = 4.dp)
                        .align(Alignment.Start)
                )
            }
            Button(
                onClick = { navController.navigate("detailsScreen") },
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text("Изменить базовый цикл")
            }
        }

        BottomNavigationBar(navController)
    }
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen(
            onLoginClick = {},
            onRegisterClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrationScreenPreview() {
    MaterialTheme {
        RegistrationScreen(
            onRegisterClick = {},
            onLoginClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyMenstruationScreenPreview() {
    MaterialTheme {
        MyMenstruationScreen(rememberNavController())
    }
}