package com.example.questionapp.network

import com.example.questionapp.model.Question
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface QuestionAPI {

    @GET("world.json")
        suspend fun getAllQuestions(): Question

}