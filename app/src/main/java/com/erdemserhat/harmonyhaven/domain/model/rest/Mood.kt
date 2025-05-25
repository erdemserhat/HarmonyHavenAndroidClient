package com.erdemserhat.harmonyhaven.domain.model.rest

import com.erdemserhat.harmonyhaven.data.api.user.MoodDto

data class Mood(
    val id: String,
    val name: String,
    val imageUrl: String
)

fun MoodDto.toDomainModel(): Mood {
    return Mood(
        id = this.id,
        name = this.name,
        imageUrl = this.moodImagePath
    )
}

fun List<MoodDto>.toDomainModel(): List<Mood> {
    return this.map { it.toDomainModel() }
} 