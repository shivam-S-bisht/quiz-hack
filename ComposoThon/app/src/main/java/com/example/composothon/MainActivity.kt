package com.example.composothon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
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
                    val painter = painterResource(id = R.drawable.vaibhav)
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                    ){
                        Column {
                            LogoDesign(painter = painter )
                            CardPoint()
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
fun CardPoint(){
//    val list = listOf(
//        painterResource(id = R.drawable.momentum),
//        painterResource(id = R.drawable.momentum),
//        painterResource(id = R.drawable.momentum),
//        painterResource(id = R.drawable.momentum))
    val arr = listOf("Momentum","Momentum","Momentum","Momentum")
    LazyColumn{
        items(count = arr.count()){ item->
            Row(horizontalArrangement = Arrangement.Center) {
                Card(modifier = Modifier
                    .background(Color.Black)
                    .padding(10.dp)){
                    Text(
                        text = arr[item].toString(),
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
        }
    }
}
@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposoThonTheme {
        Greeting("Android")
    }
}