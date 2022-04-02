package com.example.questionapp.repository

import androidx.compose.material.Text
import com.example.questionapp.data.SuccesOrException
import com.example.questionapp.model.QuestionItem
import com.example.questionapp.network.QuestionAPI
import javax.inject.Inject
import kotlin.Exception

class QuestionRepository @Inject constructor(
    private val API:QuestionAPI
) {
private val successOrException=
    SuccesOrException<ArrayList<QuestionItem>,
            Boolean,
            Exception>()
    suspend fun getAllQuestions():SuccesOrException<ArrayList<QuestionItem>,Boolean,Exception> {
        try {

            successOrException.loading = true
            successOrException.data = API.getAllQuestions()
            if (successOrException.data.toString().isNotEmpty()) successOrException.loading = false

        } catch (exception: Exception) {
        successOrException.e=exception

        }
        return successOrException
    }


}
