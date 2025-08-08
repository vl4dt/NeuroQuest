package tun.learning.neuroquest.core.game

import tun.learning.neuroquest.core.model.PlayerStats

class GameManager {
    fun awardXp(playerStats: PlayerStats, xp: Int): PlayerStats {
        val newXp = playerStats.xp + xp
        val newLevel = newXp / 1000 // Level up every 1000 XP
        return playerStats.copy(xp = newXp, level = newLevel)
    }
}
