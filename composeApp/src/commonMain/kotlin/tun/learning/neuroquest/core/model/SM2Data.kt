package tun.learning.neuroquest.core.model

import kotlinx.datetime.LocalDate

data class SM2Data(
    val interval: Int,
    val repetitions: Int,
    val easeFactor: Float,
    val nextReviewDate: LocalDate
)
