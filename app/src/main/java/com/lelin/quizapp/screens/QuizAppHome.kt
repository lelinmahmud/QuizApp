package com.lelin.quizapp.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.lelin.quizapp.component.Questions

@Composable
fun QuizApp(viewModel: QuestionViewModel = hiltViewModel()){
    Questions(viewModel)
}