package tun.learning.neuroquest.core.model

data class Quest(
    val id: Long,
    val name: String,
    val description: String,
    val target: Int,
    var progress: Int,
    var isCompleted: Boolean
)
