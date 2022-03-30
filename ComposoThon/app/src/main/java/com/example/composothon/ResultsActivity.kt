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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.airbnb.lottie.compose.*
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
                        .fillMaxSize()
                        .background(Color.Black),
                        contentAlignment = Alignment.Center
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
    PlayLottieAnim(spec = LottieCompositionSpec.RawRes(R.raw.gameover))
}

@Composable
fun PlayLottieAnim(spec: LottieCompositionSpec){
    val composition by rememberLottieComposition(spec)
    val progress by animateLottieCompositionAsState(composition)
    val compositionResult: LottieCompositionResult = rememberLottieComposition(spec = spec)
    LottieAnimation(
        composition = composition,
        progress = progress)
}