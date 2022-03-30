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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composothon.ui.theme.ComposoThonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposoThonTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val teams = listOf<String>("1", "2", "3", "4")
                    val answer = "1";
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
fun successDesign(painter:Painter){
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
    val value = remember {
        mutableStateOf(value = false)
    }
    LazyColumn{
        items(count = teams.count()){ item->
            Row(horizontalArrangement = Arrangement.Center) {
                CardHOC(teams[item],value = value.value, answer){
                    value.value = it
                    correct(it)
                }
            }
        }
    }
}

@Composable
fun CardHOC(text: String, value: Boolean, answer: String, event:(Boolean)->Unit) {
    val checkAnsState = remember {
        mutableStateOf(value = Color.White)
    }
    Card(modifier = Modifier
        .background(Color.Black)
        .padding(10.dp)
        .clickable(enabled = !value) {
//            selectedItem(text)
            if (!value) {
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
        Text(
            text = text,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(vertical = 24.dp)
                .fillMaxWidth()
        )
    }
}