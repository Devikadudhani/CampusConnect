package com.example.myapplication1.ui
@Composable
fun StudentScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text("Welcome Student!", style = MaterialTheme.typography.headlineMedium)
        }
    }
}
