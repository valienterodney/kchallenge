package com.valiente.kchallenge.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["trackId"], unique = true)])
data class Movie(
    @NonNull
    @PrimaryKey
    val trackId: Int,
    @NonNull
    val trackName: String? = "",
    val artworkUrl100: String? = "",
    @NonNull
    val trackPrice: String? = "0.0",
    val primaryGenreName : String? = "",
    val longDescription: String? ="",
    var seenDate: Long?
)