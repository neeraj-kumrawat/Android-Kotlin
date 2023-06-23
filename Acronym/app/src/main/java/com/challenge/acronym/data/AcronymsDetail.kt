package com.challenge.acronym.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "acronymsDetail")
data class AcronymsDetail(
    @PrimaryKey
    @SerializedName("sf") var sf: String,
    @SerializedName("lfs") var lfs: List<Lfs>

)