package com.example.questionapp

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.questionapp.data.SuccesOrException
import com.example.questionapp.model.QuestionItem
import com.example.questionapp.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class QuestionsViewModel @Inject constructor(
    private val repository: QuestionRepository):ViewModel() {


    val data: MutableState<SuccesOrException<ArrayList<QuestionItem>,
            Boolean, Exception>> = mutableStateOf(
        SuccesOrException(null, true, Exception("")))

    init {
        getAllQuestions()
    }

    private fun getAllQuestions(){
        viewModelScope.launch {
            data.value.loading=true
            data.value=repository.getAllQuestions()
            if(data.value.data.toString().isNotEmpty()){
                data.value.loading=false
            }
        }
    }
}