package com.example.composothon

import android.content.Intent
import android.os.Bundle
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
            ComposoThonTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val abc = listOf(
                        listOf<String>("Momentum", "Photon", "Sigma", "Nucleus","Sigma"),
                        listOf<String>("Photon", "Momentum", "Sigma", "Nucleus","Photon"),
                        listOf<String>("Sigma", "Photon", "Momentum", "Nucleus","Momentum"))

                    val options = listOf<String>("Momentum", "Photon", "Sigma", "Nucleus")

                    var quesState = questionStateModel.state.collectAsState()

                    if(quesState.value < 3){
                        QuestionPoint(Question(type = "names", abc[quesState.value].last(), options = abc[quesState.value]), state = "", questionStateModel)
                    } else{
                        val context = LocalContext.current
                        context.startActivity(Intent(context, ResultsActivity::class.java))
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
fun QuestionPoint(question: Question, state: String, questionState: QuestionStateModel){
    val listOfDP = listOf(
        painterResource(id = R.drawable.vaibhav),
        painterResource(id = R.drawable.abhilash),
        painterResource(id = R.drawable.shivam),
    )
    val painter = painterResource(id = R.drawable.vaibhav)
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
            LogoDesign(painter = listOfDP[questionState.state.value])
            CardPoint(question.answer, question.options, questionState) {
                if(it){
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = "correct",)
                    }
                } else{
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = "wrong",)
                    }
                }

                questionState.state.value += 1
            }
        }
    }
//        if(successState.value == "true"){
//            PlayLottie(LottieCompositionSpec.RawRes(R.raw.correct), questionState){
//                successState.value = ""
//            }
//            return
//        } else if (successState.value == "false"){
//            PlayLottie(LottieCompositionSpec.RawRes(R.raw.wrong), questionState){
//                successState.value = ""
//            }
//        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardPoint(answer: String, options: List<String>,questionState:QuestionStateModel,callBack: (Boolean) -> Unit){
    val flag = remember {
        mutableStateOf(value = false)
    }
    val liatnew = listOf(
        listOf(
            painterResource(id = R.drawable.momentum),
            painterResource(id = R.drawable.photon),
            painterResource(id = R.drawable.sigma),
            painterResource(id = R.drawable.nucleus)),
        listOf(
            painterResource(id = R.drawable.photon),
            painterResource(id = R.drawable.momentum),
            painterResource(id = R.drawable.sigma),
            painterResource(id = R.drawable.nucleus)),
        listOf(
            painterResource(id = R.drawable.sigma),
            painterResource(id = R.drawable.photon),
            painterResource(id = R.drawable.momentum),
            painterResource(id = R.drawable.nucleus))
    )
    val list = listOf(
        painterResource(id = R.drawable.momentum),
        painterResource(id = R.drawable.photon),
        painterResource(id = R.drawable.sigma),
        painterResource(id = R.drawable.nucleus))
    LazyColumn{
        items(count = options.count()){ item->
            CardHOC(options[item], flag = flag.value, answer, image = liatnew[questionState.state.value][item],questionState){
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