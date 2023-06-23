package com.challenge.acronym.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "lfs")
data class Lfs(
    @PrimaryKey
    @SerializedName("lf") var lf: String,
    @SerializedName("freq") var freq: Int,
    @SerializedName("since") var since: Int
)