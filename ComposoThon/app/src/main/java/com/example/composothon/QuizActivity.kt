package com.example.composothon

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

                            }
                        }
                    }
                }
            }
        }
    }
}

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
fun CardPoint(answer: String, teams: List<String>, correct:(Boolean)->Unit){
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
            Row(horizontalArrangement = Arrangement.Center) {
                CardHOC(teams[item],flag = flag.value, answer,image = list[item]){
                    flag.value = it
                    correct(it)
                }
            }
        }
    }
}

@Composable
fun CardHOC(text: String, flag: Boolean, answer: String,image:Painter ,event:(Boolean)->Unit) {
    val checkAnsState = remember {
        mutableStateOf(value = Color.White)
    }
    Card(modifier = Modifier
        .background(Color.Black)
        .padding(10.dp)
        .fillMaxWidth()
        .clickable(enabled = !flag) {
            if (!flag) {
                if(text == answer){
                    checkAnsState.value = Color.Green
                } else {
                    checkAnsState.value = Color.Red
                }
                event(true)
            }
        },
        backgroundColor = checkAnsState.value
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