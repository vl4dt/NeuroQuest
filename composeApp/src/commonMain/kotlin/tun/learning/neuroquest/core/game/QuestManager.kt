package tun.learning.neuroquest.core.game

import tun.learning.neuroquest.core.model.Quest

class QuestManager {
    fun updateQuestProgress(quest: Quest, progress: Int): Quest {
        val newProgress = quest.progress + progress
        val isCompleted = newProgress >= quest.target
        return quest.copy(progress = newProgress, isCompleted = isCompleted)
    }
}
