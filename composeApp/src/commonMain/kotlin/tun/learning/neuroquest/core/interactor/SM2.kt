package tun.learning.neuroquest.core.interactor

import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import tun.learning.neuroquest.core.model.Flashcard
import tun.learning.neuroquest.core.model.MasteryPhase
import tun.learning.neuroquest.core.model.SM2Data
import kotlin.math.roundToInt

class SM2 {
    fun calculate(flashcard: Flashcard, quality: Int): Flashcard {
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        val sm2Data = flashcard.sm2Data

        if (quality < 3) {
            return flashcard.copy(
                sm2Data = sm2Data.copy(
                    repetitions = 0,
                    interval = 1,
                    nextReviewDate = today.plus(DatePeriod(days = 1))
                )
            )
        }

        val easeFactor = calculateEaseFactor(sm2Data.easeFactor, quality)
        val repetitions = sm2Data.repetitions + 1
        val interval = calculateInterval(repetitions, sm2Data.interval, easeFactor)
        val nextReviewDate = today.plus(DatePeriod(days = interval))
        val masteryPhase = updateMasteryPhase(flashcard.masteryPhase, quality)

        return flashcard.copy(
            sm2Data = sm2Data.copy(
                repetitions = repetitions,
                interval = interval,
                easeFactor = easeFactor,
                nextReviewDate = nextReviewDate
            ),
            masteryPhase = masteryPhase
        )
    }

    private fun calculateEaseFactor(easeFactor: Float, quality: Int): Float {
        val newEaseFactor = easeFactor + (0.1 - (5 - quality) * (0.08 + (5 - quality) * 0.02)).toFloat()
        return if (newEaseFactor < 1.3f) 1.3f else newEaseFactor
    }

    private fun calculateInterval(repetitions: Int, previousInterval: Int, easeFactor: Float): Int {
        return when (repetitions) {
            1 -> 1
            2 -> 6
            else -> (previousInterval * easeFactor).roundToInt()
        }
    }

    private fun updateMasteryPhase(currentPhase: MasteryPhase, quality: Int): MasteryPhase {
        if (quality == 5) {
            return when (currentPhase) {
                MasteryPhase.RECOGNITION -> MasteryPhase.RECALL
                MasteryPhase.RECALL -> MasteryPhase.APPLICATION
                MasteryPhase.APPLICATION -> MasteryPhase.APPLICATION
            }
        }
        return currentPhase
    }
}
