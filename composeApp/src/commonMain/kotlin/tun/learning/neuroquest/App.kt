package tun.learning.neuroquest

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import tun.learning.neuroquest.core.interactor.SM2
import tun.learning.neuroquest.core.model.Flashcard
import tun.learning.neuroquest.core.model.MasteryPhase
import tun.learning.neuroquest.core.model.SM2Data

@Composable
fun App() {
    MaterialTheme {
        val sm2 = SM2()
        var flashcard by remember {
            mutableStateOf(
                Flashcard(
                    id = 1,
                    deckId = 1,
                    question = "What is the capital of France?",
                    answer = "Paris",
                    sm2Data = SM2Data(
                        interval = 0,
                        repetitions = 0,
                        easeFactor = 2.5f,
                        nextReviewDate = Clock.System.todayIn(TimeZone.currentSystemDefault())
                    ),
                    masteryPhase = MasteryPhase.RECOGNITION,
                    options = listOf("Paris", "London", "Berlin", "Madrid")
                )
            )
        }
        var showAnswer by remember { mutableStateOf(false) }
        var selectedOption by remember { mutableStateOf<String?>(null) }

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = flashcard.question, style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(16.dp))

            when (flashcard.masteryPhase) {
                MasteryPhase.RECOGNITION -> {
                    flashcard.options.forEach { option ->
                        Button(
                            onClick = { selectedOption = option },
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                        ) {
                            Text(option)
                        }
                    }
                    if (selectedOption != null) {
                        val isCorrect = selectedOption == flashcard.answer
                        Text(if (isCorrect) "Correct!" else "Incorrect!")
                    }
                }
                MasteryPhase.RECALL -> {
                    if (showAnswer) {
                        Text(text = flashcard.answer, style = MaterialTheme.typography.bodyLarge)
                    }
                }
                MasteryPhase.APPLICATION -> {
                    // TODO: Implement application UI
                    Text("Application UI")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (flashcard.masteryPhase == MasteryPhase.RECALL) {
                Button(onClick = { showAnswer = !showAnswer }) {
                    Text(if (showAnswer) "Hide Answer" else "Show Answer")
                }
            }


            Spacer(modifier = Modifier.height(32.dp))

            if (showAnswer || selectedOption != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = {
                        flashcard = sm2.calculate(flashcard, 0)
                        showAnswer = false
                        selectedOption = null
                    }) {
                        Text("Again")
                    }
                    Button(onClick = {
                        flashcard = sm2.calculate(flashcard, 3)
                        showAnswer = false
                        selectedOption = null
                    }) {
                        Text("Hard")
                    }
                    Button(onClick = {
                        flashcard = sm2.calculate(flashcard, 4)
                        showAnswer = false
                        selectedOption = null
                    }) {
                        Text("Good")
                    }
                    Button(onClick = {
                        flashcard = sm2.calculate(flashcard, 5)
                        showAnswer = false
                        selectedOption = null
                    }) {
                        Text("Easy")
                    }
                }
            }
        }
    }
}
