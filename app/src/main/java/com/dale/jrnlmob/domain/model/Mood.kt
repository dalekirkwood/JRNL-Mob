package com.dale.jrnlmob.domain.model

enum class Mood(val emoji: String, val label: String) {
    HAPPY("😊", "Happy"),
    GRATEFUL("🙏", "Grateful"),
    PEACEFUL("😌", "Peaceful"),
    EXCITED("🎉", "Excited"),
    TIRED("😴", "Tired"),
    SAD("😢", "Sad"),
    ANXIOUS("😰", "Anxious"),
    ANGRY("😤", "Angry"),
    THOUGHTFUL("🤔", "Thoughtful"),
    NEUTRAL("😐", "Neutral")
}
