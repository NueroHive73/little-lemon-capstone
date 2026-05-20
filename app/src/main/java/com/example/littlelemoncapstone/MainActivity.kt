package com.example.littlelemoncapstone

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.*
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LittleLemonApp()
        }
    }
}

@Composable
fun LittleLemonApp() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "onboarding"
    ) {

        composable("onboarding") {
            OnboardingScreen(navController)
        }

        composable("home") {
            HomeScreen(navController)
        }

        composable("profile") {
            ProfileScreen(navController)
        }
    }
}

@Composable
fun OnboardingScreen(navController: androidx.navigation.NavHostController) {

    val context = LocalContext.current

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Little Lemon",
            fontSize = 32.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("First Name") }
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last Name") }
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {

                val sharedPreferences =
                    context.getSharedPreferences("LittleLemon", Context.MODE_PRIVATE)

                sharedPreferences.edit()
                    .putString("firstName", firstName)
                    .putString("lastName", lastName)
                    .putString("email", email)
                    .apply()

                navController.navigate("home")
            }
        ) {
            Text("Register")
        }
    }
}

@Composable
fun HomeScreen(navController: androidx.navigation.NavHostController) {

    val menuItems = listOf(
        "Greek Salad",
        "Bruschetta",
        "Pasta",
        "Lemon Dessert"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Little Lemon",
            fontSize = 32.sp
        )

        Text(text = "Chicago")

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "We are a family owned Mediterranean restaurant."
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Search") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Menu",
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn {

            items(menuItems) { item ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {

                    Text(
                        text = item,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                navController.navigate("profile")
            }
        ) {
            Text("Profile")
        }
    }
}

@Composable
fun ProfileScreen(navController: androidx.navigation.NavHostController) {

    val context = LocalContext.current

    val sharedPreferences =
        context.getSharedPreferences("LittleLemon", Context.MODE_PRIVATE)

    val firstName =
        sharedPreferences.getString("firstName", "")

    val lastName =
        sharedPreferences.getString("lastName", "")

    val email =
        sharedPreferences.getString("email", "")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Profile",
            fontSize = 32.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "First Name: $firstName")

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "Last Name: $lastName")

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "Email: $email")

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {

                sharedPreferences.edit().clear().apply()

                navController.navigate("onboarding")
            }
        ) {
            Text("Log out")
        }
    }
}