package com.example.questionapp.component

import android.graphics.PathEffect
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.questionapp.QuestionsViewModel
import org.intellij.lang.annotations.JdkConstants
import androidx.compose.ui.graphics.PathEffect.Companion.dashPathEffect
import androidx.compose.ui.text.style.TextAlign
import com.example.questionapp.model.QuestionItem

@Composable
fun Questions(viewModel: QuestionsViewModel) {


    val questions=viewModel.data.value.data?.toMutableList()


    val questionIndex=remember{
        mutableStateOf(0)
    }
    if(viewModel.data.value.loading==true){
        CircularProgressIndicator()
        

    }else{
            val question=try{
            questions?.get(questionIndex.value)

        }catch (e:Exception){
            e.printStackTrace()
        }
        if (question!=null){
            QuestionDisplay(question = question as QuestionItem, questionIndex = questionIndex, viewModel =viewModel ){
                questionIndex.value=questionIndex.value+1
            }
        }
    }


}


@Composable
fun QuestionDisplay(question:QuestionItem,
                    questionIndex: MutableState<Int>,
                    viewModel: QuestionsViewModel,
                    onNextClicked:(Int)->Unit={}){

    val choicesState=remember(question){
        question.choices.toMutableList()
    }

    val answerState=remember(question){
        mutableStateOf<Int?>(null)
    }
    val correctAnswerState=remember(question){
        mutableStateOf<Boolean?>(null)
    }
    val updateAnswer:(Int)->Unit= remember(question) {
        {
            answerState.value=it
            correctAnswerState.value=choicesState[it]==question.answer
        }
    }
    val pathEffect=dashPathEffect(floatArrayOf(10f,10f),0f)
    Surface(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(4.dp),
    color = Color.Blue) {

        Column(modifier = Modifier.padding(12.dp),
        horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Top) {
            if (questionIndex.value>=3) {
                ShowProgress(score = questionIndex.value)
            }
            viewModel.getTotalCount()
                ?.let { QuestionTracker(counter = questionIndex.value, outOff = it) }
            DrawDottedLine(pathEffect = pathEffect)
            Column {
                Text(
                    text = question.question,
                    modifier = Modifier
                        .padding(6.dp)
                        .align(alignment = Alignment.Start)
                        .fillMaxHeight(0.3f),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.sp,
                    color = Color.White
                )
                //choices
                choicesState.forEachIndexed { index, answerText ->
                    Row(
                        modifier = Modifier
                            .padding(3.dp)
                            .fillMaxWidth()
                            .height(45.dp)
                            .border(
                                width = 4.dp, brush = Brush.linearGradient(
                                    colors = listOf(Color.LightGray, Color.LightGray)
                                ), shape = RoundedCornerShape(15.dp)
                            )
                            .clip(
                                RoundedCornerShape(
                                    topStartPercent = 50,
                                    bottomEndPercent = 50,
                                    bottomStartPercent = 50,
                                    topEndPercent = 50
                                )
                            )
                            .background(Color.Transparent),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected =(answerState.value==index) , onClick = { updateAnswer(index) }
                        , modifier = Modifier.padding(start = 16.dp), colors=RadioButtonDefaults.colors(
                                selectedColor =
                                    if(correctAnswerState.value==true&&index==answerState.value){
                                        Color.Green.copy(alpha = 0.3f)
                                    }
                            else{
                                    Color.Red.copy(0.3f)

                                    }

                        ))
                        val annotatedString = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Light,
                                color = if (correctAnswerState.value == true
                                    && answerState.value == index) {
                                    Color.Green
                                } else if (correctAnswerState.value == false
                                    && answerState.value == index) {
                                    Color.Red
                                } else {Color.White},
                                fontSize = 17.sp)) {
                                append(answerText)
                            }
                        }
                        Text(text = annotatedString, modifier = Modifier.padding(6.dp))
                    }

                    }
                Button(onClick = {onNextClicked(questionIndex.value)},
                modifier = Modifier
                    .padding(4.dp)
                    .align(alignment = Alignment.CenterHorizontally),
                shape = RoundedCornerShape(36.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Green.copy(0.6f),

                )) {
                    Text(text = "Sonraki soru",
                    modifier = Modifier.padding(6.dp),
                        color= Color.White,
                    fontSize = 18.sp)
                }
                }
            }
        }
    }


@Composable
fun DrawDottedLine(pathEffect:androidx.compose.ui.graphics.PathEffect){
    // ='le yazıp dene birde
    Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(1.dp),){
       drawLine(color = Color.LightGray, start = Offset(0f,0f),
           end= Offset(size.width,y=0f),
           pathEffect = pathEffect)
    }
}

@Composable
fun ShowProgress(score:Int=12){
    val gradient=Brush.linearGradient(listOf(Color(0xFFF95075),
        Color(0xFFBE6BE5)))

    val progressFactor= remember(score) {
       mutableStateOf(score*0.005f)
    }

    Row(modifier = Modifier
        .padding(3.dp)
        .fillMaxWidth()
        .height(45.dp)
        .border(
            width = 4.dp,
            brush = Brush.linearGradient(
                colors = listOf(
                    colors.primaryVariant, colors.onSecondary
                )
            ),
            shape = RoundedCornerShape(34.dp)
        )
        .clip(
            RoundedCornerShape(
                topEndPercent = 50,
                bottomStartPercent = 50,
                bottomEndPercent = 50,
                topStartPercent = 50
            )
        )
        .background(Color.Transparent), verticalAlignment = Alignment.CenterVertically) {
        Button(onClick = {}, contentPadding = PaddingValues(1.dp), modifier = Modifier
            .fillMaxWidth(progressFactor.value)
            .background(brush = gradient),
        enabled = false,
        elevation = null,
        colors = buttonColors(
            backgroundColor = Color.Transparent,
            disabledBackgroundColor = Color.Transparent
        )) {
            Text(text = (score * 10).toString(),
            modifier = Modifier.clip(shape = RoundedCornerShape(23.dp))
                .fillMaxWidth(fraction = 0.86f)
                .fillMaxWidth()
                .padding(6.dp),
                color = Color.White,
            textAlign = TextAlign.Center)
        }

    }
}

@Composable
fun QuestionTracker(counter:Int=10,outOff:Int=100){
    Text(text = buildAnnotatedString {
        withStyle(style = ParagraphStyle(textIndent = TextIndent.None)){
            withStyle(style = SpanStyle(color = MaterialTheme.colors.onPrimary, fontWeight = FontWeight.Bold, fontSize = 27.sp)){
                append("Question $counter/")
                withStyle(style = SpanStyle(color = MaterialTheme.colors.secondaryVariant, fontSize = 14.sp, fontWeight = FontWeight.Light)){
                    append("$outOff")
                }
            }
        }

    },
        modifier = Modifier.padding(20.dp)
    )
}








