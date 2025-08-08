package tun.learning.neuroquest.core.model

data class Deck(
    val id: Long,
    val name: String,
    val description: String,
    val flashcards: List<Flashcard>
)
