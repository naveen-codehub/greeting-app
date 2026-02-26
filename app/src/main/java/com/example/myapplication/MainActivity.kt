package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
//            NavHost(navController, startDestination="screenA") {
//                composable("screenA") { GreetingApp(navController) }
//                composable(
//                    route="screenB/{name}/{dob}",
//                    arguments=listOf(
//                        navArgument("name") { type = NavType.StringType },
//                        navArgument("dob") { type = NavType.StringType }
//                    )
//                ) { backStackEntry->
//                    val name = backStackEntry.arguments?.getString("name")
//                    val dob = backStackEntry.arguments?.getString("dob")
//                    GreetingApp2(navController, name, dob)
//                }
//
//                // This we can use if we don't pass any params
////                composable("screenB") { GreetingApp2(navController) }
//            }

            // If we have only one screen on one activity
            GreetingApp(navController)
        }
    }
}

// Compose life cycle
// What is the benefit for single activity for multiple screen for compose

//2️⃣ Compose Lifecycle (UI level)
//
//Managed by Compose Runtime.
//
//It controls:
//
//When composable enters composition
//
//When it recomposes
//
//When it leaves composition
//
//This is what we are discussing.
//@Composable
//fun GreetingApp() {
//    Log.d("ComposeLog", "GreetingApp EXECUTED")
//    Log.d("ComposeLog", "GreetingApp Recomposition")
//    // State → Recomposition → UI Update
//    var name by remember { mutableStateOf("") }
//
////    Column arranges UI elements vertically.
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(40.dp, 100.dp, 40.dp, 50.dp)
//    ) {
//        TextField(
//            value = name,
//            onValueChange = {
//                Log.d("ComposeLog", "Text Changed: $it")
//                name = it
//            }
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Button(onClick = {}) {
//            Text("Submit")
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Text(text = "Hello $name 👋")
//    }
//}


//@Composable
//fun GreetingApp() {
//
//    Log.d("ComposeLog", "GreetingApp EXECUTED")
//
//    var name by remember { mutableStateOf("") }
//
//    Column {
//        StaticText()
//        InputSection(name) { name = it }
//        GreetingText(name)
//    }
//}

@Composable
fun GreetingApp(navController: NavHostController) {

//    var name = ""
    var name by remember { mutableStateOf("") }
    var dob = "123"
    val context = LocalContext.current // For new activity (One Activity -> Another Activity)

//Row() {
//
//}

    Column(

        modifier = Modifier
            .background(Color.Red)
            .padding(30.dp)
            .fillMaxSize()
        .background(Color(0xFFE3F2FD)),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        StaticText()
        Spacer(modifier = Modifier.height(30.dp))

        InputSection(name) { name = it }

        Spacer(modifier = Modifier.height(24.dp))

        GreetingText(name)
        GreetingText1(name)
        // Space - Depending Pixel
        // Font Size - .sp Scale-Independent Pixels
        Spacer(modifier = Modifier.height(24.dp))

//        dob = "64"
        Button(onClick = {
//            navController.navigate("screenB/$name")
//            navController.navigate("screenB/$name/$dob")
            navController.navigate("screenB")
//            val intent= Intent(context, WelcomeActivity::class.java)
//            intent.putExtra("name", name)
//            context.startActivity(intent)
        }) {
            Text("Submit")
        }
    }
}


@Composable
fun GreetingApp2(navController: NavHostController, name: String?, dob: String?) {

    val context = LocalContext.current
    Column(
        modifier = Modifier
            .background(Color.Red)
            .padding(30.dp)
            .fillMaxSize()  // light blue,
            .background(Color(0xFFE3F2FD)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        StaticText()
        Spacer(modifier = Modifier.height(30.dp))

//        InputSection(name)
//        { textField -> // it is used by common, So, we can set meaningful name
//            name = textField
//        }

        Spacer(modifier = Modifier.height(24.dp))

        GreetingText(name, dob)
//        GreetingText1(name)
        // Space - Depending Pixel
        // Font Size - .sp Scale-Independent Pixels
//        Spacer(modifier = Modifier.height(24.dp))
//
//        Button(onClick = {
//            val intent= Intent(context, WelcomeActivity::class.java)
//            intent.putExtra("name", name)
//            context.startActivity(intent)
//        }) {
//            Text("Submit")
//        }
    }
}

@Composable
fun StaticText() {
    Log.d("ComposeLog", "StaticText EXECUTED")
    Text("I never change 🚀")
}

@Composable
fun InputSection(name: String, onNameChange: (String) -> Unit) {
    Log.d("ComposeLog", "InputSection EXECUTED")

    // onValueChange is a callback function.
    TextField(
        value = name,
        onValueChange = onNameChange
    )
}

@Composable
fun GreetingText(name: String?, dob: String? = "") {
    Log.d("ComposeLog", "GreetingText EXECUTED")

    Text("Hello $name ... $dob 👋")
}

@Composable
fun GreetingText1(name: String) {
    Log.d("ComposeLog", "GreetingText1 EXECUTED")
    Text("Hello Again $name 👋") // Recompose only if we use that mutable variable
}


// @preview annotation or function must need, If we need to see the preview on the split view.
// @Preview is a special annotation used by: Android Studio tooling (NOT Android OS)
/* Very important:

👉 Emulator does NOT use @Preview
👉 Real device does NOT use @Preview
👉 Only Android Studio uses it
Is executed by:

The IDE (Design tool), not your app

*/
@Preview
@Composable
fun GreetingPreview() {
    GreetingApp(navController= rememberNavController())
}

/*
* You asked:

When I type, does the whole UI rebuild?
Or only some parts?
What exactly is regenerated and what is not?

Short answer:

👉 The composable functions RE-EXECUTE
👉 But the entire UI tree is NOT destroyed and recreated
👉 Compose intelligently updates only changed parts
*
*
*
* There are 3 different things:

1️⃣ Activity recreation ❌ (does NOT happen)
2️⃣ Composable function re-execution ✅ (happens)
3️⃣ Actual UI node redraw ❌ (only changed parts update)

Most beginners confuse these
*
*
*Recomposition ≠ Full rebuild
Recomposition = Re-executing composable functions
*

* 🎯 Goal
Add a background color to the entire screen.
We’ll do it in 3 ways:
1️⃣ Quick hardcoded color (easy for learning)
2️⃣ Using Material theme color (professional way)
3️⃣ Gradient (optional advanced)
*
*
*
* 🧠 Important Layout Rule
Order of modifiers matters.
Correct:
Modifier
    .fillMaxSize()
    .background(Color.Blue)
Wrong:
Modifier
    .background(Color.Blue)
    .fillMaxSize()
Because modifiers are applied in sequence.
*
*
* 🧠 Rule of Thumb
Modifier order matters when you combine:
padding
background
clickable
border
size
Because they change layout and drawing layers.
*
*
* User Types
     ↓
TextField triggers onValueChange
     ↓
name state updates
     ↓
Compose marks GreetingApp as invalid
     ↓
GreetingApp re-executes
     ↓
Text("Hello $name") updates
*
* this loop is called Reactive UI
*
* "In Compose, UI is a function of state.
* When state changes, UI automatically updates. \
* We don't manually modify UI — we modify state."
That’s declarative programming.
* * */
