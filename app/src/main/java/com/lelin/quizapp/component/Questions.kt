package com.lelin.quizapp.component

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lelin.quizapp.model.QuestionItem
import com.lelin.quizapp.screens.QuestionViewModel
import com.lelin.quizapp.util.AppColors

@Composable
fun Questions(viewModel: QuestionViewModel) {
    val questions = viewModel.data.value.data?.toMutableList()
    if (viewModel.data.value.loading == true){
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
       
    }

    else{
        if (questions != null){
            QuestionDisplay(question = questions[0])
        }
    }
    Log.e("TAG", "Questions: ${questions?.size}", )
}

@Preview
@Composable
fun QuestionDisplay(
    question: QuestionItem,
//    questionIndex: MutableState<Int>,
  //  viewModel: QuestionViewModel,
    onNextClicked: (Int) ->Unit ={}
){

    val choicesState = remember(question) {
        question.choices.toMutableList()
    }

    val answerState = remember(question) {
        mutableStateOf<Int?>(null)
    }
    val correctAnswerState = remember(question) {
        mutableStateOf<Boolean?>(null)
    }
    val updateAnswer: (Int) ->Unit = remember(question) {
        {
            answerState.value = it
            correctAnswerState.value = choicesState[it] == question.answer
        }
    }



    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f,10f),0f)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(4.dp),
        color = AppColors.mDarkPurple
    ) {

        Column(
            modifier =
            Modifier.padding(12.dp),
        horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
            ) {

            QuestionTracker()
            DrawDottedLine(pathEffect = pathEffect )
            Column {
                Text(text = question.question,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.sp,
                    color = AppColors.mOffWhite,
                    modifier = Modifier
                        .padding(6.dp)
                        .align(alignment = Alignment.Start)
                        .fillMaxHeight(0.3f)
                )
                choicesState.forEachIndexed{ index,answerText ->
                    Row(modifier = Modifier
                        .padding(3.dp)
                        .fillMaxWidth()
                        .height(45.dp)
                        .border(
                            width = 4.dp, brush = Brush.linearGradient(
                                colors = listOf(AppColors.mOffDarkPurple, AppColors.mOffDarkPurple)
                            ), shape = RoundedCornerShape(15.dp)
                        )
                        .clip(
                            RoundedCornerShape(
                                topEndPercent = 50,
                                bottomEndPercent = 50,
                                topStartPercent = 50,
                                bottomStartPercent = 50
                            )
                        )
                        .background(Color.Transparent),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = (answerState.value == index), onClick = {
                            updateAnswer(index)
                        },
                        modifier = Modifier.padding(start = 16.dp),
                        colors = RadioButtonDefaults.colors(

                            selectedColor =
                            if (correctAnswerState.value == true && index == answerState.value){
                                Color.Green.copy(alpha = 0.2f)
                            }
                        else{
                                Color.Red.copy(alpha = 0.2f)

                            }

                        ))
                        Text(text = answerText)
                    }
                }
            }
            

        }

    }
}

@Composable
fun QuestionTracker(counter: Int = 10,outOff:Int = 100){
    Text(text = buildAnnotatedString {
       withStyle(style = ParagraphStyle(textIndent = TextIndent.None)){
           withStyle(style = SpanStyle(color = AppColors.mLightGray,
           fontWeight = FontWeight.Bold,
           fontSize = 27.sp)){
               append("Question $counter/")
               withStyle(style = SpanStyle(color = AppColors.mLightGray,
               fontWeight = FontWeight.Light,
               fontSize = 14.sp)
               ){
                   append("$outOff")
               }
           }
       }
    }, modifier = Modifier.padding(20.dp))
}


@Preview
@Composable
fun DrawDottedLine(pathEffect: PathEffect){

    Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(1.dp)){
        drawLine(color = AppColors.mLightGray,
        start = Offset(x = 0f, y = 0f),
            end = Offset(size.width,0f),
            pathEffect = pathEffect
            )
    }

}