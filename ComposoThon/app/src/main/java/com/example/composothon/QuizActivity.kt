package com.example.composothon

import android.content.Intent
import android.graphics.drawable.Drawable
import android.media.Image
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.lifecycle.ViewModel
import com.airbnb.lottie.compose.*
import com.example.composothon.ui.theme.ComposoThonTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
class QuizActivity : ComponentActivity() {
    private val questionStateModel: QuestionStateModel by viewModels();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var objOfSquad = listOf(
                mapOf(
                    "squad" to "Sigma",
                    "name" to "Vaibhav",
                    "image" to painterResource(id = R.drawable.vaibhav) ,
                    "options" to listOf("Momentum", "Photon", "Sigma","Nucleus"),
                    "logos" to listOf(
                        painterResource(id = R.drawable.momentum) ,
                        painterResource(id = R.drawable.photon),
                        painterResource(id = R.drawable.sigma),
                        painterResource(id = R.drawable.nucleus))
                ),
                mapOf(
                    "squad" to "Momentum",
                    "name" to "Abhilash",
                    "image" to painterResource(id = R.drawable.abhilash) ,
                    "options" to listOf("Photon", "Momentum", "Sigma","Nucleus"),
                    "logos" to listOf(
                        painterResource(id = R.drawable.photon) ,
                        painterResource(id = R.drawable.momentum),
                        painterResource(id = R.drawable.sigma),
                        painterResource(id = R.drawable.nucleus)
                    )
                ),
                mapOf(
                    "squad" to "Photon",
                    "name" to "Shivam",
                    "image" to painterResource(id =  R.drawable.shivam),
                    "options" to listOf("Sigma", "Photon", "Momentum","Nucleus"),
                    "logos" to listOf(
                        painterResource(id = R.drawable.sigma) ,
                        painterResource(id = R.drawable.photon),
                        painterResource(id = R.drawable.momentum),
                        painterResource(id = R.drawable.nucleus)
                    )
                )
            )
            ComposoThonTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    var quesState = questionStateModel.state.collectAsState()
                    var scoreState = remember {
                        mutableStateOf(0)
                    }
                    if(quesState.value < 3){
                        QuestionPoint(
                            Question(
                                type = "names",
                                answer= objOfSquad[quesState.value]["squad"].toString(),
                                options = objOfSquad[quesState.value]["options"] as List<String>,
                                squadLogos = objOfSquad[quesState.value]["logos"] as List<Painter>,
                                logoImage = objOfSquad[quesState.value]["image"]  as Painter,
                                name = objOfSquad[quesState.value]["name"] as String
                                ), state = "", questionStateModel){
                            if(it){
                                scoreState.value +=10
                            }
                        }
                    } else{
                        val context = LocalContext.current
                            context.startActivity(Intent(context, ResultsActivity::class.java).apply {
                                putExtra("score","${scoreState.value}")
                            })
                    }
                }
            }
        }
    }
}

data class Question(
    var type: String,
    var answer: String,
    var options: List<String>,
    var logoImage:Painter,
    var squadLogos: List<Painter>,
    var name:String
)

data class QuestionState(
    var state: Int

)

class QuestionStateModel : ViewModel() {
    var state = MutableStateFlow(0);
    var successState = MutableStateFlow("");
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
fun QuestionPoint(question: Question, state: String, questionState: QuestionStateModel,callBack: (Boolean) -> Unit){
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
    ) {
        Scaffold(
            scaffoldState = scaffoldState
        ) {
        Column(modifier = Modifier.background(Color.Black)) {
            LogoDesign(painter =  question.logoImage)
            CardPoint(question.answer, question.options, questionState,question) {
                if(it){
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = "correct",)
                        callBack(true)
                    }
                } else{
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = "wrong",)
                    }
                    callBack(false)
                }

                Handler().postDelayed({
                    questionState.state.value += 1
                },5000)
            }
        }
    }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardPoint(answer: String, options: List<String>,questionState:QuestionStateModel,question: Question,callBack: (Boolean) -> Unit){
    val flag = remember {
        mutableStateOf(value = false)
    }
    LazyColumn{
        items(count = options.count()){ item->
            CardHOC(options[item], flag = flag.value, answer, image = question.squadLogos[item],questionState){
                callBack(it)
            }
        }
    }
}

@Composable
fun CardHOC(text: String, flag: Boolean, answer: String, image:Painter,questionState:QuestionStateModel,callBack:(Boolean)->Unit) {
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
                    callBack(true)
                } else {
                    callBack(false)
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
fun PlayLottie(spec:LottieCompositionSpec, questionState: QuestionStateModel, event:()->Unit){
    val composition by rememberLottieComposition(spec)
    val progress by animateLottieCompositionAsState(composition)
    val compositionResult: LottieCompositionResult = rememberLottieComposition(spec = spec)
    LottieAnimation(
        composition = composition,
        progress = progress)
    var x = compositionResult
    if(compositionResult.isComplete){
        event();
        questionState.state.value += 1;
        return
    }
    event();
}