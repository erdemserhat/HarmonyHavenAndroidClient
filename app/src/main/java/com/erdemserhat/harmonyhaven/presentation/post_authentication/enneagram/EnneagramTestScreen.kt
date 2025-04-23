package com.erdemserhat.harmonyhaven.presentation.post_authentication.enneagram

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun EnneagramTestScreen(navController: NavController, enneagramViewModel: EnneagramViewModel = hiltViewModel()) {

    val enneagramState by enneagramViewModel.enneagramState


    Box(modifier = Modifier.background(Color.White).fillMaxSize()){

        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button({
                enneagramViewModel.getQuestions()
            }) { Text("Soruları getir") }

            if(enneagramState.isLoadingQuestions){
                Text("Loading")
            }

            enneagramState.questions.map { questions->
                Column{
                    Text(questions.content)
                    Text(questions.id.toString())
                    Text(questions.personalityNumber.toString())
                }

                Spacer(modifier = Modifier.size(50.dp))
            }

        }





    }

}