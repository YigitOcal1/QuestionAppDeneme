package com.example.questionapp.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.questionapp.QuestionsViewModel
import com.example.questionapp.component.Questions

@Composable
fun QuestionHome(viewModel: QuestionsViewModel = hiltViewModel()){
    Questions(viewModel)
}