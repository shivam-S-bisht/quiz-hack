package com.example.composothon

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.*
import com.example.composothon.ui.theme.ComposoThonTheme

class SplashActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposoThonTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                    ){
                        PlayLottieSplash(LottieCompositionSpec.RawRes(R.raw.splash))
                    }
                }
            }
        }
    }
}

@Composable
fun PlayLottieSplash(spec:LottieCompositionSpec){
    val composition by rememberLottieComposition(spec)
    val progress by animateLottieCompositionAsState(composition)
    val compositionResult: LottieCompositionResult = rememberLottieComposition(spec = spec)
    LottieAnimation(
        composition = composition,
        progress = progress)
    var x = compositionResult
    if(compositionResult.isComplete){
        val context = LocalContext.current
        context.startActivity(Intent(context, StartActivity::class.java))
        return
    } else if(compositionResult.isFailure){
    }
}