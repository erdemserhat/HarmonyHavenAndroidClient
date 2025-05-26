package com.erdemserhat.harmonyhaven.domain.model.rest

import com.erdemserhat.harmonyhaven.data.api.user.MoodDto

data class Mood(
    val id: String,
    val name: String,
    val displayName: String, // Türkçe isim için
    val imageUrl: String
)

fun MoodDto.toDomainModel(): Mood {
    return Mood(
        id = this.id,
        name = this.name,
        displayName = this.name.toTurkishMoodName(),
        imageUrl = this.moodImagePath
    )
}

fun List<MoodDto>.toDomainModel(): List<Mood> {
    return this.map { it.toDomainModel() }
}

/**
 * İngilizce mood isimlerini Türkçe'ye çevirir
 */
fun String.toTurkishMoodName(): String {
    return when (this.lowercase()) {
        "happy" -> "Mutlu"
        "calm" -> "Sakin"
        "angry" -> "Öfkeli"
        "burned out" -> "Tükenmiş"
        "sad" -> "Üzgün"
        "tired" -> "Yorgun"
        "excited" -> "Heyecanlı"
        else -> this // Eğer mapping bulunamazsa orijinal ismi döndür
    }
} 