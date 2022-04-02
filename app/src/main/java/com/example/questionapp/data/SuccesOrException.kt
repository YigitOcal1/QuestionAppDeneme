package com.example.questionapp.data

import java.lang.Exception

data class SuccesOrException <T,Boolean,E: Exception>(
    var data:T?=null,
    var loading: kotlin.Boolean?=null,
    var e: E?=null
        )  {
}