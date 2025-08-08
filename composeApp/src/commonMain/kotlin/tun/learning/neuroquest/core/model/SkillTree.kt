package tun.learning.neuroquest.core.model

data class SkillTree(
    val id: Long,
    val name: String,
    val nodes: List<SkillTreeNode>
)
