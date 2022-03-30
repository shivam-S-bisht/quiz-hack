package com.example.composothon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.composothon.ui.theme.ComposoThonTheme

class ResultsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposoThonTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val painter = painterResource(id = R.drawable.vaibhav)
                    Box(modifier = Modifier
//                        .fillMaxSize()
//                        .background(Color.Black)
                    ){
                        ResultsGreeting(name = "10points")
                    }
                }
            }
        }
    }
}

@Composable
fun ResultsGreeting(name: String) {
    Text(text = "Result Screen ----- Points: $name!")
}