package tun.learning.neuroquest.core.model

import kotlinx.datetime.LocalDate

data class Flashcard(
    val id: Long,
    val deckId: Long,
    val question: String,
    val answer: String,
    val sm2Data: SM2Data,
    val masteryPhase: MasteryPhase,
    val options: List<String> = emptyList()
)
