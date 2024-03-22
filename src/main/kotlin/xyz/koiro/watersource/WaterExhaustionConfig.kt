package xyz.koiro.watersource

object WaterExhaustionConfig {
    val SPRINT = 0.05f // per meter
    val JUMP = 0.8f
    val REWARD_HEALTH = 2f
    fun thirstyPerSecond(amplifier: Int): Float{
        return 0.5f + (amplifier + 1) * 0.3f
    }
    fun thirstyMultiplier(amplifier: Int): Float{
        return 1.4f + amplifier.toFloat() * 0.4f
    }
}