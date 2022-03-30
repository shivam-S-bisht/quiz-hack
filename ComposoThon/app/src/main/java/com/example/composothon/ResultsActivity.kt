package com.example.composothon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.LottieDrawable
import com.airbnb.lottie.compose.*
import com.example.composothon.ui.theme.ComposoThonTheme

class ResultsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val people = intent.getSerializableExtra("score")
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
                        Column {
                            ResultsGreeting(name = "10points")
                            Text(text = "Score:  $people / 30",
                                color = Color.White,

                                fontSize = 40.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.paddingFromBaseline(top = 70.dp)
                            )
                        }
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

    LottieAnimation(
        composition = composition,
        progress = progress,
    )
}