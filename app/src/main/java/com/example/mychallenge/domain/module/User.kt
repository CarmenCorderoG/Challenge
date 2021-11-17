package com.example.mychallenge.domain.module

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class User(
    var name: String?,
    var email: String?,
    var password: String?,

){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
