package com.example.questionapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.questionapp.ui.theme.QuestionAppTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuestionAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    QuestionHome()

                }
            }
        }
    }
}

@Composable
fun QuestionHome(viewModel: QuestionsViewModel= hiltViewModel()){
    Questions(viewModel)
}

@Composable
fun Questions(viewModel: QuestionsViewModel) {


    val questions=viewModel.data.value.data?.toMutableList()
    if(viewModel.data.value.loading==true){
        //Log.d("Loading",".....")

    }else{
        //Log.d("Loading","done.")
        questions?.forEach{questionItem ->
            //Log.d("Result","Questions: ${questionItem.question}")
        }
    }


}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    QuestionAppTheme {

    }
}