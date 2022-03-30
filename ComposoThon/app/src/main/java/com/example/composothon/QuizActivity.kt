package com.example.composothon

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*
import com.example.composothon.ui.theme.ComposoThonTheme

class QuizActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposoThonTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val successState = remember {
                        mutableStateOf("")
                    }
                    val teams = listOf<String>("Momentum", "Photon", "Sigma", "Nucleus")
                    val answer = "Sigma";
                    val painter = painterResource(id = R.drawable.vaibhav)
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                    ){

                           Column {
                               LogoDesign(painter = painter )
                               CardPoint(answer, teams){
                                   successState.value = it
                               }
                           }
                       if(successState.value == "true"){
                           PlayLottie(LottieCompositionSpec.RawRes(R.raw.correct))
                       } else if (successState.value == "false"){
                           PlayLottie(LottieCompositionSpec.RawRes(R.raw.wrong))
                       }
                    }
                }
            }
        }
    }
}

data class AnswerValue(
    var event: Boolean,
    var correct: Boolean,
)

@Composable
fun LogoDesign(painter:Painter){
    Card(
        shape = RoundedCornerShape(100.dp),
        elevation = 5.dp,
        modifier = Modifier.padding(100.dp)
    ){
        Image(painter = painter, contentDescription = "xyc" ,modifier = Modifier
            .height(200.dp)
            .width(200.dp)
        )
    }
}

@Composable
fun successDesign(painter: Painter){
    Card(
        shape = RoundedCornerShape(100.dp),
        elevation = 5.dp,
        modifier = Modifier.padding(100.dp)
    ){
        Image(painter = painter, contentDescription = "xyc" ,modifier = Modifier
            .height(200.dp)
            .width(200.dp)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardPoint(answer: String, teams: List<String>, correct:(String)->Unit){
    val flag = remember {
        mutableStateOf(value = false)
    }
    val list = listOf(
        painterResource(id = R.drawable.momentum),
        painterResource(id = R.drawable.photon),
        painterResource(id = R.drawable.sigma),
        painterResource(id = R.drawable.nucleus))
    LazyColumn{
        items(count = teams.count()){ item->
            CardHOC(teams[item],flag = flag.value, answer,image = list[item]){
                flag.value = it.event
                correct(it.correct.toString())
                }
        }
    }
}

@Composable
fun CardHOC(text: String, flag: Boolean, answer: String,image:Painter, event:(AnswerValue)->Unit) {
    val checkAnsState = remember {
        mutableStateOf(value = Color.White)
    }

    Card(modifier = Modifier
        .background(Color.Black)
        .padding(10.dp)
        .fillMaxWidth()
        .clickable(enabled = !flag) {
            if (!flag) {
                if (text == answer) {
                    checkAnsState.value = Color.Green
                    event(AnswerValue(event = true, correct = true))
                } else {
                    checkAnsState.value = Color.Red
                    event(AnswerValue(event = true, correct = false))
                }
            }
        },
        backgroundColor = checkAnsState.value,
        shape = RoundedCornerShape(20.dp)
    ){
        Row(horizontalArrangement = Arrangement.Start,verticalAlignment = Alignment.CenterVertically) {
            Card(
                elevation = 5.dp,
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .width(60.dp)
                    .height(60.dp)
            )
            {
                Image(
                    painter = image,
                    contentDescription = "hello",
                    modifier = Modifier.size(40.dp)
                )
            }
            Text(
                text = text,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .padding(vertical = 24.dp,horizontal = 10.dp)
            )
        }
    }
}

@Composable
fun PlayLottie(spec:LottieCompositionSpec){
    val composition by rememberLottieComposition(spec)
    val progress by animateLottieCompositionAsState(composition)
    val compositionResult: LottieCompositionResult = rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.correct))
    LottieAnimation(
        composition = composition,
        progress = progress)
    var x = compositionResult
    if(compositionResult.isComplete){
        val context = LocalContext.current
        context.startActivity(Intent(context, MainActivity::class.java))
        return
    } else if(compositionResult.isFailure){
    }
}